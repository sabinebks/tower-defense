package object.tower;
import board.Position;
import object.balloon.*;
import java.util.List;

public class SlowTower extends Tower {

    public SlowTower(Position pos) {
        super(pos, 500, 100, 1500, false, EffectType.SLOW);
    }

    @Override
    protected boolean attack(List<Balloon> balloons) {

        Balloon target = findTarget(balloons);

        // Si un ballon est trouvé
        if (target != null && target.isActive()) {

            // Inflige les dégâts
            target.hit(projectil.getDegats());

            // Ralentit le ballon (ex: -60% de vitesse)
            target.slow(0.4);

            // La tour a tiré
            return true;
        }
        return false;
    }
}