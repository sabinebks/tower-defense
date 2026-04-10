package choose.action;

<<<<<<< HEAD
import entity.tower.Tower;
import entity.tower.evolution.TypeEvolution;
import game.GameContext;

=======
import game.GameContext;
import entity.tower.Tower;
import entity.tower.evolution.TypeEvolution;
>>>>>>> origin/main

public class Upgrade extends Action {
    /**
     * Les attributs
     * Un tower a evolue
     * Le type d'evoluer
     */
    private Tower tower;
    private TypeEvolution typeEvo;

<<<<<<< HEAD
    private TypeEvolution evolution;

    public Upgrade(Tower tower, TypeEvolution evolution) {
        super(tower);
        this.evolution = evolution;
    }

    @Override
    public void execute(GameContext game) {
        Tower tower = getTower();
        if (tower == null) {
            return;
        }
        int cost = tower.getCoutEvolution(evolution);
        if (game.getPlayer().getCredit() < cost) {
            System.out.println("Pas assez de crédits pour cette amélioration.");
            return;
        }
        game.getPlayer().removeCredit(cost);
        tower.appliquerEvolution(evolution);
    }

    @Override
    public String getDescription() {
        Tower tower = getTower();
        String name = (tower == null) ? "tour" : tower.getName();
        return "Améliorer " + name + " (" + evolution + ")";
=======
    /**
     * Constructeur
     * @param t le tower a evoluer
     * @param typeEvo le type d'evolution
     */
    public Upgrade(Tower t, TypeEvolution typeEvo) {
        this.tower = t;
        this.typeEvo = typeEvo;
    }

    @Override
    /**
     * Execute l'action : appliquer l'evolution sur le tower et diminue un nombre de credit du joueur
     * @param game le contexte du jeu
     */
    public void execute(GameContext game) {
        if (tower != null) {
            int cost = tower.appliquerEvolution(this.typeEvo);
            game.getPlayer().removeCredit(cost);
        }
    }

    @Override
    /**
     * La description de l'action
     * @return la description
     */
    public String getDescription() {
        return  this.typeEvo.name() + " (" + this.tower.getCoutEvolution(this.typeEvo) + ")";
>>>>>>> origin/main
    }
}