package object.tower;
import board.Position;
import object.tower.projectil.ProjectilType;

public class SlowTower extends Tower {

    public SlowTower(Position pos) {
        super(pos, 500, 100, 1500, false, EffectType.SLOW, ProjectilType.NO_DAMAGE);
    }

}