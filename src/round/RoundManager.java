package round;

import board.Board;
import entity.balloon.Balloon;
import player.Player;

import java.util.List;

public class RoundManager {
    private final List<Round> rounds;
    private final Board board;
    private final Player player;
    private int currentIndex = 0;
    private int spawnTick = 0;
    private boolean roundStarted = false;
    private static final int SPAWN_INTERVAL = 5;


    public RoundManager(List<Round> rounds, Board board, Player player) {
        this.rounds = rounds;
        this.board = board;
        this.player = player;
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
            player.loseLife();
            if (!player.isAlive()) {
                System.out.println("!!! Plus de vies ! DEFAITE !!!");
            }
        }
        board.clearEscapedBalloons();

        if (roundStarted
                && spawnTick >= round.getBalloons().size()
                && board.getBalloons().isEmpty()) {
            if (player.isAlive()) {
                System.out.println("=== Round " + round.getNumber() + " termine ! ===");
            }
            currentIndex++;
            spawnTick = 0;
            roundStarted = false;
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


}