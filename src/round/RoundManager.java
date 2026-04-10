package round;

import board.Board;
import entity.balloon.Balloon;
<<<<<<< HEAD
=======
import player.Player;
>>>>>>> origin/main

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
     */
    private final List<Round> rounds;
    private final Board board;
    private final Player player;
    private int currentIndex = 0;
    private int spawnTick = 0;
    private boolean roundStarted = false;
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
        if (isFinished()) return true;
        Round round = rounds.get(currentIndex);
        return roundStarted
                && spawnTick >= round.getBalloons().size()
                && board.getBalloons().isEmpty();
    }


    /**
     * Retourner la liste de rounds
     * @return liste de rounds
     */
    public List<Round> getRounds() {
        return this.rounds;
    }


}