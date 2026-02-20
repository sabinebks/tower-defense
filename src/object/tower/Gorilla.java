package object.tower;

import board.Position;
import object.tower.projectil.Projectil;
import object.tower.projectil.ProjectilType;


public class Gorilla extends Tower {

    public Gorilla(Position pos) {
        super(pos, 1200, 200, 3, true, EffectType.IMMEDIATE,  ProjectilType.FLECHETTE_POINTUE);
    }
}
