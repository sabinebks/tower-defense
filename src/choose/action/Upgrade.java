package choose.action;

import game.GameContext;
import entity.tower.Tower;
import entity.tower.evolution.TypeEvolution;

public class Upgrade extends Action {
    private Tower tower;
    private TypeEvolution typeEvo;

    public Upgrade(Tower t, TypeEvolution typeEvo) {
        this.tower = t;
        this.typeEvo = typeEvo;
    }

    @Override
    public void execute(GameContext game) {
        if (tower != null) {
            int cost = tower.appliquerEvolution(this.typeEvo);
            game.getPlayer().removeCredit(cost);
        }
    }

    @Override
    public String getDescription() {
        return  this.typeEvo.name() + " (" + this.tower.getCoutEvolution(this.typeEvo) + ")";
    }
}