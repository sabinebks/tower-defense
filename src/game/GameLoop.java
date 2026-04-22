package game;

import player.Player;
import player.Shop;

public class GameLoop {

    private final Game game;
    private final Player player;
    private final Shop shop;

    private static final int REWARD = 150;
    /** this class manages the main game loop,
     * alternating between action and combat phases
     * until the player loses
     * @param game the game we playing
     * @param player the player
     * @param shop the shop of the game
    */
    public GameLoop(Game game, Player player, Shop shop) {
        this.game = game;
        this.player = player;
        this.shop = shop;
    }
     /*
        * main game loop
      */
    public void run() {

        System.out.println("=== DEBUT DU JEU ===");

        while (player.isAlive()) {

            System.out.println("\n=== PHASE ACTION ===");
            player.runActionPhase(game, shop);
            System.out.println("\n=== PHASE COMBAT ===");
            game.runOneRound();
            if (game.hasPlayerLost()) {
                System.out.println("GAME OVER");
                return;
            }
            if (!game.roundPasFini()) {
                break;
            }
            player.addCredit(REWARD);
            System.out.println("Recompense : +" + REWARD + " credits");
        }

        System.out.println("FIN DU JEU");
    }
}