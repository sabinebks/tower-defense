package object.tower;

import board.Position;
import object.balloon.Balloon;
import object.tower.projectil.Projectil;
import object.tower.projectil.ProjectilBombe;

import java.util.List;

public class BombTower extends Tower {

    public BombTower(Position pos) {
        super(pos, 600, 150, 1800, true, EffectType.AREA);
        this.projectil = new ProjectilBombe();
    }
}
