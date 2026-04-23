package game;

import player.Player;
import player.Shop;
import ui.GameRenderer;

/**
 * Boucle principale du jeu : gere les rounds, les phases d'achat, et l'affichage des resultats.
 */
public class GameLoop {

    private static final int REWARD_PER_ROUND = 150;

    /**
     * The game we are playing
     * The player
     * The games shop
     * Verify if a human is playing
     * The bot
     */
    private final Game   game;
    private final Player player;
    private final Shop   shop;
    private final boolean isHuman;
    private final BotStrategy bot;

    /**
     * Constructor
     * @param setup the game setup
     */
    public GameLoop(GameSetup setup) {
        this.game    = setup.getGame();
        this.player  = setup.getPlayer();
        this.shop    = setup.getShop();
        this.isHuman = setup.isHuman();
        this.bot     = new BotStrategy(player, shop);
    }

    /**
     * Function to start the game
     * This function is the loop that will run for a number of round
     */
    public void start() {
        GameRenderer.printBanner(player);
        if (!isHuman) bot.placeInitialTowers(game);
        GameRenderer.printInitialBuyPhase();
        player.runActionPhase(game, shop);

        for (int i = 0; i < game.getRoundCount(); i++) {
            GameRenderer.printRoundHeader(i + 1, player);
            if (!isHuman) bot.applyRoundEvolution(i + 1, game);
            game.runOneRound();
            if (game.hasPlayerLost()) { GameRenderer.printGameOver(); return; }
            GameRenderer.printRoundResult(game.getEscapedCount(), player, REWARD_PER_ROUND);
            if (i < game.getRoundCount() - 1) { GameRenderer.printActionPhase(); player.runActionPhase(game, shop); }
        }
        GameRenderer.printVictory(player.getLives());
    }
}