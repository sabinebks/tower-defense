package object.tower;

import board.Position;
import object.balloon.BalloonStraight;
import java.util.List;

public class IceTower extends Tower {

    public IceTower(Position pos) {
        super(pos, 400, 100, 1500, false, EffectType.FREEZE);
    }

    @Override
    protected boolean attack(List<BalloonStraight> balloons) {

        return false;
    }
}
