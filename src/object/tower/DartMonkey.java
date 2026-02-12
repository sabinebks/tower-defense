package object.tower;

import board.Position;
<<<<<<< HEAD
import object.balloon.*;
import object.tower.projectil.Projectil;

import java.util.List;
=======
>>>>>>> 0939c0b0cf11555ad9583ee7444d2a19decc0417

public class DartMonkey extends Tower {
    public DartMonkey(Position pos) {
        super(pos, 200, 100, 1000, true, EffectType.IMMEDIATE);
        this.projectil = Projectil.FLECHETTE;
    }
}
