package object.tower;
import board.Position;
import object.tower.projectil.ProjectilType;

public class DartMonkey extends Tower {
    public DartMonkey(Position pos) {
        super(pos, 200, 100, 2, true, EffectType.IMMEDIATE, ProjectilType.FLECHETTE);
    }
}
