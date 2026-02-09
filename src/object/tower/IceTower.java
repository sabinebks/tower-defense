package object.tower;

import board.Position;

public class IceTower extends Tower {

    public IceTower(Position pos) {
        super(pos, 400, 100, 1500, false, EffectType.FREEZE);
    }
}
