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

    /**
     * constructor
     * @param player player
     * @param board board
     * @param rounds rounds
     * @param fps fps
     */
    public Game(Player player, Board board, List<Round> rounds, int fps) {
        this.player = player;
        this.board = board;
        this.renderer = new BoardRenderer();
        this.roundManager = new RoundManager(rounds, board, player);
        this.clock = new Clock(fps);
        this.clock.addListener(this);
    }

    @Override
    /**
     * tick update
     * @param tickCount tick
     */
    public void onTick(int tickCount) {
        if (!isRunning) return;


        List<Balloon> removed = roundManager.update(tickCount);

        for (Balloon b : removed) {
            if (!b.hasEscaped()) {
                int reward = b.getType().reward;
                player.addCredit(reward);
            }
        }

        renderer.render(board);
    }

    /**
     * run one round
     */
    public void runOneRound() {
        int tickCount = 0;
        long delay = 1000L / clock.getFps();

        while (!roundManager.isCurrentRoundFinished() && player.isAlive()) {
            tickCount++;
            System.out.println("TICK " + tickCount);
            List<Balloon> removed = roundManager.update(tickCount);

            for (Balloon b : removed) {
                if (!b.hasEscaped()) {
                    int reward = b.getType().reward;
                    player.addCredit(reward);
                }
            }

            renderer.render(board);

            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        roundManager.advanceRound();
        board.clearEscapedBalloons();
    }

    /**
     * start game
     */
    public void start() {
        isRunning = true;
        clock.start();
    }

    /**
     * stop game
     */
    public void stop() {
        isRunning = false;
        clock.stop();
    }

    @Override
    /**
     * get player
     * @return player
     */
    public Player getPlayer() {
        return player;
    }

    @Override
    /**
     * get board
     * @return board
     */
    public Board getBoard() {
        return board;
    }

    /**
     * round count
     * @return rounds number
     */
    public int getRoundCount() {
        return roundManager.getRounds().size();
    }

    /**
     * check lose
     * @return true if player lost
     */
    public boolean hasPlayerLost() {
        return roundManager.hasPlayerLost();
    }

    /**
     * escaped balloons
     * @return escaped count
     */
    public int getEscapedCount() {
        return roundManager.getEscapedCount();
    }
}