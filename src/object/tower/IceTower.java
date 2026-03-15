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

    /**
     * retourne le symbole de la IceTower
     * @return "I"
     */
    @Override
    public String getSymbol() { return "I"; }

}
