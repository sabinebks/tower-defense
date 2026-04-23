package entity.tower;

import board.Position;
import entity.balloon.Balloon;
import entity.tower.evolution.TypeEvolution;
import entity.tower.projectil.NotMatchException;
import entity.tower.projectil.Projectil;
import entity.tower.projectil.ProjectilBombe;
import entity.tower.projectil.ProjectilType;

import java.util.List;

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
            this.evolutions.getEvolutionsPossedees().put(typeEvo, cout);
        }
        return cout;
    }
 /*  
    * methode d'attaque de la BombTower
      * @param balloons la liste des ballons presents sur le plateau
      * @return un projectil si une cible est trouvee, sinon null
    */
    public Projectil attack(List<Balloon> balloons) throws NotMatchException {
        Balloon target = findTarget(balloons);
        if (target == null) return null;
        double startX = position.getCol() + 0.5;
        double startY = position.getRow() + 0.5;
        return new ProjectilBombe(this.type, startX, startY, target, 3, balloons);
    }

    @Override
    public int getCoutEvolution(TypeEvolution typeEvo) {
         switch (typeEvo) {
            case PUISSANCE:
                return 200;
            case PORTEE:
                return 250;
            case CANDENCE:
                return 300;
            case PROJECTILES: 
                return 400;
            default:
                return  0;
        }
    }



    /**
     * retourne le symbole de la BombTower
     * @return "B"
     */
    public String getSymbol() { return "B"; }

    @Override
    public String getName() {
        return "BombTower";
    }
}
