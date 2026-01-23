package board;

import board.FreeBoard;
import board.Cell;

public class FreeBoardMain {

    public static void main(String[] args) {

        int height = 5;
        int width = 7;

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
                if (grid[i][j].isPath()) {
                    System.out.print(" * ");
                } else {
                    System.out.print(" . ");
                }
            }
            System.out.println();
        }
    }
}

