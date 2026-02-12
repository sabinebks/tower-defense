package object.tower;

import board.Position;
<<<<<<< HEAD
import object.balloon.Balloon;
import object.tower.projectil.Projectil;

import java.util.List;
=======

>>>>>>> 0939c0b0cf11555ad9583ee7444d2a19decc0417

public class BombTower extends Tower {

    public BombTower(Position pos) {
        super(pos, 600, 150, 1800, true, EffectType.AREA);
        this.projectil = Projectil.BOMBE;
    }
}
