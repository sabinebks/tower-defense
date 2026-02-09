package object.tower;

import board.Position;

public class TackShooter extends Tower {

    public TackShooter(Position pos) {
        super(pos, 350, 80, 1200, true, EffectType.IMMEDIATE);
        this.projectil = Projectil.AIGUILLE;
    }
}
