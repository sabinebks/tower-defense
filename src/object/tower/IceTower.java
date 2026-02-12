package object.tower;

import board.Position;
import object.tower.projectil.ProjectilType;

public class IceTower extends Tower {

    public IceTower(Position pos) {
        super(pos, 400, 100, 1500, false, EffectType.FREEZE, ProjectilType.NO_DAMAGE);
    }
}
