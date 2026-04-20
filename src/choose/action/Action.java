package choose.action;

import game.GameContext;

/**
 * abstract class for game actions
 * every action must define how it works and its description
 */
public abstract class Action {
    /**
     * executes the action in the game
     *
     * @param game the current game context
     */
    public abstract void execute(GameContext game);
   
    /**
     * gives a short description of the action
     *
     * @return description of the action
     */
    public abstract String getDescription();

    /**
     * says if the action is allowed in auto mode
     *
     * @return true by default
     */
    public boolean isAllowedForAuto() {
        return true;
    }
}