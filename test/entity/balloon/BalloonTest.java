package test.entity.balloon;

import board.Position;
import entity.balloon.Balloon;
import entity.balloon.BalloonType;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BalloonTest {

    @Test
    void testConstructorInitialisation() {
        Balloon balloon = new Balloon(1, 2, 3, BalloonType.BASIC);


        assertEquals(2.5, balloon.getLigne(), 0.01);
        assertEquals(3.5, balloon.getColonne(), 0.01);
        assertEquals(1, balloon.getHealth());
        assertTrue(balloon.isActive());
    }

    @Test
    void testSetPathAndMove() {
        Balloon balloon = new Balloon(1, 0, 0, BalloonType.BASIC);

        List<Position> path = List.of(
                new Position(0, 1),
                new Position(0, 2));

        balloon.setPath(path);

        assertTrue(balloon.move());
    }

    @Test
    void testBalloonStopsAtEnd() {
        Balloon balloon = new Balloon(1, 0, 0, BalloonType.BASIC);

        List<Position> path = List.of(
                new Position(0, 1));

        balloon.setPath(path);

        int steps = 0;
        while (balloon.move() && steps < 100) {
            steps++;
        }

        assertFalse(balloon.isActive());
    }

    @Test
    void testFreezeStopsMovement() {
        Balloon balloon = new Balloon(1, 0, 0, BalloonType.BASIC);

        List<Position> path = List.of(
                new Position(0, 5));

        balloon.setPath(path);
        balloon.freeze(5);

        double before = balloon.getColonne();

        balloon.move();

        assertEquals(before, balloon.getColonne(), 0.001);
    }

   
  @Test
void testSlowReducesSpeed() {
    Balloon normal = new Balloon(1, 0, 0, BalloonType.BASIC);
    Balloon slowed = new Balloon(2, 0, 0, BalloonType.BASIC);

    List<Position> path = List.of(
        new Position(0, 5),
        new Position(0, 6)
    );

    normal.setPath(path);
    slowed.setPath(path);

    normal.move();

    slowed.slow(0.5, 5);
    slowed.move();

    assertTrue(slowed.getColonne() <= normal.getColonne());
}

    @Test
    void testHitReducesHealth() {
        Balloon balloon = new Balloon(1, 0, 0, BalloonType.MEDIUM);

        assertEquals(2, balloon.getHealth());

        boolean destroyed = balloon.hit(2);

        assertTrue(destroyed);
        assertFalse(balloon.isActive());
    }
}