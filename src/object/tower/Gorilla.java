package object.tower;

import board.Position;
import object.balloon.BalloonStraight;
import java.util.List;

public class Gorilla extends Tower {

    public Gorilla(Position pos) {
        super(pos, 1200, 200, 300, true, EffectType.IMMEDIATE);
        this.projectil = Projectil.FLECHETTE_POINTUE;
    }

    @Override
    protected boolean attack(List<BalloonStraight> balloons) {

        // On cherche un ballon dans la portée
        BalloonStraight target = findTarget(balloons);

        // Si un ballon est trouvé
        if (target != null) {

            // Le ballon prend les dégâts de la fléchette pointue
            target.takeDamage(projectil.getDegats());

            // La tour a tiré
            return true;
        }

        // Aucun ballon à portée
        return false;
    }
}
