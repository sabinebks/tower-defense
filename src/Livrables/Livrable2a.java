package Livrables;
import board.*;
import board.typeOfBoard.FreeBoard;


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

    /**
     * Main class to test the FreeBoard implementation.
     */
    public static class Livrable1b {

        public static void main(String[] args) {
            if (args.length < 3) {
                System.out.println("Arguments manquants : <hauteur> <largeur> <nbChemins>");
                return;
            }

            int height = Integer.parseInt(args[0]);
            int width = Integer.parseInt(args[1]);
            int nbTarget = Integer.parseInt(args[2]);

            FreeBoard board = new FreeBoard(height, width);

            // 1. Initialisation de la grille
            board.init();

            // On définit le nombre de chemins aant le calcul
            board.setNbCheminsVoulus(nbTarget);

            // 3. Calcul du chemin
            board.calculerPath();

            // 4. Affichage
            afficherBoard(board, nbTarget);
        }

       private static void afficherBoard(FreeBoard board, int nbTarget) {
            Cell[][] grid = board.getGrid();
            // On utilise le nombre réellement généré par le plateau
            int nbAfficher = board.retourneNbrChemins();

            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[0].length; j++) {
                    Cell cell = grid[i][j];
                    boolean estDebut = false;
                    boolean estFin = false;

                    for (int num = 0; num < nbAfficher; num++) {
                        Position[] chemin = board.retourneLesChemins(num);
                        if (i == chemin[0].getRow() && j == chemin[0].getCol()) {
                            estDebut = true;
                            break;
                        }
                        if (i == chemin[1].getRow() && j == chemin[1].getCol()) {
                            estFin = true;
                            break;
                        }
                    }

                    if (estDebut) System.out.print("D ");
                    else if (estFin) System.out.print("F ");
                    else if (cell.isPath()) System.out.print("C ");
                    else System.out.print(". ");
                }
                System.out.println();
            }

            System.out.println("\nChemins :");
            for (int num = 0; num < nbAfficher; num++) {
                Position[] chemin = board.retourneLesChemins(num);
                System.out.println(chemin[0] + " -> " + chemin[1]);
            }
        }
    }
}