package object.tower;

import board.Position;
<<<<<<< HEAD
import object.balloon.*;
import object.tower.projectil.Projectil;

import java.util.List;
=======
>>>>>>> 0939c0b0cf11555ad9583ee7444d2a19decc0417

// classe tireur d'elite
public class EliteSniper extends Tower {

    public EliteSniper(Position pos) {
        super(pos, 500, -1, 2000, true, EffectType.IMMEDIATE);
        this.projectil = Projectil.FLECHETTE_TRES_POINTUE;
    }
}
