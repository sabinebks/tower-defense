package object.tower;

import board.Position;

import object.tower.projectil.ProjectilType;

import java.util.List;


// classe tireur d'elite
public class EliteSniper extends Tower {

    public EliteSniper(Position pos) {
        super(pos, 500, -1, 2000, true, EffectType.IMMEDIATE, ProjectilType.FLECHETTE_TRES_POINTUE);
    }
}
