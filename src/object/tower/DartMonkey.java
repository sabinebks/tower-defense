package object.tower;

import board.Position;

public class DartMonkey extends Tower {
    public DartMonkey(Position pos) {
        super(pos, 200, 100, 1000, true, EffectType.IMMEDIATE);
        this.projectil = Projectil.FLECHETTE;
    }
}
