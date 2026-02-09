package Livrables;
import board.*;

/**
 * Livrable 2a - Simulation avec DefinedBoard.
 * Progression du temps : apres chaque tic, les ballons avancent
 */
public class Livrable2a {
    
    public static void main(String[] args) {
        if (args.length != 4) {
            System.err.println("Usage: java board.Livrable2a <hauteur> <largeur> <nombre_ballons> <mode ( 1 pour DefinedBoard, )>"); //1 pour defined board, else pour freeboard, exception lancee
            System.exit(1);
        }
        Game game = new Game(Integer.parseInt(args[3]));
        game.play(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]));

    }
}