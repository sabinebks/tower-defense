package choose.action;

import game.GameContext;

/**
 * do nothing action
 */
public class DoNothingAction extends Action {

    /**
     * do nothing
     * @param game the game context
     */
    @Override
    public void execute(GameContext game) {
    }

    /**
     * action description
     * @return description
     */
    @Override
    public String getDescription() {
        return "ne rien faire";
    }
}