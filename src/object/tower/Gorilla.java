package object.tower;

import board.Position;

import object.balloon.Balloon;
import object.tower.evolution.TypeEvolution;
import object.tower.projectil.NotMatchException;
import object.tower.projectil.Projectil;
import object.tower.projectil.ProjectilSimple;
import object.tower.projectil.ProjectilType;

import java.util.List;


public class Gorilla extends Tower {

    /**
     * Crée une Gorilla à la position spécifiée.
     *
     * @param pos la position initiale de la tour sur le plateau
     */
    public Gorilla(Position pos) {
        super(pos, 1200, 200, 3, true, EffectType.IMMEDIATE,  ProjectilType.FLECHETTE_POINTUE);
        this.evolutions.addEvolution(TypeEvolution.PUISSANCE);
        this.evolutions.addEvolution(TypeEvolution.PORTEE);
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
                cout = 600;
                this.evolutions.deleteEvolutions(TypeEvolution.PUISSANCE);
                break;
            case PORTEE:
                this.porte = (int)(this.porte * 1.5);
                cout = 400;
                this.evolutions.deleteEvolutions(TypeEvolution.PORTEE);
                break;
            default:
                break;
        }
        if (cout > 0) {
            this.evolutions.getEvolutionsPossedees().put(typeEvo, cout);
        }
        return cout;
    }

    @Override
    public int getCoutEvolution(TypeEvolution typeEvo) {
        return switch (typeEvo) {
            case PUISSANCE -> 600;
            case PORTEE    -> 400;
            default        -> 0;
        };
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

    /**
     * retourne le symbole de la Gorilla
     * @return "G"
     */    
    @Override
    public String getSymbol() { return "G"; }

    @Override
    public String getName() {
        return "Gorilla";
    }

}
