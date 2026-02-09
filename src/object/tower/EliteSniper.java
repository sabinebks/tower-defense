package object.tower;

import board.Position;

// classe tireur d'elite
public class EliteSniper extends Tower {

    public EliteSniper(Position pos) {
        super(pos, 500, -1, 2000, true, EffectType.IMMEDIATE);
        this.projectil = Projectil.FLECHETTE_TRES_POINTUE;
    }
}
