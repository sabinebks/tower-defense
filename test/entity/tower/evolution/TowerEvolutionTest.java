package test.entity.tower.evolution;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;

import board.*;
import entity.tower.*;
import entity.tower.evolution.*;

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
        assertEquals(600, bombTower.getCost());
        assertTrue(bombTower.getEvolution());
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
        assertEquals(200, dartMonkey.getCost());
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

    @Test
    void testTackShooterInit() {
        assertNotNull(tackShooter);
        assertEquals(3, tackShooter.getPosition().getRow());
        assertEquals(3, tackShooter.getPosition().getCol());
        assertEquals(350, tackShooter.getCost());
    }

    @Test
    void testTackShooterEvolutionPortee() {
        int cout = tackShooter.appliquerEvolution(TypeEvolution.PORTEE);
        assertEquals(150, cout);
        assertTrue(tackShooter.getEvolutions().getEvolutionsPossedees().containsKey(TypeEvolution.PORTEE));
    }

    @Test
    void testTackShooterEvolutionCadence() {
        int cout = tackShooter.appliquerEvolution(TypeEvolution.CANDENCE);
        assertEquals(200, cout);
        assertTrue(tackShooter.getEvolutions().getEvolutionsPossedees().containsKey(TypeEvolution.CANDENCE));
    }

    @Test
    void testTackShooterEvolutionPasDePuissanceEvolue() {
        int cout = tackShooter.appliquerEvolution(TypeEvolution.PUISSANCE);
        assertEquals(0, cout);
    }

    @Test
    void testGorillaInit() {
        assertNotNull(gorilla);
        assertEquals(4, gorilla.getPosition().getRow());
        assertEquals(4, gorilla.getPosition().getCol());
        assertEquals(1200, gorilla.getCost());
    }

    @Test
    void testGorillaEvolutionPuissance() {
        int cout = gorilla.appliquerEvolution(TypeEvolution.PUISSANCE);
        assertEquals(600, cout);
        assertTrue(gorilla.getEvolutions().getEvolutionsPossedees().containsKey(TypeEvolution.PUISSANCE));
    }

    @Test
    void testGorillaEvolutionPortee() {
        int cout = gorilla.appliquerEvolution(TypeEvolution.PORTEE);
        assertEquals(400, cout);
        assertTrue(gorilla.getEvolutions().getEvolutionsPossedees().containsKey(TypeEvolution.PORTEE));
    }

    @Test
    void testGorillaEvolutionCadendeEvoluePasPossible() {
        int cout = gorilla.appliquerEvolution(TypeEvolution.CANDENCE);
        assertEquals(0, cout);
    }

    @Test
    void testEliteSniperCreation() {
        assertNotNull(eliteSniper);
        assertEquals(5, eliteSniper.getPosition().getRow());
        assertEquals(5, eliteSniper.getPosition().getCol());
        assertEquals(500, eliteSniper.getCost());
    }

    @Test
    void testEliteSniperEvolutionPuissance() {
        int cout = eliteSniper.appliquerEvolution(TypeEvolution.PUISSANCE);
        assertEquals(300, cout);
        assertTrue(eliteSniper.getEvolutions().getEvolutionsPossedees().containsKey(TypeEvolution.PUISSANCE));
    }

    @Test
    void testEliteSniperEvolutionCadence() {
        int cout = eliteSniper.appliquerEvolution(TypeEvolution.CANDENCE);
        assertEquals(200, cout);
        assertTrue(eliteSniper.getEvolutions().getEvolutionsPossedees().containsKey(TypeEvolution.CANDENCE));
    }

    @Test
    void testEliteSniperEvolutionPorteePeutPasEvolue() {
        int cout = eliteSniper.appliquerEvolution(TypeEvolution.PORTEE);
        assertEquals(0, cout);
    }

    @Test
    void testEliteSniperEvolutionPeutPasAcheterMemeEvolution() {
        int cout1 = dartMonkey.appliquerEvolution(TypeEvolution.PORTEE);
        int cout2 = dartMonkey.appliquerEvolution(TypeEvolution.PORTEE);

        assertEquals(100, cout1);
        assertEquals(0, cout2);
    }
}
