package ui;

import board.Board;
import board.Cell;
import board.Position;
import entity.balloon.Balloon;
import entity.tower.Tower;

public class BoardRenderer {

    /**
     * Methode afficher le board
     * @param board le board a afficher
     */
    public void render(Board board) {
        System.out.println("----TICK----");
        String[][] display = buildGrid(board);
        printGrid(display, board);
    }
    /**
     * Methode a creer les grids du board
     * @param board le board a creer
     * @return un string de display
     */
    public String[][] buildGrid(Board board) {
        String[][] display = new String[board.getHeight()][board.getWidth()];

        for (int r = 0; r < board.getHeight(); r++)
            for (int c = 0; c < board.getWidth(); c++)
                display[r][c] = ".";

        for (Cell c : board.allCells()) {
            if (c == null) continue;
            Position p = c.getPosition();
            if (p == null) continue;
            display[p.getRow()][p.getCol()] = "#";
        }
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

    /**
     * Methode a afficher les grids
     * @param display le grid a afficher
     * @param board le board du jeu
     */
    public void printGrid(String[][] display, Board board) {
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