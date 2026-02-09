package object.tower;

import board.Position;

public class Gorilla extends Tower {

    public Gorilla(Position pos) {
        super(pos, 1200, 200, 300, true, EffectType.IMMEDIATE);
        this.projectil = Projectil.FLECHETTE_POINTUE;
    }
}
