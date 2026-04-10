package test.choose.action;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import choose.action.*;
import player.Player;
import player.Shop;
import board.typeOfBoard.DefinedBoard;
import board.Board;

import java.util.List;

public class ActionBuilderTest {

    @Test
    void testBuildMenu() {
        Player player = new Player(1000, null);
        Board board = new DefinedBoard(5, 5);
        board.init();
        Shop shop = new Shop(board);
        

        List<Action> menu = ActionBuilder.buildMenu(player, shop);

        assertNotNull(menu);
        assertTrue(menu.size() > 0);
    }

    @Test
    void testBuildAcheterTour() {
        Player player = new Player(1000, null);
        Board board = new DefinedBoard(5, 5);
        board.init();

        Shop shop = new Shop(board);
        Action action = ActionBuilder.buildAcheterTour(player, shop);

        assertNotNull(action);
    }

    @Test
    void testBuildEvoluerTour() {
        Player player = new Player(1000, null);

        Action action = ActionBuilder.buildEvoluerTour(player);

        assertNotNull(action);
    }

    @Test
    void testBuildVendreTour() {
        Player player = new Player(1000, null);

        Action action = ActionBuilder.buildVendreTour(player);

        assertNotNull(action);
    }
}