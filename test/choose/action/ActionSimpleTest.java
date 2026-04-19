package test.choose.action;

import board.Board;
import board.Position;
import board.typeOfBoard.DefinedBoard;
import choose.action.BuyTowerAction;
import choose.action.DoNothingAction;
import choose.action.DownGrade;
import choose.action.QuitAction;
import choose.action.RetourAction;
import choose.action.SellTowerAction;
import choose.action.Upgrade;
import entity.tower.DartMonkey;
import entity.tower.Tower;
import entity.tower.evolution.TypeEvolution;
import game.GameContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import player.Player;

import static org.junit.jupiter.api.Assertions.*;

public class ActionSimpleTest {

    private Board board;

    @BeforeEach
    void setUp() {
        board = new DefinedBoard(6, 6);
    }

    @Test
    void testBuyTowerActionAcheteEtPlace() {
        Player player = new Player(1000, null);
        Tower tower = new DartMonkey(null);
        GameContext ctx = new FakeContext(player, board);

        BuyTowerAction action = new BuyTowerAction(tower, new Position(0, 0));
        action.execute(ctx);

        assertTrue(player.getTowers().contains(tower));
        assertTrue(board.getTowers().contains(tower));
        assertEquals(800, player.getCredit());
    }

    @Test
    void testBuyTowerActionRefuseSiPasDeCredit() {
        Player player = new Player(0, null);
        Tower tower = new DartMonkey(null);
        GameContext ctx = new FakeContext(player, board);

        BuyTowerAction action = new BuyTowerAction(tower, new Position(0, 0));
        action.execute(ctx);

        assertFalse(player.getTowers().contains(tower));
        assertFalse(board.getTowers().contains(tower));
        assertEquals(0, player.getCredit());
    }

    @Test
    void testSellTowerActionRetireEtRendCredits() {
        Player player = new Player(1000, null);
        Tower tower = new DartMonkey(null);
        player.buyTower(tower);
        board.placeTower(tower, new Position(0, 1));
        GameContext ctx = new FakeContext(player, board);

        SellTowerAction action = new SellTowerAction(tower);
        action.execute(ctx);

        assertFalse(player.getTowers().contains(tower));
        assertFalse(board.getTowers().contains(tower));
        assertEquals(900, player.getCredit());
    }

    @Test
    void testUpgradeRetireLeCout() {
        Player player = new Player(1000, null);
        Tower tower = new DartMonkey(new Position(0, 0));
        GameContext ctx = new FakeContext(player, board);
        Upgrade action = new Upgrade(tower, TypeEvolution.PORTEE);
        action.execute(ctx);

        assertEquals(900, player.getCredit());
    }

    @Test
    void testDownGradeRendLaMoitie() {
        Player player = new Player(1000, null);
        Tower tower = new DartMonkey(new Position(0, 0));
        player.buyTower(tower);
        tower.appliquerEvolution(TypeEvolution.PORTEE); // cout = 100
        GameContext ctx = new FakeContext(player, board);
        DownGrade action = new DownGrade(tower, TypeEvolution.PORTEE);
        action.execute(ctx);
        assertEquals(850, player.getCredit());
    }

    @Test
    void testDoNothingRetourQuitDescriptions() {
        DoNothingAction doNothing = new DoNothingAction();
        RetourAction retour = new RetourAction();
        QuitAction quit = new QuitAction();

        assertEquals("Ne rien faire", doNothing.getDescription());
        assertEquals(" Retour", retour.getDescription());
        assertEquals("Quitter le jeu", quit.getDescription());
        assertFalse(quit.isAllowedForAuto());
    }

    private static class FakeContext implements GameContext {
        private final Player player;
        private final Board board;

        FakeContext(Player player, Board board) {
            this.player = player;
            this.board = board;
        }

        @Override
        public Player getPlayer() {
            return player;
        }

        @Override
        public Board getBoard() {
            return board;
        }
    }
}
