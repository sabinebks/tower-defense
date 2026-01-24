package board;

/**
 * Main class to test the DefinedBoard.
 * It displays the generated path in the console.
 */

public class DefinedBoardMain {
    public static void main(String[] args) {

        int height = 15;
        int width  = 15;

        // Création du plateau 
        DefinedBoard board = new DefinedBoard(height, width);

        System.out.println("Plateau de taille :" + height + " * " + width );
        System.out.println(" D =Debut  , F= Fin  , c= Chemin  , . = Vide ");

        // Affichage du plateau
        for (int r = 0; r < board.getHeight(); r++) {
            for (int c = 0; c < board.getWidth(); c++) {
                Cell cell = board.getCell(r, c);

                if (cell.isPath()) {
                    // Début du chemin
                    if (board.getChemins().indexOf(cell) == 0) {
                        System.out.print("D ");
                    }
                    // Fin du chemin
                    else if (board.getChemins().indexOf(cell) == board.getCheminLong() - 1) {
                        System.out.print("F ");
                    }
                    // Chemin intermédiaire
                    else {
                        System.out.print("c ");
                    }
                } else {
                    System.out.print(". "); // Cellule vide
                }
            }
            System.out.println(); // nouvelle ligne
        }

        // afficher le chemin complet sous forme de positions
        System.out.println("\nChemin complet :");
        for (Cell c : board.getChemins()) {
            System.out.print(c.getPosition() + " -> ");
        }
        
    }
}
