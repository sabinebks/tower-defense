package choose.action;

import game.GameContext;
import entity.tower.Tower;
import entity.tower.evolution.TypeEvolution;

/**
 * downgrade action
 */
public class DownGrade extends Action {

    /**
     * tower concerned
     * evolution to remove
     */
    private Tower tower;
    private TypeEvolution evo;

    /**
     * constructor
     * @param tower the tower
     * @param evo the evolution to remove
     */
    public DownGrade(Tower tower, TypeEvolution evo) {
        this.tower = tower;
        this.evo = evo;
    }

    @Override
    /**
     * execute action: give player credits from selling evolution
     * @param game the game context
     */
    public void execute(GameContext game) {
        if (game.getPlayer().getTowers().contains(this.tower)) {
            int index = game.getPlayer().getTowers().indexOf(this.tower);

            if (game.getPlayer().getTowers().get(index)
                    .getEvolutions()
                    .getEvolutionsPossedees()
                    .containsKey(evo)) {

                int cost = game.getPlayer().getTowers()
                        .get(index)
                        .SellEvolution(evo);

                game.getPlayer().addCredit(cost);
            }
        }
    }

    @Override
    /**
     * action description
     * @return description
     */
    public String getDescription() {
        return this.evo.name() + " (" + this.tower.getCoutEvolution(this.evo) + ")";
    }
}