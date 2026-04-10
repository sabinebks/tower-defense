package game;

import board.Board;
import player.Player;
/**
 * Interface pour fournir le contexte du jeu aux différentes classes
 * comme les tours, les ballons, etc.
 */
public interface GameContext {
    Player getPlayer();
    Board getBoard();
}
