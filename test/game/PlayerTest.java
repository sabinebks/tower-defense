package test.game;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import game.*;
import board.*;
import object.tower.*;
import object.tower.evolution.TypeEvolution;


public class PlayerTest {
    private Player player;

    @BeforeEach
    void init() {
        player = new Player();
    }

    @Test
    void testVieEtCreditQuandInit() {
        assertEquals(2500, player.getCredit());
        assertEquals(20, player.getVie());
    }

    @Test
    void testSommeDunTower() {
        Tower t = new DartMonkey(new Position(5, 5)); 
        assertEquals(200, player.calculInvestissementTotal(t));
    }

    @Test
    void testSommeDunTowerAvecUpgrade() {
        Tower t = new DartMonkey(new Position(5, 5)); 
        t.appliquerEvolution(TypeEvolution.PORTEE);
        t.appliquerEvolution(TypeEvolution.CANDENCE);
        assertEquals(450, player.calculInvestissementTotal(t));
    }
}
