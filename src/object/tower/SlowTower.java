package object.tower;
import board.Position;
public class SlowTower extends Tower {

    public SlowTower(Position pos) {
        super(pos, 500, 100, 1500, false, EffectType.SLOW);
    }

}