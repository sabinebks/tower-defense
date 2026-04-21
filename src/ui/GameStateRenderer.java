package ui;

import board.Board;
import entity.balloon.Balloon;
import player.Player;

import java.util.List;

/**
 * Displays detailed game state information in console.
 */
public class GameStateRenderer {

    /**
     * Print global info at each tick
     * @param tick the tick to print
     * @param board the board of the game
     * @param player the player
     */
    public static void printTick(int tick, Board board, Player player) {

        System.out.println("\n===== TICK " + tick + " =====");
        System.out.println("Vies : " + player.getLives() +
                " | Credits : " + player.getCredit());

        List<Balloon> balloons = board.getBalloons();

        if (balloons.isEmpty()) {
            System.out.println("Aucun ballon actif.");
            return;
        }

        for (Balloon b : balloons) {
            System.out.println(
                    "Ballon " + b.getId()
                    + " | HP: " + b.getHealth()
                    + " | Pos: ("
                    + String.format("%.2f", b.getLigne()) + ", "
                    + String.format("%.2f", b.getColonne()) + ")"
            );
        }
    }

    /**
     * Print start of a round
     * @param round the round we run now
     * @param player the player
     */
    public static void printRoundStart(int round, Player player) {
        System.out.println("\n========================");
        System.out.println("   DEBUT ROUND " + round);
        System.out.println("========================");
        System.out.println("Vies : " + player.getLives()
                + " | Credits : " + player.getCredit());
    }

    /**
     * Print end of a round
     * @param escaped number of ballons that escaped
     * @param player the player
     */
    public static void printRoundEnd(int escaped, Player player) {
        System.out.println("\n=== FIN DU ROUND ===");

        if (escaped == 0) {
            System.out.println("Perfect round !");
        } else {
            System.out.println(escaped + " ballon(s) echappe(s)");
        }

        System.out.println("Vies restantes : " + player.getLives());
        System.out.println("Credits : " + player.getCredit());
    }

    /**
     * Game over display
     */
    public static void printGameOver() {
        System.out.println("\n=====================");
        System.out.println("     GAME OVER");
        System.out.println("=====================");
    }

    /**
     * Victory display
     */
    public static void printVictory(int lives) {
        System.out.println("\n=====================");
        System.out.println("     VICTOIRE !");
        System.out.println("Vies restantes : " + lives);
        System.out.println("=====================");
    }
}