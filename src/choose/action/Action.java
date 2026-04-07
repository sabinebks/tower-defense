package choose.action;

import game.GameContext;

public abstract class Action {
    public abstract void execute(GameContext game);
    public abstract String getDescription();
}
