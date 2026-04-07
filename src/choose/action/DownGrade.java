package choose.action;

import entity.tower.Tower;
import entity.tower.evolution.TypeEvolution;
import game.GameContext;


public class DownGrade extends Action {

    private TypeEvolution evolution; // l'évolution à retirer

    /**
     * constructeur
     * @param tower la tour à downgrader
     * @param evolution l'évolution à retirer
     */
    public DownGrade(Tower tower, TypeEvolution evolution) {
        super(tower);
        this.evolution = evolution;
    }

    /**
     * methode pour retirer une evolution a la tour
     * @param game le contexte du jeu
     */
    @Override
    public void execute(GameContext game) {
        Tower tower = getTower();

        if (tower == null) {
            return;
        }

        int refund = tower.SellEvolution(evolution);
        game.getPlayer().addCredit(refund);
    }

    /**
     * methode pour obtenir la description de l'action
     * @return description de l'action
     */
    @Override
    public String getDescription() {
        Tower tower = getTower();
        String name = (tower == null) ? "tour" : tower.getName();
        return "Downgrade " + name + " (" + evolution + ")";
    }
}