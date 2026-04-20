package choose.action;

import game.GameContext;

import java.util.List;

/**
 * menu action
 */
public class MenuAction extends Action {

    /**
     * description
     * list of actions
     */
    private final String des;
    private final List<Action> actions;

    /**
     * constructor
     * @param des the description
     * @param actions the actions list
     */
    public MenuAction(String des, List<Action> actions) {
        this.des = des;
        this.actions = actions;
    }

    @Override
    /**
     * execute action
     * @param ctx the game context
     */
    public void execute(GameContext ctx) {
        boolean done = false;

        while (!done) {
            Action a = ctx.getPlayer().chooseAction(actions, ctx);

            if (a instanceof RetourAction) {
                done = true;
            } else {
                a.execute(ctx);
                done = !(a instanceof MenuAction);
            }
        }
    }

    @Override
    /**
     * description
     * @return description
     */
    public String getDescription() {
        return des;
    }
}