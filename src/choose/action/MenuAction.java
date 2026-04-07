// action/MenuAction.java
package choose.action;


import game.GameContext;

import java.util.List;

public class MenuAction extends Action {
    private final String label;
    private final List<Action> sousActions;

    public MenuAction(String label, List<Action> sousActions) {
        this.label = label;
        this.sousActions = sousActions;
    }

    @Override
    public void execute(GameContext ctx) {
        boolean done = false;
        while (!done) {
            Action chosen = ctx.getPlayer().chooseAction(sousActions, ctx);
            if (chosen instanceof RetourAction) {
                done = true;
            } else {
                chosen.execute(ctx);
                done = !(chosen instanceof MenuAction);
            }
        }
    }

    @Override
    public String getDescription() { return label; }
}