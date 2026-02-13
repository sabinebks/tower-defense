package Livrables;
import game.*;

/**
 * Livrable 2b - Simulation de ballons rectilignes sur un FreeBoard.
 */
public class Livrable2b {
    public static void main(String[] args) {
        if (args.length != 3) {
            System.err.println("Usage: java Livrable2b <hauteur> <largeur> <nombre_ballons>");
            System.exit(1);
        }

        int height = Integer.parseInt(args[0]);
        int width = Integer.parseInt(args[1]);
        int nbBalloons = Integer.parseInt(args[2]);

        // Mode 2 pour FreeBoard
        Game game = new Game(2);
        game.play(height, width, nbBalloons);
    }
}
