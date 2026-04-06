package game;

import board.*;
import board.typeOfBoard.*;
import clock.Clock;
import clock.ClockListener;
import entity.balloon.*;
import entity.tower.*;
import entity.tower.evolution.TypeEvolution;
import entity.tower.projectil.*;
import player.Player;
import round.Round;
import round.RoundManager;
import ui.BoardRenderer;

import java.util.*;

public class Game implements ClockListener, GameContext {
    private final Board board;
    private final Player player;
    private final RoundManager roundManager;
    private final Clock clock;
    private volatile boolean isRunning;
    private final BoardRenderer renderer;

    public Game(Player player, Board board, List<Round> rounds, int fps) {
        this.player = player;
        this.board = board;
        this.renderer = new BoardRenderer();
        this.roundManager = new RoundManager(rounds, board);
        this.clock = new Clock(fps);
        this.clock.addListener(this);
    }

    @Override
    public void onTick(int tickCount) {
        if (!isRunning) return;

        System.out.println("TICK " + tickCount);
        roundManager.update(tickCount);
        List<Balloon> killed = board.update();
        for (Balloon b : killed) {
            int reward = b.getType().reward;
            player.addCredit(reward);
            System.out.println(">>> +" + reward + " crédits ! Total: " + player.getCredit());
        }

        this.renderer.render(board);
    }

    @Override
    public Player getPlayer() { return player; }
    @Override
    public Board getBoard()   { return board; }

    public void runOneRound() {
        clock.reset();
        isRunning = true;
        clock.start();

        // Attendre que le premier tick soit passé avant de vérifier la fin
        // pour éviter la race condition (roundStarted encore false)
        try { Thread.sleep(1000L / clock.getFps() + 50); }
        catch (InterruptedException e) { Thread.currentThread().interrupt(); }

        // Maintenant attendre la vraie fin du round
        while (!roundManager.isCurrentRoundFinished()) {
            try { Thread.sleep(50); }
            catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }

        isRunning = false;
        clock.stop();
    }

    public int getRoundCount() {
        return roundManager.getRounds().size(); // ou stocker la liste dans Game
    }

    public boolean hasPlayerLost() {
        return roundManager.hasPlayerLost();
    }

}