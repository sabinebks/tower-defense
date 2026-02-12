package test.object.tower;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import board.Position;
import object.balloon.BalloonStraight;
import object.balloon.BalloonPath;
import object.tower.DartMonkey;
import object.tower.IceTower;
import object.tower.Tower;

import java.util.ArrayList;
import java.util.List;

class TowerTest {

    private Tower dartTower;
    private Tower iceTower;
    private List<BalloonStraight> balloonsStraight;
    private List<BalloonPath> balloonsPath;

    @BeforeEach
    void setUp() {
        // Création de positions pour les tours
        Position pos1 = new Position(0, 0);
        Position pos2 = new Position(5, 5);

        // Sous-classes de Tower avec constructeurs simples
        dartTower = new DartMonkey(pos1);
        iceTower = new IceTower(pos2);

        // Ballons droits (exemple : départ (1,1) vers (10,10))
        balloonsStraight = new ArrayList<>();
        balloonsStraight.add(new BalloonStraight(1, new Position(1,1), new Position(10,10), 1.0, null));
        balloonsStraight.add(new BalloonStraight(2, new Position(2,2), new Position(10,10), 1.0, null));

        // Ballons sur chemin (exemple : chemin minimal)
        balloonsPath = new ArrayList<>();
        List<board.Cell> path1 = new ArrayList<>();
        path1.add(new board.Cell(new Position(0, 1)));
        path1.add(new board.Cell(new Position(0, 2)));
        balloonsPath.add(new BalloonPath(1, new board.Cell(new Position(0, 1)), 1.0, null, path1));
    }

    @Test
    void testTowerPosition() {
        assertEquals(0, dartTower.getPosition().getRow());
        assertEquals(0, dartTower.getPosition().getCol());

        assertEquals(5, iceTower.getPosition().getRow());
        assertEquals(5, iceTower.getPosition().getCol());
    }

    @Test
    void testTowerDistanceCalculation() {
        BalloonStraight balloon = balloonsStraight.get(0);
        double distance = dartTower.distanceTo(balloon);

        // Distance entre (0,0) et (1,1) = sqrt(2)
        assertEquals(Math.sqrt(2), distance, 0.001);
    }

    @Test
    void testFindTarget() {
        // Comme findTarget prend List<Balloon>, on peut passer les deux types
        object.balloon.Balloon target = dartTower.findTarget(new ArrayList<>(balloonsStraight));
        assertNotNull(target);
        assertTrue(dartTower.distanceTo(target) <= dartTower.getPorte() || dartTower.getPorte() == -1);
    }

    @Test
    void testTowerAttackOnStraightBalloon() {
        // On simule quelques ticks pour que la tour tire
        for (int i = 0; i < 10; i++) {
            dartTower.update(new ArrayList<>(balloonsStraight));
        }

        // Au moins un ballon doit être touché
        boolean anyInactive = balloonsStraight.stream().anyMatch(b -> !b.isActive());
        assertTrue(anyInactive);
    }

    @Test
    void testTowerAttackOnPathBalloon() {
        for (int i = 0; i < 10; i++) {
            iceTower.update(new ArrayList<>(balloonsPath));
        }

        boolean anyInactive = balloonsPath.stream().anyMatch(b -> !b.isActive());
        assertTrue(anyInactive);
    }
}
