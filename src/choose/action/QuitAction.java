package choose.action;

import game.GameContext;

public class QuitAction extends Action {
    @Override
    public void execute(GameContext ctx) {
        System.out.println("Au revoir !");
        System.exit(0);
    }
    @Override
    public String getDescription() { return "Quitter le jeu"; }
}
