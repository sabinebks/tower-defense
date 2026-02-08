package object.tower;

import board.Position;
import object.balloon.BalloonStraight;
import java.util.List;

public class TackShooter extends Tower {

    public TackShooter(Position pos) {
        super(pos, 350, 80, 1200, true, EffectType.IMMEDIATE);
        this.projectil = Projectil.AIGUILLE;
    }
    @Override
    protected boolean attack(List<Balloon> balloons) {

        boolean hit = false; // Vérifie si un tir a eu lieu

        // On parcourt tous les ballons
        for (BalloonStraight b : balloons) {

            // Si le ballon est actif et dans la portée
            if (b.isActive() && distanceTo(b) <= porte) {

                // Inflige les dégâts de l'aiguille
                b.takeDamage(projectil.getDegats());
                hit = true;
            }
        }
        // Retourne true si au moins un ballon a été touché
        return hit;
    }
}
