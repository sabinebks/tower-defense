package object.tower;

import board.Position;

import object.tower.projectil.ProjectilType;

import java.util.List;


// classe tireur d'elite
public class EliteSniper extends Tower {
 /**
     * Crée une EliteSniper à la position spécifiée.
     *
     * @param pos la position initiale de la tour sur le plateau
     */
    public EliteSniper(Position pos) {
        super(pos, 500, -1, 7, true, EffectType.IMMEDIATE, ProjectilType.FLECHETTE_TRES_POINTUE);
    }
}
