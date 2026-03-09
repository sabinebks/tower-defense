package test.object.balloon;

import board.Cell;
import board.Position;
import java.util.List;
import object.balloon.BalloonPath;
import object.balloon.BalloonType;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BalloonPathTest {

    @Test
    void testConstructorInitialisation() {
        Cell start = new Cell(new Position(2, 3)); 
        BalloonPath balloon = new BalloonPath(1, start, BalloonType.BASIC, List.of());

        assertFalse(balloon.isActive());
        assertEquals(2.5, balloon.getLigne(), 0.01);
        assertEquals(3.5, balloon.getColonne(), 0.01);
        assertEquals(0, balloon.getPathIndex());
    }

    @Test
    void testReachCellCenter() {
        Cell start = new Cell(new Position(0, 0));
        Cell target = new Cell(new Position(0, 1)); // Row 0, Col 1
        BalloonPath balloon = new BalloonPath(1, start, BalloonType.MEDIUM, List.of(target));

        balloon.move(10, 10);

        assertEquals(0.75, balloon.getColonne(), 0.01);
        assertEquals(0.5, balloon.getLigne(), 0.01);
        assertEquals(0, balloon.getPathIndex());
    }

    @Test
    void testMoveReturnsFalseWhenInactive() {
        Cell start = new Cell(new Position(0, 0));
        BalloonPath balloon = new BalloonPath(1, start, BalloonType.BASIC, List.of());
        while(balloon.move(10, 10)) {}
        assertFalse(balloon.move(10, 10));
    }

    @Test
    void testBalloonExitAfterPath() {
        Cell start = new Cell(new Position(0, 0));
        BalloonPath balloon = new BalloonPath(1, start, BalloonType.BASIC, List.of());
        for (int i = 0; i < 20; i++) {
            balloon.move(10, 10);
        }
        assertFalse(balloon.isActive());
    }
}