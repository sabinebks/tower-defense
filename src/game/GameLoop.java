package game;

import player.Player;
import player.Shop;
import ui.GameRenderer;

/**
 * Boucle principale du jeu : gere les rounds, les phases d'achat, et l'affichage des resultats.
 */
public class GameLoop {

    private static final int REWARD_PER_ROUND = 150;

    private final Game   game;
    private final Player player;
    private final Shop   shop;
    private final boolean isHuman;
    private final BotStrategy bot;

    public GameLoop(GameSetup setup) {
        this.game    = setup.getGame();
        this.player  = setup.getPlayer();
        this.shop    = setup.getShop();
        this.isHuman = setup.isHuman();
        this.bot     = new BotStrategy(player, shop);
    }

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