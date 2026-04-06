package choose.action;

import game.GameContext;
import object.tower.Tower;

public abstract class Action {

    private Tower tower;

    public Action(Tower tower) {
        this.tower = tower;
    }

    public Tower getTower() {
        return tower;
    }

    public abstract void execute(GameContext game);
    public abstract String getDescription();
}