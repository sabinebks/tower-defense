package test.object.tower;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import board.Position;
import object.balloon.*;
import object.tower.*;
import object.tower.projectil.Projectil;
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
        balloons.add(new BalloonStraight(1, new Position(1, 1), new Position(2, 2), BalloonType.BASIC));

        List<board.Cell> path = new ArrayList<>();
        path.add(new board.Cell(new Position(5, 6)));
        path.add(new board.Cell(new Position(5, 7)));
        
        // Utilisation de BalloonType.MEDIUM au lieu de BLUE
        balloons.add(new BalloonPath(2, path.get(0), BalloonType.MEDIUM, path));
    }

    @Test
    void testTowerPosition() {
        assertEquals(0, dartTower.getPosition().getRow());
        assertEquals(0, dartTower.getPosition().getCol());
    }

    @Test
    void testTowerDistanceCalculation() {
        Balloon b = balloons.get(0);
        double distance = dartTower.distanceTo(b);
        assertEquals(Math.sqrt(2), distance, 0.001);
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
        balloons.add(new BalloonStraight(3, loin, new Position(51, 51), BalloonType.STRONG));
        
        Balloon target = dartTower.findTarget(balloons);
        
        if (dartTower.getPorte() != -1 && dartTower.getPorte() < 50) {
            assertNull(target);
        }
    }
}