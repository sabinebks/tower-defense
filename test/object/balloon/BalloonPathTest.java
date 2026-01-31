package object.balloon;

import board.Cell;
import board.Position;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BalloonPathTest {

    /**
     * Test du constructeur.
     * Vérifie que le ballon est bien initialisé :
     * - actif
     * - position X et Y correctes (centre de la cellule)
     * - pathIndex initial = 0
     */
    @Test
    void testConstructorInitialisation() {
        Cell start = new Cell(new Position(2, 3));

        BalloonPath balloon = new BalloonPath(1, start, 1.0, BalloonType.BASIC, List.of());

        assertTrue(balloon.isActive());
        assertEquals(3.5, balloon.getPosX());
        assertEquals(2.5, balloon.getPosY());
        assertEquals(0, balloon.getPathIndex());
    }

    /**
     * Test du déplacement vers la première cellule du chemin
     * Vérifie que :
     * - le ballon atteint le centre de la cellule cible si la vitesse est suffisante
     * - le pathIndex est incrémenté
     */

    @Test
    void testReachCellCenter() {
        Cell start = new Cell(new Position(0, 0));
        Cell target = new Cell(new Position(0, 1));

        BalloonPath balloon = new BalloonPath(1, start, 2.0, BalloonType.MEDIUM,List.of(target));

        balloon.move();

        assertEquals(0.5, balloon.getPosY());
        assertEquals(3.5, balloon.getPosX());
        assertEquals(1, balloon.getPathIndex());
    }

    /**
     * Test du comportement lorsqu'un ballon est inactif
     * - On déplace le ballon jusqu'à ce qu'il sorte du plateau
     * - Après cela, move() doit retourner false
     */

    @Test
    void testMoveReturnsFalseWhenInactive() {
        Cell start = new Cell(new Position(0, 0));

        BalloonPath balloon = new BalloonPath(1, start, 5.0, BalloonType.BASIC, List.of());

        while(balloon.move()) {

        }

        assertFalse(balloon.move());
    }

    /**
     * Test de la sortie complète du plateau.
     * Vérifie que :
     * - le ballon continue de se déplacer après la fin du chemin
     * - il est désactivé après avoir dépassé 20 cases à droite
     */
    @Test
    void testBalloonExitAfterPath() {
        Cell start = new Cell(new Position(0, 0));
        BalloonPath balloon = new BalloonPath(1, start, 5.0, BalloonType.BASIC, List.of());

        // On simule plusieurs moves pour dépasser 20 cases
        for (int i = 0; i < 10; i++) {
            balloon.move();
        }

        // Le ballon doit être désactivé après être sorti du plateau
        assertFalse(balloon.isActive());
    }
}