package object.tower;

import board.Position;
import object.tower.evolution.TypeEvolution;
import object.tower.projectil.ProjectilType;

public class IceTower extends Tower {
    /**
     * Crée une IceTower à la position spécifiée.
     *
     * @param pos la position initiale de la tour sur le plateau
     */
    public IceTower(Position pos) {
        super(pos, 400, 100, 15, false, EffectType.FREEZE, ProjectilType.NO_DAMAGE);
    }


    @Override
    public int appliquerEvolution(TypeEvolution typeEvo) {
        return 0;
    }

}
