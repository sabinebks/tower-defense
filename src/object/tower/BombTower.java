package object.tower;

import board.Position;
import object.tower.evolution.TypeEvolution;
import object.tower.projectil.ProjectilType;
public class BombTower extends Tower {
    /**
     * Crée une BombTower à la position spécifiée.
     *
     * @param pos la position initiale de la tour sur le plateau
     */


    public BombTower(Position pos) {
        super(pos, 600, 150, 5, true, EffectType.AREA, ProjectilType.BOMBE);
        this.evolutions.addEvolution(TypeEvolution.PORTEE);
        this.evolutions.addEvolution(TypeEvolution.CANDENCE);
        this.evolutions.addEvolution(TypeEvolution.PROJECTILES);
        this.evolutions.addEvolution(TypeEvolution.PUISSANCE);
    }

}
