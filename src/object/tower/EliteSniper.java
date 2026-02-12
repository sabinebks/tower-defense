package object.tower;

import board.Position;
import object.balloon.*;
import object.tower.projectil.Projectil;

import java.util.List;

// classe tireur d'elite
public class EliteSniper extends Tower {

    public EliteSniper(Position pos) {
        super(pos, 500, -1, 2000, true, EffectType.IMMEDIATE);
        this.projectil = Projectil.FLECHETTE_TRES_POINTUE;
    }

    @Override
    protected boolean attack(List<Balloon> balloons) {

        // On cherche un ballon cible dans la portée
        Balloon target = findTarget(balloons);

        // Si un ballon est trouvé
        if (target != null) {

            // Inflige les dégâts de la fléchette très pointue
            target.hit(projectil.getDegats());

            // La tour a tiré
            return true;
        }

        // Aucun tir effectué
        return false;
    }
}
