package entity.tower;
import board.Position;
import entity.balloon.Balloon;
import entity.tower.evolution.TypeEvolution;
import entity.tower.projectil.NotMatchException;
import entity.tower.projectil.Projectil;
import entity.tower.projectil.ProjectilSlow;
import entity.tower.projectil.ProjectilType;

import java.util.List;

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
/*  
    * methode d'attaque de la SlowTower
      * @param balloons la liste des ballons presents sur le plateau
      * @return un projectil si une cible est trouvee, sinon null
    */
    public Projectil attack(List<Balloon> balloons) throws NotMatchException {
        Balloon target = findTarget(balloons);
        if (target == null) return null;
        double startX = position.getCol() + 0.5;
        double startY = position.getRow() + 0.5;
        return new ProjectilSlow(this.type, startX, startY, target);
    }

    @Override
    public int getCoutEvolution(TypeEvolution typeEvo) {
        return 0;
    }
    /**
     * retourne le symbole de la SlowTower
     * @return "S"
     */
    @Override
    public String getSymbol() { return "S"; }

    @Override
    public String getName() {
        return "SlowTower";
    }
}