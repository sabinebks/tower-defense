package object.tower;

import board.Position;
import object.balloon.*;
import java.util.List;

public class IceTower extends Tower {

    public IceTower(Position pos) {
        super(pos, 400, 100, 1500, false, EffectType.FREEZE);
    }

    @Override
    protected boolean attack(List<Balloon> balloons) {

        Balloon target = findTarget(balloons);
        if (target != null) {

            // Inflige les dégâts
            target.hit(projectil.getDegats());

            // Gèle le ballon
            target.freeze();

            // La tour a tiré
            return true;

        }
        return false;
    }
}
