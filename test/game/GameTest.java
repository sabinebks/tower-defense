package test.game;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import game.Game;
import board.Position;
import object.balloon.BalloonType;
import object.tower.*;

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
    }

    @Test
    void testSpawnMultipleBalloons() {
        game.autoPlayA(10, 10, 0);
        assertDoesNotThrow(() -> game.spawnBalloon(BalloonType.BASIC));
        assertDoesNotThrow(() -> game.spawnBalloon(BalloonType.MEDIUM));
        assertDoesNotThrow(() -> game.spawnBalloon(BalloonType.STRONG));
    }

    @Test
    void testAddTower() {
        game.autoPlayA(10, 10, 0);
        game.addTower(new DartMonkey(new Position(1, 1)));
        assertDoesNotThrow(() -> game.gameTick());
    }

    @Test
    void testAddMultipleTowers() {
        game.autoPlayA(10, 10, 0);
        assertDoesNotThrow(() -> game.addTower(new DartMonkey(new Position(1, 1))));
        assertDoesNotThrow(() -> game.addTower(new IceTower(new Position(2, 2))));
        assertDoesNotThrow(() -> game.addTower(new BombTower(new Position(3, 3))));
        assertDoesNotThrow(() -> game.addTower(new SlowTower(new Position(4, 4))));
    }

    @Test
    void testGameTickLogic() {
        game.autoPlayA(5, 5, 2);
        assertDoesNotThrow(() -> game.gameTick());
    }

    @Test
    void testMultipleGameTick() {
        game.autoPlayA(8, 8, 2);
        assertDoesNotThrow(() -> {
            for (int i = 0; i < 5; i++) {
                game.gameTick();
            }
        });
    }

    @Test
    void testAutoPlayA() {
        assertDoesNotThrow(() -> game.autoPlayA(8, 8, 3));
    }

    @Test
    void testAutoPlayB() {
        assertDoesNotThrow(() -> game.autoPlayB(8, 8, 3));
    }

    @Test
    void testMultipleTowerInMultipleTick() {
        game.autoPlayA(10, 10, 3);
        game.addTower(new DartMonkey(new Position(1, 1)));
        game.addTower(new IceTower(new Position(2, 2)));
        assertDoesNotThrow(() -> {
            for (int i = 0; i < 5; i++) {
                game.gameTick();
            }
        });
    }
}