package test.board;

import board.*;
import board.typeOfBoard.FreeBoard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.RepeatedTest;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

/**
 * Test class for FreeBoard.
 * Checks if straight paths are generated correctly.
 */
public class FreeBoardTest {
    
    private FreeBoard fb;
    private final int HEIGHT = 10;
    private final int WIDTH = 10;

    /**
     * Set up the board before each test.
     */
    @BeforeEach
    public void setUp() {
        fb = new FreeBoard(HEIGHT, WIDTH);
        fb.init(); 
        fb.calculerPath(); 
    }

    /**
     * Check if the board has the correct dimensions.
     */
    @Test
    public void testDimensionsInitiales() {
        assertEquals(HEIGHT, fb.getHeight(), "Height should be 10"); 
        assertEquals(WIDTH, fb.getWidth(), "Width should be 10"); 
    }

    /**
     * Check if at least one path was created.
     */
    @Test
    public void testNombreDeCheminsEstPositif() {
        assertTrue(fb.retourneNbrChemins() > 0, "There should be at least one path"); 
    }

    /**
     * Helper method to check if a position is on the board border.
     */
    private boolean isPointOnBorder(Position p) {
        return p.getRow() == 0 || p.getRow() == HEIGHT - 1 || 
               p.getCol() == 0 || p.getCol() == WIDTH - 1;
    }

    /**
     * Check if generated paths are valid and straight.
     */
    @RepeatedTest(10)
    public void testValiditeDesCheminsGeneres() {
        int nbrChemins = fb.retourneNbrChemins(); 
        Map<Position, Position> mapChemins = fb.getCheminsMap(); 

        assertEquals(nbrChemins, mapChemins.size());

        for (int i = 0; i < nbrChemins; i++) {
            Position[] extremites = fb.retourneLesChemins(i); 
            Position debut = extremites[0];
            Position fin = extremites[1];

            assertNotNull(debut);
            assertNotNull(fin);

            // Check if start and end are on the borders
            assertTrue(isPointOnBorder(debut));
            assertTrue(isPointOnBorder(fin));

            // Check if path is a straight line (same row or same column)
            assertTrue(debut.getRow() == fin.getRow() || debut.getCol() == fin.getCol());
   
            
            // Check if cells are correctly marked as path
            assertTrue(fb.getCell(debut.getRow(), debut.getCol()).isPath());
            assertTrue(fb.getCell(fin.getRow(), fin.getCol()).isPath());
        }
    }

    /**
     * Check if an exception is thrown for invalid path indexes.
     */
    @Test
    public void testRetourneLesCheminsException() {
        //
        assertThrows(IllegalArgumentException.class, () -> fb.retourneLesChemins(-1));
        assertThrows(IllegalArgumentException.class, () -> fb.retourneLesChemins(fb.retourneNbrChemins() + 10));
    }


}