package test.object.balloon;

import board.Direction;
import board.Position;
import object.balloon.BalloonStraight;
import object.balloon.BalloonType;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BalloonStraightTest {

    @Test
    void testConstructorInitialisation() {
        Position start = new Position(2, 3); // Row 2, Col 3
        Position end = new Position(2, 6);
        BalloonStraight balloon = new BalloonStraight(1, start, end, BalloonType.BASIC);

        // posX = Col (3.5), posY = Ligne (2.5)
        assertEquals(3.5, balloon.getLigne(), 0.01);  
        assertEquals(2.5, balloon.getColonne(), 0.01);  
        assertEquals(Direction.RIGHT, balloon.getDirection());
    }

    @Test
    void testMoveRight() {
        Position start = new Position(0, 0);
        Position end = new Position(0, 3);
        BalloonStraight balloon = new BalloonStraight(1, start, end, BalloonType.MEDIUM);
        balloon.move(10, 10);
        assertEquals(0.5, balloon.getColonne(), 0.01);
        assertTrue(balloon.getLigne() > 0.5);
    }

    @Test
    void testMoveLeft() {
        Position start = new Position(0, 3);
        Position end = new Position(0, 0);
        BalloonStraight balloon = new BalloonStraight(1, start, end, BalloonType.BASIC);
        balloon.move(10, 10);
        // On vérifie que la ligne (Y) reste 0.5 et que X a diminué
        assertEquals(0.5, balloon.getColonne(), 0.01);
        assertTrue(balloon.getLigne() < 3.5);
    }

    @Test
    void testMoveDown() {
        Position start = new Position(0, 0);
        Position end = new Position(3, 0);
        BalloonStraight balloon = new BalloonStraight(1, start, end, BalloonType.STRONG);
        balloon.move(10, 10);
        assertEquals(0.5, balloon.getLigne(), 0.01);
        assertTrue(balloon.getColonne() > 0.5);
    }

    @Test
    void testMoveUp() {
        Position start = new Position(3, 0);
        Position end = new Position(0, 0);
        BalloonStraight balloon = new BalloonStraight(1, start, end, BalloonType.BASIC);
        balloon.move(10, 10);
        assertEquals(0.5, balloon.getLigne(), 0.01);
        assertTrue(balloon.getColonne() < 3.5);
    }

    @Test
    void testBalloonStopsAtEnd() {
        Position start = new Position(0, 0);
        Position end = new Position(0, 1);
        BalloonStraight balloon = new BalloonStraight(1, start, end, BalloonType.MEDIUM);
        int s = 0;
        while (balloon.move(10, 10) && s < 100) { s++; }
        assertFalse(balloon.isActive());
    }
}