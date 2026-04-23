package ui;

import player.Player;

/**
 * Responsable de tout l'affichage console du jeu.
 */
public class GameRenderer {

    public static void printBanner(Player player) {
        System.out.println("====================================");
        System.out.println("|        BLOONS TOWER DEFENSE       |");
        System.out.println("|  Vies : " + player.getLives() + " | Credits : " + player.getCredit() + "       |");
        System.out.println("====================================");
    }

    public static void printRoundHeader(int roundNum, Player player) {
        System.out.println("\n===========================");
        System.out.printf( "|        MANCHE %-2d         |%n", roundNum);
        System.out.println("===========================");
        System.out.println("Vies : " + player.getLives() + " | Credits : " + player.getCredit());
    }

    public static void printGameOver() {
        System.out.println("========================");
        System.out.println("|      GAME OVER !     |");
        System.out.println("|  Vies restantes : 0  |");
        System.out.println("========================");
    }

    public static void printVictory(int lives) {
        System.out.println("========================");
        System.out.println("|  VICTOIRE TOTALE !   |");
        System.out.printf( "|  Vies restantes : %-2d |%n", lives);
        System.out.println("========================");
    }

    public static void printRoundResult(int escaped, Player player, int reward) {
        if (escaped == 0) {
            player.addCredit(reward);
            System.out.println(">>> Round parfait ! +" + reward
                    + " credits. Total : " + player.getCredit()
                    + " | Vies : " + player.getLives());
        } else {
            System.out.println(">>> " + escaped + " ballon(s) echappe(s) ce round."
                    + " Pas de recompense. Credits : " + player.getCredit()
                    + " | Vies : " + player.getLives());
        }
    }

    public static void printInitialBuyPhase() {
        System.out.println("\n=== PHASE D'ACHAT INITIALE ===");
    }

    public static void printActionPhase() {
        System.out.println("=== Phase d'action ===");
    }
}