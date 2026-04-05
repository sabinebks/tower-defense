package game;

import board.Board;
import player.Player;

public interface GameContext {
    Player getPlayer();
    Board getBoard();
}
