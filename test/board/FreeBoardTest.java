package test.board;

import board.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.RepeatedTest;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

/**
 * Classe de test pour FreeBoard.
 * Vérifie la génération correcte des chemins rectilignes.
 */
public class FreeBoardTest {
    
    private FreeBoard fb;
    private final int HEIGHT = 10;
    private final int WIDTH = 10;

    @BeforeEach
    public void setUp() {
        // Initialisation du plateau avec les dimensions définies
        fb = new FreeBoard(HEIGHT, WIDTH);
        // Initialise la grille de cellules
        fb.init();
        // Calcule les chemins aléatoires
        fb.calculerPath();
    }
}