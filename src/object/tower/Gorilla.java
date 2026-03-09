package object.tower;

import board.Position;

import object.tower.projectil.ProjectilType;


public class Gorilla extends Tower {

    /**
     * Crée une Gorilla à la position spécifiée.
     *
     * @param pos la position initiale de la tour sur le plateau
     */
    public Gorilla(Position pos) {
        super(pos, 1200, 200, 3, true, EffectType.IMMEDIATE,  ProjectilType.FLECHETTE_POINTUE);
    }
}
