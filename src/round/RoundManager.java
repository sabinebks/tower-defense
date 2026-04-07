package round;

import board.Board;
import entity.balloon.Balloon;
import player.Player;

import java.util.List;

/**
 * Manages the sequence of rounds in the game.
 * Handles balloon spawning, escape detection, life deduction, and round transitions.
 */
public class RoundManager {
    private final List<Round> rounds;
    private final Board board;
    private final Player player;
    private int currentIndex = 0;
    private int spawnTick = 0;
    private boolean roundStarted = false;
    private static final int SPAWN_INTERVAL = 5;

    // BUG FIX : compteur de ballons echappes dans le round en cours.
    // Remis a 0 a chaque nouveau round. Expose via getLastRoundEscaped().
    private int lastRoundEscaped = 0;

    /**
     * Creates a RoundManager.
     *
     * @param rounds  The list of rounds to play
     * @param board   The game board
     * @param player  The player (used to deduct lives on balloon escape)
     */
    public RoundManager(List<Round> rounds, Board board, Player player) {
        this.rounds = rounds;
        this.board = board;
        this.player = player;
    }

    /**
     * Updates game state for the current tick.
     * Spawns balloons on schedule, detects escapes, deducts lives, and advances rounds.
     *
     * @param tickCount The current game tick
     */
    public void update(int tickCount) {
        if (isFinished()) return;

        Round round = rounds.get(currentIndex);

        // Spawn balloons at fixed intervals
        if (tickCount % SPAWN_INTERVAL == 0 && spawnTick < round.getBalloons().size()) {
            board.spawnBalloon(round.getBalloons().get(spawnTick));
            System.out.println("SPAWN ballon " + spawnTick);
            spawnTick++;
            roundStarted = true;
        }

        // Check for escaped balloons — lose 1 life per escaped balloon
        for (Balloon b : board.getEscapedBalloons()) {
            lastRoundEscaped++;   // BUG FIX : compter les echappes de ce round
            player.loseLife();
            if (!player.isAlive()) {
                System.out.println("!!! Plus de vies ! DEFAITE !!!");
            }
        }
        board.clearEscapedBalloons();

        // End of round: all balloons spawned and board empty
        if (roundStarted
                && spawnTick >= round.getBalloons().size()
                && board.getBalloons().isEmpty()) {
            if (player.isAlive()) {
                System.out.println("=== Round " + round.getNumber() + " termine ! ===");
            }
            currentIndex++;
            spawnTick = 0;
            roundStarted = false;
            // Note : on ne remet PAS lastRoundEscaped a 0 ici,
            // Livrable5 le lit apres runOneRound(), puis appelle resetLastRoundEscaped().
        }
    }

    public boolean isFinished() { return currentIndex >= rounds.size(); }

    public boolean hasPlayerLost() { return !player.isAlive(); }

    public int getCurrentIndex() { return currentIndex; }


    public boolean isCurrentRoundFinished() {
        if (isFinished()) return true;
        Round round = rounds.get(currentIndex);
        return roundStarted
                && spawnTick >= round.getBalloons().size()
                && board.getBalloons().isEmpty();
    }


    public List<Round> getRounds() {
        return this.rounds;
    }


    public int getLastRoundEscaped() {
        return lastRoundEscaped;
    }


    public void resetLastRoundEscaped() {
        lastRoundEscaped = 0;
    }
}