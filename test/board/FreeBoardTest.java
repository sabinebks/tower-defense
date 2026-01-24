package test.board;

import board.*;
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
}