package object.tower;
import board.Position;
import object.balloon.BalloonStraight;
import java.util.List;

public class SlowTower extends Tower {
    public SlowTower(Position pos) {
        super(pos, 500, 100, 1500, false, EffectType.SLOW);
    }
    @Override
    protected boolean attack(List<BalloonStraight> balloons) {
        return false;
    }
}