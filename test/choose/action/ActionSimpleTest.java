package test.choose.action;

import board.Board;
import board.Position;
import board.typeOfBoard.DefinedBoard;
import choose.AutoListChooser;
import choose.ListChooser;
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

    private Player createPlayer(int credit) {
        return new Player(credit, new AutoListChooser<>(), new AutoListChooser<>());
    }

    private GameContext ctx(Player player) {
        return new GameContext() {
            @Override
            public Player getPlayer() {
                return player;
            }

            @Override
            public Board getBoard() {
                return board;
            }
        };
    }

    @BeforeEach
    void setUp() {
        board = new DefinedBoard(6, 6);
    }

    @Test
    void testBuyTowerActionAcheteEtPlace() {
        Player player = createPlayer(1000);
        Tower tower = new DartMonkey(null);
        GameContext ctx = ctx(player);
        ListChooser<Position> fixedChooser = (prompt, options) -> options.get(0);

        BuyTowerAction action = new BuyTowerAction(tower, board, fixedChooser);
        action.execute(ctx);

        assertTrue(player.getTowers().contains(tower));
        assertTrue(board.getTowers().contains(tower));
        assertEquals(800, player.getCredit());
    }

    @Test
    void testBuyTowerActionRefuseSiPasDeCredit() {
        Player player = createPlayer(0);
        Tower tower = new DartMonkey(null);
        GameContext ctx = ctx(player);
        ListChooser<Position> fixedChooser = (prompt, options) -> options.get(0);

        BuyTowerAction action = new BuyTowerAction(tower, board, fixedChooser);
        action.execute(ctx);

        assertFalse(player.getTowers().contains(tower));
        assertFalse(board.getTowers().contains(tower));
        assertEquals(0, player.getCredit());
    }

    @Test
    void testSellTowerActionRetireEtRendCredits() {
        Player player = createPlayer(1000);
        Tower tower = new DartMonkey(null);
        player.buyTower(tower);
        board.placeTower(tower, new Position(0, 1));
        GameContext ctx = ctx(player);

        SellTowerAction action = new SellTowerAction(tower);
        action.execute(ctx);

        assertFalse(player.getTowers().contains(tower));
        assertFalse(board.getTowers().contains(tower));
        assertEquals(900, player.getCredit());
    }

    @Test
    void testUpgradeRetireLeCout() {
        Player player = createPlayer(1000);
        Tower tower = new DartMonkey(new Position(0, 0));
        GameContext ctx = ctx(player);
        Upgrade action = new Upgrade(tower, TypeEvolution.PORTEE);
        action.execute(ctx);

        assertEquals(900, player.getCredit());
    }

    @Test
    void testDownGradeRendLaMoitie() {
        Player player = createPlayer(1000);
        Tower tower = new DartMonkey(new Position(0, 0));
        player.buyTower(tower);
        tower.appliquerEvolution(TypeEvolution.PORTEE); // cout = 100
        GameContext ctx = ctx(player);
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

}
