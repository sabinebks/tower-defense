package object.tower;

import board.Position;

import object.tower.evolution.TypeEvolution;
import object.tower.projectil.ProjectilType;


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

    @Override
    public int appliquerEvolution(TypeEvolution typeEvo) {
        if (!this.evolutions.getEvolutions().contains(typeEvo)) return 0;

        if (this.evolutions.getEvolutionsPossedees().contains(typeEvo)) return 0;
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
        }
        if (cout > 0) {
            this.evolutions.getEvolutionsPossedees().add(typeEvo);
        }
        return cout;
    }

}
