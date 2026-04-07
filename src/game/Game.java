package game;

import board.Board;
import clock.Clock;
import clock.ClockListener;
import entity.balloon.Balloon;
import player.Player;
import round.Round;
import round.RoundManager;
import ui.BoardRenderer;

import java.util.List;


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
        this.roundManager = new RoundManager(rounds, board, player);
        this.clock = new Clock(fps);
        this.clock.addListener(this);
    }

    @Override
    public void onTick(int tickCount) {
        if (!isRunning) return;

        System.out.println("TICK " + tickCount);
        roundManager.update(tickCount);
        List<Balloon> removed = board.update();

        for (Balloon b : removed) {
            if (!b.hasEscaped()) {
                int reward = b.getType().reward;
                player.addCredit(reward);
                System.out.println(">>> Ballon detruit ! +" + reward
                        + " credits. Total: " + player.getCredit());
            }
        }

        this.renderer.render(board);
    }


    public void runOneRound() {
        clock.reset();
        isRunning = true;
        clock.start();

        try { Thread.sleep(1000L / clock.getFps() + 50); }
        catch (InterruptedException e) { Thread.currentThread().interrupt(); }

        while (!roundManager.isCurrentRoundFinished() && player.isAlive()) {
            try { Thread.sleep(50); }
            catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }

        isRunning = false;
        clock.stop();
    }

    public void start() { isRunning = true; clock.start(); }
    public void stop()  { isRunning = false; clock.stop(); }

    @Override public Player getPlayer() { return player; }
    @Override public Board getBoard()   { return board; }

    public int getRoundCount() {
        return roundManager.getRounds().size();
    }

    /** @return true if the player has run out of lives */
    public boolean hasPlayerLost() {
        return roundManager.hasPlayerLost();
    }

    /**
     * Returns the number of balloons that escaped during the last round.
     * Used by Livrable5 to decide whether to award the round bonus.
     *
     * @return number of escaped balloons in the last completed round
     */
    public int getLastRoundEscaped() {
        return roundManager.getLastRoundEscaped();
    }

    /**
     * Resets the escaped-balloon counter before a new round starts.
     * Must be called by Livrable5 before each call to runOneRound().
     */
    public void resetLastRoundEscaped() {
        roundManager.resetLastRoundEscaped();
    }
}