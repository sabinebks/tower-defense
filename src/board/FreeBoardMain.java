package board;

/**
 * Main class to test the FreeBoard implementation.
 * It initializes a board, generates straight paths, and prints the result.
 */

public class FreeBoardMain {

    public static void main(String[] args) {

        int height = 10;
        int width = 10;

        FreeBoard board = new FreeBoard(height, width);

        // initialisation du plateau
        board.init();

        // calcul du chemin depuis une cellule de la bordure
        board.calculerPath();

        // affichage du plateau
        afficherBoard(board);
    }

   private static void afficherBoard(FreeBoard board) {
        Cell[][] grid = board.getGrid();

    for (int i = 0; i < grid.length; i++) {
        for (int j = 0; j < grid[0].length; j++) {
            Cell cell = grid[i][j];
            boolean estDebut = false;
            boolean estFin = false;

            // Verifier chaque chemin
            for (int num = 0; num < board.retourneNbrChemins(); num++) {
                Position[] chemin = board.retourneLesChemins(num);
                Position debut = chemin[0];
                Position fin = chemin[1];

                if (i == debut.getRow() && j == debut.getCol()) {
                    estDebut = true;
                    break;
                }
                if (i == fin.getRow() && j == fin.getCol()) {
                    estFin = true;
                    break;
                }
            }

            if (estDebut) {
                System.out.print("D ");
            } else if (estFin) {
                System.out.print("F ");
            } else if (cell.isPath()) {
                System.out.print("C ");
            } else {
                System.out.print(". ");
            }
        }
        System.out.println();
    }

    // Affichage des couples debut /fin de tous les chemins possibles
    System.out.println("\ntous les chemins (debut -> fin) :");
    for (int num = 0; num < board.retourneNbrChemins(); num++) {
        //enregister chaque couple le temps de laffichage 
        Position[] chemin = board.retourneLesChemins(num);
        Position debut = chemin[0];
        Position fin = chemin[1];
        System.out.println(debut + " -> " + fin);
    }
}

}
