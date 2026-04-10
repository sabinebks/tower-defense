package choose.action;

<<<<<<< HEAD
import entity.tower.Tower;
import entity.tower.evolution.TypeEvolution;
import game.GameContext;


public class DownGrade extends Action {

    private TypeEvolution evolution;

    public DownGrade(Tower tower, TypeEvolution evolution) {
        super(tower);
        this.evolution = evolution;
    }

    @Override
    public void execute(GameContext game) {
        Tower tower = getTower();

        if (tower == null) {
            return;
        }

        int refund = tower.SellEvolution(evolution);
        game.getPlayer().addCredit(refund);
    }

    @Override
    public String getDescription() {
        Tower tower = getTower();
        String name = (tower == null) ? "tour" : tower.getName();
        return "Downgrade " + name + " (" + evolution + ")";
    }
}
=======
import game.GameContext;
import entity.tower.Tower;
import entity.tower.evolution.TypeEvolution;

public class DownGrade extends Action{
    /**
     * Les attributs
     * un tower a enlever l'evolution
     * le type evo a enlever
     */
    private Tower tower;
    private TypeEvolution evo;

    /**
     * Le constructeur
     * @param tower le tower 
     * @param evo le type d'evo a enlever
     */
    public DownGrade(Tower tower, TypeEvolution evo){
        this.tower = tower;
        this.evo = evo;
    }

    @Override
    /**
     * Lancer l'action : donner au joueur le credit de la vente d'evo
     * @param game le context du jeu
     */
    public void execute(GameContext game){;
        if(game.getPlayer().getTowers().contains(this.tower)){
            int index = game.getPlayer().getTowers().indexOf(this.tower);
            if(game.getPlayer().getTowers().get(index).getEvolutions().getEvolutionsPossedees().containsKey(evo)) {
                int cost = game.getPlayer().getTowers().get(index).SellEvolution(evo);
                game.getPlayer().addCredit(cost);
            }
        }
    }

    @Override
    /**
     * La description de l'action
     * @return une description du jeu
     */
    public String getDescription(){
        return  this.evo.name() + " (" + this.tower.getCoutEvolution(this.evo) + ")";
    }
}
>>>>>>> origin/main
