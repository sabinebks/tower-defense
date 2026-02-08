package object.tower;

import board.Position;
import object.balloon.BalloonStraight;
import java.util.List;

public class BombTower extends Tower {

    public BombTower(Position pos) {
        super(pos, 600, 150, 1800, true, EffectType.AREA);
        this.projectil = Projectil.BOMBE;
    }

    @Override
    protected boolean attack(List<BalloonStraight> balloons) {
        // On cherche un ballon cible dans la portée
        BalloonStraight target = findTarget(balloons);
        // Si un ballon est trouvé
        if (target != null) {
            // Position de l'explosion (position du ballon ciblé)
            Position impact = target.getCurrentPosition();
            // On parcourt tous les ballons
            for (BalloonStraight b : balloons) {
                // Calcul de la distance entre le ballon et l'explosion
                double d = Math.sqrt(
                    Math.pow(b.getCurrentPosition().getRow() - impact.getRow(), 2) +
                    Math.pow(b.getCurrentPosition().getCol() - impact.getCol(), 2)
                );
                // Si le ballon est actif et dans la zone d'explosion
                if (b.isActive() && d <= porte) {
                    // Le ballon prend des dégâts
                    b.takeDamage(projectil.getDegats());
                }
            }
            // La tour a tiré
            return true;
        }
        // Aucun tir effectué
        return false;
    }

}
