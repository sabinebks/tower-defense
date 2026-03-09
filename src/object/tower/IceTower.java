package object.tower;

import board.Position;
import object.tower.projectil.ProjectilType;

public class IceTower extends Tower {
    /**
     * Crée une IceTower à la position spécifiée.
     *
     * @param pos la position initiale de la tour sur le plateau
     */
    public IceTower(Position pos) {
        super(pos, 400, 100, 5, false, EffectType.FREEZE, ProjectilType.NO_DAMAGE);
    }
}
