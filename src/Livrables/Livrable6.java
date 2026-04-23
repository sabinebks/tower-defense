package Livrables;

import game.GameLoop;
import game.GameSetup;

/**
 * Point d'entree du Livrable 5.
 */
public class Livrable6 {

    public static void main(String[] args) {
        int[] p = parseArgs(args);
        new GameLoop(new GameSetup(p[0], p[1], p[2], args[3], args[4])).start();
    }

    private static int[] parseArgs(String[] args) {
        if (args.length < 5) {
            printUsage();
            System.exit(1);
        }

        if (!args[3].equalsIgnoreCase("chemin") && !args[3].equalsIgnoreCase("libre")) {
            System.err.println("Erreur : type plateau doit etre 'chemin' ou 'libre'.");
            System.exit(1);
        }

        if (!args[4].equalsIgnoreCase("humain") && !args[4].equalsIgnoreCase("aleatoire")) {
            System.err.println("Erreur : type joueur doit etre 'humain' ou 'aleatoire'.");
            System.exit(1);
        }

        try {
            int hauteur = Integer.parseInt(args[0]);
            int largeur = Integer.parseInt(args[1]);
            int nbBallons = Integer.parseInt(args[2]);

            if (nbBallons < 15) {
                System.err.println("Erreur : le nombre de ballons doit etre >= 15.");
                System.exit(1);
            }

            return new int[]{ hauteur, largeur, nbBallons };

        } catch (NumberFormatException e) {
            System.err.println("Erreur : hauteur, largeur et nb ballons doivent etre des entiers.");
            System.exit(1);
            return null;
        }
    }

    private static void printUsage() {
        System.err.println("Usage: java -jar livrable5.jar <hauteur> <largeur> <nb ballons> <type plateau> <type joueur>");
        System.err.println("  type plateau : chemin | libre  |  type joueur : humain | aleatoire");
    }
}