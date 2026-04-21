package game;

import board.Board;
import board.Position;
import board.typeOfBoard.DefinedBoard;
import board.typeOfBoard.FreeBoard;
import choose.*;
import choose.action.Action;
import entity.balloon.*;
import player.*;
import round.*;

import java.util.*;
/*helper class to create game 
instances with different configurations.
 */
public class GameFactory {

    public static Game build(int height, int width, boolean isPlateauB, boolean isAuto) {

        Board board = isPlateauB
                ? new FreeBoard(height, width)
                : new DefinedBoard(height, width);

        ListChooser<Action> chooser = isAuto
                ? new AutoListChooser<>()
                : new HumanListChooser<>();

        Player player = new Player(2500, chooser);
        Shop shop = new Shop(board);

        List<Round> rounds = generateRounds(board);

        return new Game(player, board, rounds, 3);
    }
 /*it creates a game instance 
 by initializing the board, player, shop,
  and rounds based on the provided parameters.

  */
    private static List<Round> generateRounds(Board board) {
        List<Round> rounds = new ArrayList<>();
        List<Position> path = board.getPathPositions();

        for (int i = 1; i <= 10; i++) {
            rounds.add(new Round(i, generateBalloons(15, path)));
        }

        return rounds;
    }
/* it generates balloons for each round, 
assigning them a type in a round-robin fashion 
and setting their path based on the board's path positions.
 */
    private static List<Balloon> generateBalloons(int count, List<Position> path) {
        List<Balloon> list = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            Balloon b = new Balloon(i,
                    path.get(0).getRow() + 0.5,
                    path.get(0).getCol() + 0.5,
                    BalloonType.values()[i % BalloonType.values().length]);

            b.setPath(new ArrayList<>(path));
            list.add(b);
        }

        return list;
    }
}