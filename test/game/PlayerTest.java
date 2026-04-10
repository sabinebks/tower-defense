package test.game;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
<<<<<<< HEAD
import game.*;
import board.*;
import entity.tower.*;
import entity.tower.evolution.TypeEvolution;
=======
import player.*;
>>>>>>> origin/main


public class PlayerTest {
    private Player player;

    @BeforeEach
    void init() {
        player = new Player(2500, new choose.AutoListChooser<>());
    }

    @Test
    void testVieEtCreditQuandInit() {
        assertEquals(2500, player.getCredit());
        assertEquals(20, player.getLives());
    }
}
