package test.game;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import game.Game;
import board.Position;
import object.balloon.BalloonType;
import object.tower.DartMonkey;

public class GameTest {

    private Game game;

    @BeforeEach
    void setUp() {
        game = new Game();
    }

    @Test
    void testInitialisation() {
        assertNotNull(game);
    }

    @Test
    void testSpawnBalloon() {
        game.autoPlayA(10, 10, 0);
        assertDoesNotThrow(() -> game.spawnBalloon(BalloonType.BASIC));
        game.autoPlayA(10, 10, 0);
        assertDoesNotThrow(() -> game.spawnBalloon(BalloonType.MEDIUM));
        game.autoPlayA(10, 10, 0);
        assertDoesNotThrow(() -> game.spawnBalloon(BalloonType.STRONG));
    }

    @Test
    void testAddTower() {
        game.autoPlayA(10, 10, 0);
        game.addTower(new DartMonkey(new Position(1, 1)));
        assertDoesNotThrow(() -> game.gameTick());
    }

    @Test
    void testGameTickLogic() {
        game.autoPlayA(5, 5, 2);
        assertDoesNotThrow(() -> game.gameTick());
    }

    @Test
    void testAutoPlayA() {
        assertDoesNotThrow(() -> game.autoPlayA(8, 8, 3));
    }

    @Test
    void testAutoPlayB() {
        assertDoesNotThrow(() -> game.autoPlayB(8, 8, 3));
    }
}