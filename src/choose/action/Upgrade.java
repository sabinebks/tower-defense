package choose.action;

import game.GameContext;
import entity.tower.Tower;
import entity.tower.evolution.TypeEvolution;

/**
 * upgrade action
 */
public class Upgrade extends Action {

    /**
     * tower to upgrade
     * evolution type
     */
    private Tower tower;
    private TypeEvolution typeEvo;

    /**
     * constructor
     * @param t the tower to upgrade
     * @param typeEvo the evolution type
     */
    public Upgrade(Tower t, TypeEvolution typeEvo) {
        this.tower = t;
        this.typeEvo = typeEvo;
    }

    @Override
    /**
     * execute upgrade
     * @param game the game context
     */
    public void execute(GameContext game) {
        if (tower != null) {
            int cost = tower.appliquerEvolution(this.typeEvo);
            game.getPlayer().removeCredit(cost);
        }
    }

    @Override
    /**
     * description
     * @return description
     */
    public String getDescription() {
        return this.typeEvo.name() + " (" + this.tower.getCoutEvolution(this.typeEvo) + ")";
    }
}