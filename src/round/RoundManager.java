package round;

import board.Board;

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
}
