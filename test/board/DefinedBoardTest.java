package test.board;

import board.*;
import board.typeOfBoard.DefinedBoard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.RepeatedTest;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

/**
 * Test class for DefinedBoard.
 * Verifies the correct initialization of the board and the validity 
 * of the randomly generated path.
 */
public class DefinedBoardTest {
    
    /** The board instance used for testing. */
    private DefinedBoard b;
    
    /** Constant for board height. */
    private final int HEIGHT = 12;
    
    /** Constant for board width. */
    private final int WIDTH = 12;

    /**
     * Sets up a new DefinedBoard before each test.
     * The constructor automatically triggers path calculation.
     */
    @BeforeEach
    public void before() {
        b = new DefinedBoard(HEIGHT, WIDTH);
    }

    /**
     * Checks if the board dimensions are correct and if 
     * all cells in the grid are properly initialized.
     */
    @Test 
    public void boardEstCreeNormalement() {
        assertEquals(HEIGHT, b.getHeight());
        assertEquals(WIDTH, b.getWidth());

        for (int i = 0; i < b.getHeight(); i++) {
            for (int j = 0; j < b.getWidth(); j++) {
                assertNotNull(b.getCell(i, j));
            }
        }
    }

    /**
     * Verifies that the generated path meets the logic requirements.
     * It checks minimum length and ensures start/end points are on the borders.
     * This test is repeated to ensure stability across different random seeds.
     */
    @RepeatedTest(10)
    public void testValiditeDuChemin() {
        List<Cell> chemin = b.allCells();
        
     // Check minimum expected length
        int minLenExpected = (HEIGHT + WIDTH) / 2;
        assertTrue(chemin.size() >= minLenExpected, "The path is too short");

        // Ensure start and end cells are on the board boundaries
        assertTrue(b.isBord(chemin.get(0).getPosition()));
        assertTrue(b.isBord(chemin.get(chemin.size() - 1).getPosition()));
    }

    /**
     * Tests that the getCell method correctly throws an exception 
     * when accessed with out-of-bounds coordinates.
     */
    @Test 
    public void testGetCell() {
        assertThrows(IllegalArgumentException.class, () -> b.getCell(-1, 0));
        assertThrows(IllegalArgumentException.class, () -> b.getCell(HEIGHT, 0));
    }
}