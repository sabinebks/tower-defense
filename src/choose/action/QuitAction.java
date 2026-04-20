package choose.action;

import game.GameContext;
/**
 * quit action
 */
public class QuitAction extends Action {

    @Override
    /**
     * execute quit action
     * @param ctx the game context
     */
    public void execute(GameContext ctx) {
        System.out.println("au revoir !");
        System.exit(0);
    }

    @Override
    /**
     * description
     * @return description
     */
    public String getDescription() {
        return "quitter le jeu";
    }

    @Override
    public boolean isAllowedForAuto() {
        return false;
    }
}