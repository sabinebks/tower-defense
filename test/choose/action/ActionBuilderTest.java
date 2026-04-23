package test.choose.action;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import choose.action.*;
import choose.AutoListChooser;
import player.Player;
import player.Shop;
import board.typeOfBoard.DefinedBoard;
import board.Board;

import java.util.List;

public class ActionBuilderTest {

    private Player createPlayer(int credit) {
        return new Player(credit, new AutoListChooser<>(), new AutoListChooser<>());
    }

    @Test
    void testBuildMenu() {
        Player player = createPlayer(1000);
        Board board = new DefinedBoard(5, 5);
        board.init();
        Shop shop = new Shop(board);
        

        List<Action> menu = ActionBuilder.buildMenu(player, shop);

        assertNotNull(menu);
        assertTrue(menu.size() > 0);
    }

    @Test
    void testBuildAcheterTour() {
        Player player = createPlayer(1000);
        Board board = new DefinedBoard(5, 5);
        board.init();

        Shop shop = new Shop(board);
        Action action = ActionBuilder.buildAcheterTour(player, shop);

        assertNotNull(action);
    }

    @Test
    void testBuildEvoluerTour() {
        Player player = createPlayer(1000);

        Action action = ActionBuilder.buildEvoluerTour(player);

        assertNotNull(action);
    }

    @Test
    void testBuildVendreTour() {
        Player player = createPlayer(1000);

        Action action = ActionBuilder.buildVendreTour(player);

        assertNotNull(action);
    }
}
