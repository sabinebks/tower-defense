package choose.action;

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