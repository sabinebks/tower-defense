package object.tower;

import board.Position;

import object.tower.projectil.ProjectilType;



public class BombTower extends Tower {

    public BombTower(Position pos) {
        super(pos, 600, 150, 1800, true, EffectType.AREA, ProjectilType.BOMBE);
    }
}
