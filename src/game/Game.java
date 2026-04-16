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
    /**
     * Les attributs de Game
     * Un board
     * Un joueur
     * Un manageur de round
     * Un clock
     * Un type boolean
     * Un type BoardRenderer pour render le board
     */
    private final Board board;
    private final Player player;
    private final RoundManager roundManager;
    private final Clock clock;
    private volatile boolean isRunning;
    private final BoardRenderer renderer;


    /**
     * Constructeur
     * @param player le joueur
     * @param board le board
     * @param rounds le round
     * @param fps le fps
     */
    public Game(Player player, Board board, List<Round> rounds, int fps) {
        this.player = player;
        this.board = board;
        this.renderer = new BoardRenderer();
        this.roundManager = new RoundManager(rounds, board, player);
        this.clock = new Clock(fps);
        this.clock.addListener(this);
    }

    /**
     * Afficher le board a chaque tick du jeu (utilise uniquement par start/stop)
     * @param tickCount le tick
     */
    @Override
    public void onTick(int tickCount) {
        if (!isRunning) return;

        System.out.println("TICK " + tickCount);

        List<Balloon> removed = roundManager.update(tickCount);

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

    /**
     * Methode lancer 1 round du jeu de facon synchrone (sans thread Clock)
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
                    System.out.println(">>> Ballon detruit ! +" + reward
                            + " credits. Total: " + player.getCredit());
                }
            }

            this.renderer.render(board);

            try { Thread.sleep(delay); }
            catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }

        roundManager.advanceRound();
        board.clearEscapedBalloons();
    }

    /**
     * Methode pour lancer le jeu (mode continu avec Clock)
     */
    public void start() { isRunning = true; clock.start(); }

    /**
     * Methode pour arreter le jeu
     */
    public void stop()  { isRunning = false; clock.stop(); }

    @Override public Player getPlayer() { return player; }
    @Override public Board getBoard()   { return board; }

    /**
     * Retourner le nombre de round
     * @return le nombre de round
     */
    public int getRoundCount() {
        return roundManager.getRounds().size();
    }

    /**
     * Verifier si le joueur est mort ou pas
     * @return true si joueur mort, false sinon
     */
    public boolean hasPlayerLost() {
        return roundManager.hasPlayerLost();
    }

    /**
     * Retourner le nombre de ballons echappes lors du dernier round
     * @return nombre de ballons echappes
     */
    public int getEscapedCount() {
        return roundManager.getEscapedCount();
    }
}