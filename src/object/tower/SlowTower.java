package object.tower;
import board.Position;
import object.tower.evolution.TypeEvolution;
import object.tower.projectil.ProjectilType;

public class SlowTower extends Tower {
/**
     * Crée une SlowTower à la position spécifiée.
     *
     * @param pos la position initiale de la tour sur le plateau
     */
    public SlowTower(Position pos) {
        super(pos, 500, 100, 8, false, EffectType.SLOW, ProjectilType.NO_DAMAGE);
    }


    /**
     * methode pour appliquer une evolution a la tour
     * aucune evolution disponible pour cette tour
     * @param typeEvo le type d'evolution a appliquer
     * @return 0 car aucune evolution n'est disponible
     */
    @Override
    public int appliquerEvolution(TypeEvolution typeEvo) {
        return 0;
    }
}