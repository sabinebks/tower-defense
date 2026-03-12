package object.tower;

import board.Position;

import object.tower.evolution.TypeEvolution;
import object.tower.projectil.ProjectilType;




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
        }
        if (cout > 0) {
            this.evolutions.getEvolutionsPossedees().put(typeEvo,cout);
        }
        return cout;
    }

}
