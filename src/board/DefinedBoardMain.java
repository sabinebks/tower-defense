package board;

import java.util.*;

public class DefinedBoardMain {
    public static void main(String[] args) {

        int height = 10;
        int width  = 15;

        DefinedBoard board = new DefinedBoard(height, width);
        List<Cell> chemins = new ArrayList<>();

        System.out.println("Plateau (" + height + " x " + width + ")");
        System.out.println("C = chemin, X = vide");

        for (int r = 0; r < board.getHeight(); r++) {
            for (int c = 0; c < board.getWidth(); c++) {
                if (board.getCell(r, c).isPath()) {
                    chemins.add(board.getCell(r, c));
                    if( chemins.indexOf(board.getCell(r, c)) == 0) {
                        System.out.print("D ");
                    }
                    else {
                        System.out.print("C ");
                    }
                } 
                else {
                    System.out.print(". "); // . sinon
                }
            }
            System.out.println(); //nouvelle ligne
        }
    }
}
