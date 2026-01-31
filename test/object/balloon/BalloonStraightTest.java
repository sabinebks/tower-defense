package test.object.balloon;

import board.Direction;
import board.Position;
import object.balloon.BalloonStraight;
import object.balloon.BalloonType;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitaires pour la classe BalloonStraight.
 */
public class BalloonStraightTest {

    /**
     * Teste la création du ballon.
     * Vérifie que la position initiale, la direction et le type sont corrects.
     */
    @Test
    void testConstructorInitialisation() {
        Position start = new Position(2, 3);
        Position end = new Position(2, 6);
        BalloonStraight balloon = new BalloonStraight(1, start, end, 1.0, BalloonType.BASIC);

        // Vérifie la position initiale (centre de la cellule)
        assertEquals(3.5, balloon.getPosX());  // col + 0.5
        assertEquals(2.5, balloon.getPosY());  // row + 0.5

        // Vérifie la direction
        assertEquals(Direction.RIGHT, balloon.getDirection());

        // Vérifie la position de départ et d'arrivée
        assertEquals(start, balloon.getStartPos());
        assertEquals(end, balloon.getEndPos());
    }

    /**
     * Teste le déplacement vers la droite
     */
    @Test
    void testMoveRight() {
        Position start = new Position(0, 0);
        Position end = new Position(0, 3);
        BalloonStraight balloon = new BalloonStraight(1, start, end, 1.0, BalloonType.MEDIUM);

        // Après 1 déplacement
        balloon.move();
        assertEquals(0.5, balloon.getPosY());
        assertEquals(1.5, balloon.getPosX());
        assertTrue(balloon.move());  // Toujours actif tant qu'il n'a pas atteint la fin
    }

}
