package choose.action;

import game.GameContext;

public class QuitAction extends Action {
    @Override
    /**
     * Methode execute la quittance du jeu
     * @param ctx le context du jeu
     */
    public void execute(GameContext ctx) {
        System.out.println("Au revoir !");
        System.exit(0);
    }
    @Override
    /**
     * La description du quittance
     * @return une description
     */
    public String getDescription() { return "Quitter le jeu"; }
}
