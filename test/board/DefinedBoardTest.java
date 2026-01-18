package test.board;

import board.*;
import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DefinedBoardTest {
    
    private Board b;

    @BeforeEach
    public void before() {
        b = new DefinedBoard(12, 12);
    }

    @Test 
    public void BoardEstCreeNormalement() {
        assertEquals( 12, b.getHeight());
        assertEquals ( 12, b.getWidth());

        for ( int i = 0; i < b.getHeight(); i++) {
            for ( int j = 0; j < b.getWidth(); j++) {
                assertNotNull(b.getCell(i, j));
                assertNotNull(b.getCell(i, j).getPosition());
            }
        }
    }

    @Test 
    public void GetCellDehorsDuBoard() {
        assertThrow( IllegalArgumentException.class, () -> b.getCell( -1, 0));
        assertThrow( IllegalArgumentException.class, () -> b.getCell( 0, -2));
        assertThrow( IllegalArgumentException.class, () -> b.getCell( 13, 0));
        assertThrow( IllegalArgumentException.class, () -> b.getCell( 0, 13));
    }

    @Test 
    public void AssignCheminTest() {
        b.getCell(5, 5).assignChemin();
        assertTrue(b.getCell(5, 5).isPath());
        assertFalse(b.getCell(6, 6).isPath());
    }
}
