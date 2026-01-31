package object.balloon;

import board.Cell;
import board.Position;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BalloonPathTest {

    @Test
    void testConstructorInitialisation() {
        Cell start = new Cell(new Position(2, 3));

        BalloonPath balloon = new BalloonPath(1, start, 1.0, BalloonType.BASIC, List.of());

        assertTrue(balloon.isActive());
        assertEquals(3.5, balloon.getPosX());
        assertEquals(2.5, balloon.getPosY());
        assertEquals(0, balloon.getPathIndex());
    }

    @Test
    void testMoveReturnsFalseWhenInactive() {
        Cell start = new Cell(new Position(0, 0));

        BalloonPath balloon = new BalloonPath(1, start, 5.0, BalloonType.BASIC, List.of());

        while(balloon.move()) {

        }

        assertFalse(balloon.move());
    }

}
