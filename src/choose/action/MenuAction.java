// action/MenuAction.java
package choose.action;


import game.GameContext;

import java.util.List;

public class MenuAction extends Action {
    /**
     * Les attributs 
     * un description de la classe
     * une liste des actions 
     */
    private final String des;
    private final List<Action> actions;

    /**
     * Le menu des actions du jeu
     * @param des la description
     * @param actions les actions
     */
    public MenuAction(String des, List<Action> actions) {
        this.des = des;
        this.actions = actions;
    }

    @Override
    /**
     * Methode execute l'action de la classe
     * @param ctx le context du jeu
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
     * Description de la classe
     * @return la description de la classe
     */
    public String getDescription() { return des; }
}