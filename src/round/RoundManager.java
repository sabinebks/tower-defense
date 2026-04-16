package round;

import board.Board;
import entity.balloon.Balloon;
import player.Player;

import java.util.ArrayList;
import java.util.List;

public class RoundManager {
    /**
     * Les attributs de RoundManager
     * Un liste des rounds
     * Le board du jeu
     * Le joueur
     * Le nombre du curr round
     * Le tick a creer les ballons
     * Le status du round ( commence ou pas)
     * La frequence pour creer les ballons
     * Un flag pour savoir si le round vient de se terminer
     * Le nombre de ballons echappes ce round
     * Un flag pour savoir si c'est le premier tick du round
     */
    private final List<Round> rounds;
    private final Board board;
    private final Player player;
    private int currentIndex = 0;
    private int spawnTick = 0;
    private boolean roundStarted = false;
    private boolean roundJustFinished = false;
    private int escapedCount = 0;
    private boolean firstTick = true;
    private static final int SPAWN_INTERVAL = 5;

    /**
     * Constructeur
     * @param rounds la liste des rounds
     * @param board le board du jeu
     * @param player le joueur
     */
    public RoundManager(List<Round> rounds, Board board, Player player) {
        this.rounds = rounds;
        this.board = board;
        this.player = player;
    }

    /**
     * Mettre a jour le status du jeu apres un nombre de tick
     * @param tickCount le nombre de tick
     */
    public List<Balloon> update(int tickCount) {
        if (isFinished()) return new ArrayList<>();

        if (firstTick) {
            escapedCount = 0;
            firstTick = false;
        }

        Round round = rounds.get(currentIndex);

        if (tickCount % SPAWN_INTERVAL == 0 && spawnTick < round.getBalloons().size()) {
            board.spawnBalloon(round.getBalloons().get(spawnTick));
            System.out.println("SPAWN ballon " + spawnTick);
            spawnTick++;
            roundStarted = true;
        }

        List<Balloon> removed = board.update();

        for (Balloon b : board.getEscapedBalloons()) {
            player.loseLife();
            round.addEscaped(b);
            escapedCount++;
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
            roundJustFinished = true;
        }

        return removed;
    }

    /**
     * Avancer au round suivant — a appeler apres la sortie de runOneRound()
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
     * Retourner si il n'y a plus de round
     * @return true si il n'y a plus, false sinon
     */
    public boolean isFinished() {
        return currentIndex >= rounds.size();
    }

    /**
     * Verifier si le joueur est mort ou pas
     * @return true si joueur est mort, false sinon
     */
    public boolean hasPlayerLost() {
        return !player.isAlive();
    }

    /**
     * Retourner l'index du round courant
     * @return index du round courant
     */
    public int getCurrentIndex() {
        return currentIndex;
    }

    /**
     * Verifier si le curr round est fini ou pas
     * @return true si fini, false sinon
     */
    public boolean isCurrentRoundFinished() {
        return isFinished() || roundJustFinished;
    }

    /**
     * Retourner le nombre de ballons echappes ce round
     * @return nombre de ballons echappes
     */
    public int getEscapedCount() {
        return escapedCount;
    }

    /**
     * Retourner la liste de rounds
     * @return liste de rounds
     */
    public List<Round> getRounds() {
        return this.rounds;
    }
}