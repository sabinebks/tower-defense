package board.typeOfBoard;

import board.*;
import entity.tower.Tower;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class DefinedBoard extends Board {

    private Position cheminAv;

    private Position pointDeDepart;

    /**
     * Constructor for DefinedBoard.
     *
     * @param height Height of the board (top to bottom)
     * @param width  Width of the board (left to right)
     */
    public DefinedBoard(int height, int width) {
        super(height, width);
    }

    /**
     * Initializes the board by creating the grid
     * and starting the path generation.
     */
    @Override
    public void init() {
        grid = new Cell[height][width];
        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                grid[r][c] = new Cell(new Position(r, c));
            }
        }
        calculerPath();
    }


    /**
     * Returns the length of the path.
     *
     * @return path length
     */
    public int getCheminLong() {
        return this.allCells.size();
    }

    /**
     * Checks if a position is on the board border.
     *
     * @param p the position to check
     * @return true if the position is on a border, false otherwise
     */
    public boolean isBord(Position p) {
        return p.getRow() == 0 || p.getRow() == this.height - 1
                || p.getCol() == 0 || p.getCol() == this.width - 1;
    }

    /**
     * Generates a random path.
     * The path starts on a border and ends on a different border cell.
     */
    @Override
    public void calculerPath() {
        Random rand = new Random();

        int minLen = (this.width + this.height) / 2;

        List<Position> listPositions = new ArrayList<>();

        for (int j = 0; j < width; j++) {
            listPositions.add(new Position(0, j));
            listPositions.add(new Position(height - 1, j));
        }

        for (int i = 1; i < height - 1; i++) {
            listPositions.add(new Position(i, 0));
            listPositions.add(new Position(i, width - 1));
        }

        Position pRandom = listPositions.get(rand.nextInt(listPositions.size()));
        pointDeDepart = pRandom;
        cheminAv = pRandom;

        getCell(pRandom.getRow(),pRandom.getCol()).assignChemin();
        this.allCells.add(getCell(pRandom.getRow(),pRandom.getCol()));

        List<Position> bloque = new ArrayList<>();
        boolean fin = false;

        while (!fin) {
            List<Position> mesChoix = new ArrayList<>();

            if (cheminAv.getCol() != 0) {
                Position voisin = new Position(cheminAv.getRow(), cheminAv.getCol() - 1);
                if (getCell(voisin.getRow(),voisin.getCol()).estVide() && !bloque.contains(voisin)) {
                    mesChoix.add(voisin);
                }
            }

            if (cheminAv.getCol() != width - 1) {
                Position voisin = new Position(cheminAv.getRow(), cheminAv.getCol() + 1);
                if (getCell(voisin.getRow(),voisin.getCol()).estVide() && !bloque.contains(voisin)) {
                    mesChoix.add(voisin);
                }
            }

            if (cheminAv.getRow() != 0) {
                Position voisin = new Position(cheminAv.getRow() - 1, cheminAv.getCol());
                if (getCell(voisin.getRow(),voisin.getCol()).estVide() && !bloque.contains(voisin)) {
                    mesChoix.add(voisin);
                }
            }

            if (cheminAv.getRow() != height - 1) {
                Position voisin = new Position(cheminAv.getRow() + 1, cheminAv.getCol());
                if (getCell(voisin.getRow(), voisin.getCol()).estVide() && !bloque.contains(voisin)) {
                    mesChoix.add(voisin);
                }
            }

            if (mesChoix.isEmpty()) {
                break;
            }


            if (this.allCells.size() < minLen) {
                List<Position> filtre = new ArrayList<>();
                for (Position p : mesChoix) {
                    if (!(isBord(p) && !p.equals(pointDeDepart))) {
                        filtre.add(p);
                    }
                }
                if (!filtre.isEmpty()) {
                    mesChoix = filtre;
                }
            }

            Position randi = mesChoix.get(rand.nextInt(mesChoix.size()));
            getCell(randi.getRow(),randi.getCol()).assignChemin();
            this.allCells.add(getCell(randi.getRow(), randi.getCol()));

            if (cheminAv.getCol() != randi.getCol()) {
                for (int rr = 0; rr < height; rr++) {
                    bloque.add(new Position(rr, cheminAv.getCol()));
                }
            }

            cheminAv = randi;

            if (this.allCells.size() >= minLen && isBord(randi) && !randi.equals(pointDeDepart)) {
                fin = true;
            }
        }
        this.allPath.put(0, this.allCells);
    }

    public List<Position> getPathPositions() {
        List<Position> path = new ArrayList<>();

        for (Cell cell : allCells) {
            path.add(cell.getPosition());
        }

        return path;
    }


    public boolean isCellAvailable(int row, int col) {
        if (!isInside(row, col)) return false;

        for (Position p : getPathPositions()) {
            if (p.getRow() == row && p.getCol() == col) return false;
        }

        for (Tower t : towers) {
            if (t.getPosition().getRow() == row && t.getPosition().getCol() == col) return false;
        }

        return true;
    }

    @Override
    public List<Cell> allCells(){
        return this.allCells;
    }
}
