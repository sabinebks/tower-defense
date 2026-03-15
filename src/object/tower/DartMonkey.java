package object.tower;
import board.Position;
import object.tower.evolution.TypeEvolution;
import object.tower.projectil.ProjectilType;

// DartMonkey qui tire des flechettes
public class DartMonkey extends Tower {
    
    /**
     * Cree une DartMonkey a la position specifiee.
     *
     * @param pos la position initiale de la tour sur le plateau
     */
    public DartMonkey(Position pos) {
        super(pos, 200, 100, 2, true, EffectType.IMMEDIATE, ProjectilType.FLECHETTE);
        this.evolutions.addEvolution(TypeEvolution.PORTEE);
        this.evolutions.addEvolution(TypeEvolution.CANDENCE);
        this.evolutions.addEvolution(TypeEvolution.PUISSANCE);
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
                this.degatsSup += 1;
                cout = 250;
                this.evolutions.deleteEvolutions(TypeEvolution.PUISSANCE);
                break;
            case PORTEE:
                this.porte = (int)(this.porte * 1.25);
                cout = 100;
                this.evolutions.deleteEvolutions(TypeEvolution.PORTEE);
                break;
            case CANDENCE:
                this.cadence = (int)(this.cadence * 1.25);
                cout = 150;
                this.evolutions.deleteEvolutions(TypeEvolution.CANDENCE);
                break;
            default:
                break;
        }
        if (cout > 0) {
            this.evolutions.getEvolutionsPossedees().put(typeEvo, cout);
        }
        return cout;
    }

}

