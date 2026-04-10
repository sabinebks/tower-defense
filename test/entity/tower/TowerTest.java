package test.entity.tower;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import board.Position;
import entity.balloon.*;
import entity.tower.*;
import entity.tower.projectil.Projectil;

import java.util.ArrayList;
import java.util.List;

class TowerTest {

    private Tower dartTower;
    private Tower iceTower;
    private List<Balloon> balloons;
    private List<Projectil> activeProjectiles;

    @BeforeEach
    void setUp() {
        Position pos1 = new Position(0, 0);
        Position pos2 = new Position(5, 5);

        dartTower = new DartMonkey(pos1);
        iceTower = new IceTower(pos2);

        balloons = new ArrayList<>();
        activeProjectiles = new ArrayList<>();

        // Utilisation de BalloonType.BASIC au lieu de RED

        // Balloon 1
        Balloon b1 = new Balloon(1, 1, 1, BalloonType.BASIC);
        b1.setPath(List.of(
                new Position(1, 2),
                new Position(1, 3)));
        balloons.add(b1);

        // Balloon 2
        Balloon b2 = new Balloon(2, 5, 6, BalloonType.MEDIUM);
        b2.setPath(List.of(
                new Position(5, 7),
                new Position(5, 8)));
        balloons.add(b2);
    }

    @Test
    void testTowerPosition() {
        assertEquals(0, dartTower.getPosition().getRow());
        assertEquals(0, dartTower.getPosition().getCol());
    }

@Test
void testTowerDistanceCalculation() {
    Balloon b = new Balloon(3, 3, 4, BalloonType.BASIC);

    double expected = Math.sqrt(
        Math.pow(dartTower.getPosition().getRow() + 0.5 - b.getLigne(), 2) +
        Math.pow(dartTower.getPosition().getCol() + 0.5 - b.getColonne(), 2)
    );

    double dist = dartTower.distanceTo(b);

    assertEquals(expected, dist, 0.01);
}
  

    @Test
    void testFindTarget() {
        Balloon target = dartTower.findTarget(balloons);
        assertNotNull(target);

        double dist = dartTower.distanceTo(target);
        assertTrue(dist <= dartTower.getPorte() || dartTower.getPorte() == -1);
    }

    @Test
    void testTowerUpdateGeneratesProjectile() {
        Balloon close = new Balloon(99, 0, 1, BalloonType.BASIC);
        close.setPath(List.of(new Position(0, 2)));
        balloons.add(close);
        try {
            for (int i = 0; i < 10; i++) {
                dartTower.update(balloons, activeProjectiles);
            }
        } catch (Exception e) {
            fail(e.getMessage());
        }
        assertFalse(activeProjectiles.isEmpty());
    }

    @Test
    void testAttackLogic() {
        Position loin = new Position(50, 50);
        balloons.clear();
        Balloon b = new Balloon(3, 50, 50, BalloonType.STRONG);
        b.setPath(List.of(
                new Position(51, 51),
                new Position(52, 52)));
        balloons.add(b);

        Balloon target = dartTower.findTarget(balloons);

        if (dartTower.getPorte() != -1 && dartTower.getPorte() < 50) {
            assertNull(target);
        }
    }
}