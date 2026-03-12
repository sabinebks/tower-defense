package object.tower;

import board.Position;
import object.tower.evolution.TypeEvolution;
import object.tower.projectil.ProjectilType;
public class BombTower extends Tower {
    /**
     * Crée une BombTower à la position spécifiée.
     *
     * @param pos la position initiale de la tour sur le plateau
     */


    public BombTower(Position pos) {
        super(pos, 600, 150, 5, true, EffectType.AREA, ProjectilType.BOMBE);
        this.evolutions.addEvolution(TypeEvolution.PORTEE);
        this.evolutions.addEvolution(TypeEvolution.CANDENCE);
        this.evolutions.addEvolution(TypeEvolution.PROJECTILES);
        this.evolutions.addEvolution(TypeEvolution.PUISSANCE);
    }

    @Override
    public int appliquerEvolution(TypeEvolution typeEvo) {
        if (!this.evolutions.getEvolutions().contains(typeEvo)) return 0;

        if (this.evolutions.getEvolutionsPossedees().contains(typeEvo)) return 0;
        int cout = 0;

        switch (typeEvo) {
            case PUISSANCE:
                this.degatsSup += 1;
                cout = 200;
                this.evolutions.deleteEvolutions(TypeEvolution.PUISSANCE);
                break;
            case PORTEE:
                this.porte = (int)(this.porte * 1.5);
                cout = 250;
                this.evolutions.deleteEvolutions(TypeEvolution.PORTEE);
                break;
            case CANDENCE:
                this.cadence = (int)(this.cadence * 0.75);
                cout = 300;
                this.evolutions.deleteEvolutions(TypeEvolution.CANDENCE);
                break;
            case PROJECTILES:
                this.type = ProjectilType.EXTRA_BOMBE;
                cout = 400;
                this.evolutions.deleteEvolutions(TypeEvolution.PROJECTILES);
                break;
        }
        if (cout > 0) {
            this.evolutions.getEvolutionsPossedees().add(typeEvo);
        }
        return cout;
    }
}
