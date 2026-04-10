package entity.tower;
import board.Position;
import entity.balloon.Balloon;
import entity.tower.evolution.TypeEvolution;
import entity.tower.projectil.NotMatchException;
import entity.tower.projectil.Projectil;
import entity.tower.projectil.ProjectilSimple;
import entity.tower.projectil.ProjectilType;

import java.util.List;

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
  /*  
    * methode d'attaque de la DartMonkey
      * @param balloons la liste des ballons presents sur le plateau
      * @return un projectil si une cible est trouvee, sinon null
    */
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
         switch (typeEvo) {
            case PUISSANCE:
                return 250;
            case PORTEE:
                return 100;
            case CANDENCE:
                return 150;
            default:
                return 0;
        }
    }
    
    /**
     * retourne le symbole de la DartMonkey
     * @return "A"
     */
    @Override
    public String getSymbol() { return "A"; }

    @Override
    public String getName() {
        return "DartMonkey";
    }

}

