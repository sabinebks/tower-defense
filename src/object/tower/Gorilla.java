package object.tower;

import board.Position;
<<<<<<< HEAD
import object.balloon.*;
import object.tower.projectil.Projectil;

import java.util.List;
=======
>>>>>>> 0939c0b0cf11555ad9583ee7444d2a19decc0417

public class Gorilla extends Tower {

    public Gorilla(Position pos) {
        super(pos, 1200, 200, 300, true, EffectType.IMMEDIATE);
        this.projectil = Projectil.FLECHETTE_POINTUE;
    }
}
