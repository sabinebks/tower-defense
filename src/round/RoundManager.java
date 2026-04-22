package round;

import board.Board;
import entity.balloon.Balloon;
import player.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * round manager
 */
public class RoundManager {

    /**
     * Spawn interval of balloons
     * List of round
     * Board of the game
     * The player
     * The current index of round
     * Spawn tick
     * Verify if round started
     * Verify if round finished
     * Number of balloons escaped
     * First tick of the round
     */
    private static final int SPAWN_INTERVAL = 5;
    private final List<Round> rounds;
    private final Board board;
    private final Player player;
    private int currentIndex = 0;
    private int spawnTick = 0;
    private boolean roundStarted = false;
    private boolean roundJustFinished = false;
    private int escapedCount = 0;
    private boolean firstTick = true;
    

    /**
     * constructor
     * @param rounds rounds
     * @param board board
     * @param player player
     */
    public RoundManager(List<Round> rounds, Board board, Player player) {
        this.rounds = rounds;
        this.board = board;
        this.player = player;
    }

    /**
     * update round
     * @param tickCount tick
     * @return removed balloons
     */
    public List<Balloon> update(int tickCount) {
        if (isFinished()) return new ArrayList<>();

        if (firstTick) {
            escapedCount = 0;
            firstTick = false;
        }

        Round round = rounds.get(currentIndex);

        if (tickCount % SPAWN_INTERVAL == 0
                && spawnTick < round.getBalloons().size()) {
            board.spawnBalloon(round.getBalloons().get(spawnTick));
            spawnTick++;
            roundStarted = true;
        }

        List<Balloon> removed = board.update();

        for (Balloon b : board.getEscapedBalloons()) {
            player.loseLife();
            round.addEscaped(b);
            escapedCount++;
        }

        board.clearEscapedBalloons();

        if (roundStarted
                && spawnTick >= round.getBalloons().size()
                && board.getBalloons().isEmpty()) {
            roundJustFinished = true;
        }

        return removed;
    }

    /**
     * advance round
     */
    public void advanceRound() {
        if (roundJustFinished) {
            currentIndex++;
            spawnTick = 0;
            roundStarted = false;
            roundJustFinished = false;
            firstTick = true;
        }
    }

    /**
     * check finished
     * @return true if finished
     */
    public boolean isFinished() {
        return currentIndex >= rounds.size();
    }

    /**
     * check lose
     * @return true if player lost
     */
    public boolean hasPlayerLost() {
        return !player.isAlive();
    }

    /**
     * current index
     * @return index
     */
    public int getCurrentIndex() {
        return currentIndex;
    }

    /**
     * current round finished
     * @return true if finished
     */
    public boolean isCurrentRoundFinished() {
        return isFinished() || roundJustFinished;
    }

    /**
     * escaped count
     * @return escaped
     */
    public int getEscapedCount() {
        return escapedCount;
    }

    /**
     * get rounds
     * @return rounds list
     */
    public List<Round> getRounds() {
        return this.rounds;
    }
}