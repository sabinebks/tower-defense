package object.tower;

import board.Position;
<<<<<<< HEAD
import object.balloon.*;
import object.tower.projectil.Projectil;

import java.util.List;
=======
>>>>>>> 0939c0b0cf11555ad9583ee7444d2a19decc0417

public class TackShooter extends Tower {

    public TackShooter(Position pos) {
        super(pos, 350, 80, 1200, true, EffectType.IMMEDIATE);
        this.projectil = Projectil.AIGUILLE;
    }
}
