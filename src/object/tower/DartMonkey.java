package object.tower;

import board.Position;
import object.balloon.BalloonStraight;
import java.util.List;

public class DartMonkey extends Tower {
    public DartMonkey(Position pos) {
        super(pos, 200, 100, 1000, true, EffectType.IMMEDIATE);
        this.projectil = Projectil.FLECHETTE;
    }
    @Override
    protected boolean attack(List<BalloonStraight> balloons) {
        // On cherche un ballon cible dans la portée
        BalloonStraight target = findTarget(balloons);
        // Si un ballon est trouvé
        if (target != null) {
            // Le ballon prend les dégâts de la fléchette
            target.takeDamage(projectil.getDegats());
            // La tour a tiré
            return true;
        }
        // Aucun ballon à portée
        return false;
    }
}
