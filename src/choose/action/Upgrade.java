package choose.action;

import game.GameContext;
import object.tower.Tower;
import object.tower.evolution.TypeEvolution;


public class Upgrade extends Action {

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
    }
}