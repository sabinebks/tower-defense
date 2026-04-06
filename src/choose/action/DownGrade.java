package choose.action;

import game.GameContext;
import object.tower.Tower;
import object.tower.evolution.TypeEvolution;


public class DownGrade extends TowerAction {

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