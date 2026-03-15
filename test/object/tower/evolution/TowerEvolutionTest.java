package test.object.tower.evolution;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;

import object.tower.*;
import object.tower.evolution.*;
import board.*;

public class TowerEvolutionTest {
    private BombTower bombTower;
    private DartMonkey dartMonkey;
    private TackShooter tackShooter;
    private Gorilla gorilla;
    private EliteSniper eliteSniper;

    @BeforeEach
    void setUp() {
        bombTower = new BombTower(new Position(1, 1));
        dartMonkey = new DartMonkey(new Position(2, 2));
        tackShooter = new TackShooter(new Position(3, 3));
        gorilla = new Gorilla(new Position(4, 4));
        eliteSniper = new EliteSniper(new Position(5, 5));
    }

    @Test
    void testBombTowerInit() {
        assertNotNull(bombTower);
        assertEquals(1, bombTower.getPosition().getRow());
        assertEquals(1, bombTower.getPosition().getCol());
        assertEquals(600, bombTower.getCout());
        assertTrue(bombTower.getEvolution());
        assertNotNull(bombTower.getEvolutions());
    }

    @Test
    void testBombTowerEvolutionPuissance() {
        int cout = bombTower.appliquerEvolution(TypeEvolution.PUISSANCE);
        assertEquals(200, cout);
        assertTrue(bombTower.getEvolutions().getEvolutionsPossedees().containsKey(TypeEvolution.PUISSANCE));
    }

    @Test
    void testBombTowerEvolutionPortee() {
        int cout = bombTower.appliquerEvolution(TypeEvolution.PORTEE);
        assertEquals(250, cout);
        assertTrue(bombTower.getEvolutions().getEvolutionsPossedees().containsKey(TypeEvolution.PORTEE));
    }

    @Test
    void testBombTowerEvolutionCadence() {
        int cout = bombTower.appliquerEvolution(TypeEvolution.CANDENCE);
        assertEquals(300, cout);
        assertTrue(bombTower.getEvolutions().getEvolutionsPossedees().containsKey(TypeEvolution.CANDENCE));
    }

    @Test
    void testBombTowerEvolutionProjectiles() {
        int cout = bombTower.appliquerEvolution(TypeEvolution.PROJECTILES);
        assertEquals(400, cout);
        assertTrue(bombTower.getEvolutions().getEvolutionsPossedees().containsKey(TypeEvolution.PROJECTILES));
    }

    @Test
    void testDartMonkeyInit() {
        assertNotNull(dartMonkey);
        assertEquals(2, dartMonkey.getPosition().getRow());
        assertEquals(2, dartMonkey.getPosition().getCol());
        assertEquals(200, dartMonkey.getCout());
    }

    @Test
    void testDartMonkeyEvolutionPuissance() {
        int cout = dartMonkey.appliquerEvolution(TypeEvolution.PUISSANCE);
        assertEquals(250, cout);
        assertTrue(dartMonkey.getEvolutions().getEvolutionsPossedees().containsKey(TypeEvolution.PUISSANCE));
    }

    @Test
    void testDartMonkeyEvolutionPortee() {
        int cout = dartMonkey.appliquerEvolution(TypeEvolution.PORTEE);
        assertEquals(100, cout);
        assertTrue(dartMonkey.getEvolutions().getEvolutionsPossedees().containsKey(TypeEvolution.PORTEE));
    }

    @Test
    void testDartMonkeyEvolutionCadence() {
        int cout = dartMonkey.appliquerEvolution(TypeEvolution.CANDENCE);
        assertEquals(150, cout);
        assertTrue(dartMonkey.getEvolutions().getEvolutionsPossedees().containsKey(TypeEvolution.CANDENCE));
    }

    @Test
    void testDartMonkeyEvolutionPasDeProjectileEvolue() {
        int cout = dartMonkey.appliquerEvolution(TypeEvolution.PROJECTILES);
        assertEquals(0, cout);
    }
}
