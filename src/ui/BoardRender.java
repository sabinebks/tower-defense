package ui;

import board.Board;

public class BoardRender {

    private void printGrid(String[][] display, Board board) {
        System.out.println("=".repeat(board.getWidth() * 2 + 1));
        for (String[] row : display) {
            System.out.print("|");
            for (String cell : row)
                System.out.print(cell + " ");
            System.out.println("|");
        }
        System.out.println("=".repeat(board.getWidth() * 2 + 1));
    }
}
