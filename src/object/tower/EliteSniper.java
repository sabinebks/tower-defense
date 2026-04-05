package object.tower;

import board.Position;

import object.balloon.Balloon;
import object.tower.evolution.TypeEvolution;
import object.tower.projectil.NotMatchException;
import object.tower.projectil.Projectil;
import object.tower.projectil.ProjectilSimple;
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
        this.evolutions.addEvolution(TypeEvolution.PUISSANCE);
        this.evolutions.addEvolution(TypeEvolution.CANDENCE);
    }

    /**
     * methode pour appliquer une evolution a la tour
     * @param typeEvo le type d'evolution a appliquer
     * @return le cout de l'evolution, ou 0 si invalide ou deja achetee
     */
    @Override
    public int appliquerEvolution(TypeEvolution typeEvo) {
        if (!this.evolutions.getEvolutions().contains(typeEvo)) return 0;

        if (this.evolutions.getEvolutionsPossedees().containsKey(typeEvo)) return 0;
        int cout = 0;

        switch (typeEvo) {
            case PUISSANCE:
                this.degatsSup += 2;
                cout = 300;
                this.evolutions.deleteEvolutions(TypeEvolution.PUISSANCE);
                break;
            case CANDENCE:
                this.cadence = (int)(this.cadence * 1.25);
                cout = 200;
                this.evolutions.deleteEvolutions(TypeEvolution.CANDENCE);
                break;
            default:
                break;
        }
        if (cout > 0) {
            this.evolutions.getEvolutionsPossedees().put(typeEvo,cout);
        }
        return cout;
    }

    public Projectil attack(List<Balloon> balloons) throws NotMatchException {
        Balloon target = findTarget(balloons);
        if (target == null) return null;
        double startX = position.getCol() + 0.5;
        double startY = position.getRow() + 0.5;
        if (this.porte == -1) {
            target.hit(this.type.getDegats());
            return null;
        }
        return new ProjectilSimple(
                this.type,
                startX,
                startY,
                target
        );
    }

    @Override
    public int getCoutEvolution(TypeEvolution typeEvo) {
        return switch (typeEvo) {
            case PUISSANCE -> 300;
            case CANDENCE  -> 200;
            default        -> 0;
        };
    }
    /**
     * retourne le symbole de la EliteSniper
     * @return "E"
     */
    @Override    
    public String getSymbol() { return "E"; }


    @Override
    public String getName() {
        return "EliteSniper";
    }

}
