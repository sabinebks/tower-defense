package choose.action;

import entity.tower.Tower;
import entity.tower.evolution.TypeEvolution;
import game.GameContext;


public class Upgrade extends Action {

    private TypeEvolution evolution; // l'évolution à appliquer

    /**
     * constructeur
     * @param tower la tour à améliorer
     * @param evolution l'évolution à appliquer
     */
    public Upgrade(Tower tower, TypeEvolution evolution) {
        super(tower);
        this.evolution = evolution;
    }

    /**
     * methode pour appliquer une evolution à la tour
     * @param game le contexte du jeu
     */
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

    /**
     * methode pour obtenir la description de l'action
     * @return description de l'action
     */
    @Override
    public String getDescription() {
        Tower tower = getTower();
        String name = (tower == null) ? "tour" : tower.getName();
        return "Améliorer " + name + " (" + evolution + ")";
    }
}