package object.tower;

import board.Position;


import object.tower.evolution.TypeEvolution;
import object.tower.projectil.ProjectilType;


public class TackShooter extends Tower {
  /**
     * Crée une TackShooter à la position spécifiée.
     *
     * @param pos la position initiale de la tour sur le plateau
     */
    public TackShooter(Position pos) {

        super(pos, 350, 80, 2, true, EffectType.IMMEDIATE, ProjectilType.AIGUILLE);
        this.evolutions.addEvolution(TypeEvolution.PORTEE);
        this.evolutions.addEvolution(TypeEvolution.CANDENCE);
    }


}
