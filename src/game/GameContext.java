package game;

import board.Board;
import player.Player;

/**
 * game context
 */
public interface GameContext {

    /**
     * get player
     * @return player
     */
    Player getPlayer();

    /**
     * get board
     * @return board
     */
    Board getBoard();
}