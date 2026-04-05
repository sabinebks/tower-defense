package ui;

import board.Board;
import board.Position;
import object.balloon.Balloon;
import object.tower.Tower;

public class BoardRenderer {

    public void render(Board board) {
        System.out.println("----TICK----");
        String[][] display = buildGrid(board);
        printGrid(display, board);
    }

    private String[][] buildGrid(Board board) {
        String[][] display = new String[board.getHeight()][board.getWidth()];

        for (int r = 0; r < board.getHeight(); r++)
            for (int c = 0; c < board.getWidth(); c++)
                display[r][c] = ".";

        for (Position p : board.getPathPositions())
            display[p.getRow()][p.getCol()] = "#";

        for (Balloon b : board.getBalloons()) {
            int row = (int) b.getLigne();
            int col = (int) b.getColonne();
            if (row >= 0 && row < board.getHeight() && col >= 0 && col < board.getWidth()) {
                display[row][col] = "O";
            }
        }

        for (Tower t : board.getTowers())
            display[t.getPosition().getRow()][t.getPosition().getCol()] = t.getSymbol();

        return display;
    }

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
