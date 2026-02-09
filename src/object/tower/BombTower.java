package object.tower;

import board.Position;


public class BombTower extends Tower {

    public BombTower(Position pos) {
        super(pos, 600, 150, 1800, true, EffectType.AREA);
        this.projectil = Projectil.BOMBE;
    }
}
