package object.tower;

import board.Position;


import object.tower.evolution.TypeEvolution;
import object.tower.projectil.ProjectilType;


public class TackShooter extends Tower {
  /**
     * Crée une TackShooter à la position spécifiée.
     *
     * @param pos la position initiale de la tour sur le plateau
     */
    public TackShooter(Position pos) {

        super(pos, 350, 80, 2, true, EffectType.IMMEDIATE, ProjectilType.AIGUILLE);
        this.evolutions.addEvolution(TypeEvolution.PORTEE);
        this.evolutions.addEvolution(TypeEvolution.CANDENCE);
    }


    @Override
    public int appliquerEvolution(TypeEvolution typeEvo) {
        if (!this.evolutions.getEvolutions().contains(typeEvo)) return 0;

        if (this.evolutions.getEvolutionsPossedees().containsKey(typeEvo)) return 0;
        int cout = 0;

        switch (typeEvo) {
            case PORTEE:
                this.porte = (int)(this.porte * 1.2);
                cout = 150;
                this.evolutions.deleteEvolutions(TypeEvolution.PORTEE);
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
