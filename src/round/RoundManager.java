package round;

import board.Board;
import object.balloon.Balloon;

import java.util.List;

public class RoundManager {
    private final List<Round> rounds;
    private final Board board;
    private int currentIndex = 0;
    private int spawnTick = 0;
    private boolean roundStarted = false;
    private boolean playerLost = false; // ← un ballon a atteint la fin
    private static final int SPAWN_INTERVAL = 5;

    public RoundManager(List<Round> rounds, Board board) {
        this.rounds = rounds;
        this.board = board;
    }

    public void update(int tickCount) {
        if (isFinished()) return;

        Round round = rounds.get(currentIndex);

        if (tickCount % SPAWN_INTERVAL == 0 && spawnTick < round.getBalloons().size()) {
            board.spawnBalloon(round.getBalloons().get(spawnTick));
            System.out.println("SPAWN ballon " + spawnTick);
            spawnTick++;
            roundStarted = true;
        }

        for (Balloon b : board.getEscapedBalloons()) {
            System.out.println("!!! Un ballon a atteint la fin du chemin ! DÉFAITE !!!");
            playerLost = true;
        }
        board.clearEscapedBalloons();

        if (roundStarted
                && spawnTick >= round.getBalloons().size()
                && board.getBalloons().isEmpty()) {
            if (!playerLost) {
                System.out.println("=== Round " + round.getNumber() + " terminé ! VICTOIRE ! ===");
            }
            currentIndex++;
            spawnTick = 0;
            roundStarted = false;
            playerLost = false;
        }
    }
    public boolean isFinished() { return currentIndex >= rounds.size(); }

}
