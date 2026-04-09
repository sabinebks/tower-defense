package choose.action;

import game.GameContext;
/**
 * Classe abstraite des actions
 */
public abstract class Action {
    public abstract void execute(GameContext game);
    public abstract String getDescription();
}
