package test.object.tower.evolution;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;

import object.tower.*;
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
}
