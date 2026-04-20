package board.typeOfBoard;

import board.*;
import entity.tower.Tower;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
/**
 * This class represents a defined board.
 * The path is generated randomly but must stay connected
 * from a start position to a border exit.
 */
public class DefinedBoard extends Board {
    /**
     * Current position of the path
     */
    private Position cheminAv;

    /**
     * Starting position of the path
     */
    private Position pointDeDepart;

    /**
     * Create a defined board.
     *
     * @param height board height
     * @param width board width
     */
    public DefinedBoard(int height, int width) {
        super(height, width);
    }

    /**
     * Initialize the board with empty cells.
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
     * Return the length of the path.
     *
     * @return number of cells in the path
     */
    public int getCheminLong() {
        return this.allCells.size();
    }

    /**
     * Check if a position is on the border of the board.
     *
     * @param p position to check
     * @return true if border, false otherwise
     */
    public boolean isBord(Position p) {
        return p.getRow() == 0 || p.getRow() == this.height - 1
                || p.getCol() == 0 || p.getCol() == this.width - 1;
    }

    /**
     * Generate a random connected path on the board.
     * The path starts from a border position and tries to reach another border.
     */
    @Override
    public void calculerPath() {
        Random rand = new Random();

        int minLen = (this.width + this.height) / 2;

        List<Position> listPositions = new ArrayList<>();

        // add border positions
        for (int j = 0; j < width; j++) {
            listPositions.add(new Position(0, j));
            listPositions.add(new Position(height - 1, j));
        }

        for (int i = 1; i < height - 1; i++) {
            listPositions.add(new Position(i, 0));
            listPositions.add(new Position(i, width - 1));
        }

        Position start = listPositions.get(rand.nextInt(listPositions.size()));
        pointDeDepart = start;
        cheminAv = start;

        getCell(start.getRow(), start.getCol()).assignChemin();
        this.allCells.add(getCell(start.getRow(), start.getCol()));

        List<Position> bloque = new ArrayList<>();
        boolean fin = false;

        while (!fin) {

            List<Position> choices = new ArrayList<>();

            // check 4 directions
            if (cheminAv.getCol() > 0) {
                Position p = new Position(cheminAv.getRow(), cheminAv.getCol() - 1);
                if (getCell(p.getRow(), p.getCol()).estVide() && !bloque.contains(p)) {
                    choices.add(p);
                }
            }

            if (cheminAv.getCol() < width - 1) {
                Position p = new Position(cheminAv.getRow(), cheminAv.getCol() + 1);
                if (getCell(p.getRow(), p.getCol()).estVide() && !bloque.contains(p)) {
                    choices.add(p);
                }
            }

            if (cheminAv.getRow() > 0) {
                Position p = new Position(cheminAv.getRow() - 1, cheminAv.getCol());
                if (getCell(p.getRow(), p.getCol()).estVide() && !bloque.contains(p)) {
                    choices.add(p);
                }
            }

            if (cheminAv.getRow() < height - 1) {
                Position p = new Position(cheminAv.getRow() + 1, cheminAv.getCol());
                if (getCell(p.getRow(), p.getCol()).estVide() && !bloque.contains(p)) {
                    choices.add(p);
                }
            }

            if (choices.isEmpty()) {
                break;
            }

            // force minimum path length
            if (this.allCells.size() < minLen) {
                List<Position> filtered = new ArrayList<>();

                for (Position p : choices) {
                    if (!(isBord(p) && !p.equals(pointDeDepart))) {
                        filtered.add(p);
                    }
                }

                if (!filtered.isEmpty()) {
                    choices = filtered;
                }
            }

            Position next = choices.get(rand.nextInt(choices.size()));

            getCell(next.getRow(), next.getCol()).assignChemin();
            this.allCells.add(getCell(next.getRow(), next.getCol()));

            // block column if needed
            if (cheminAv.getCol() != next.getCol()) {
                for (int r = 0; r < height; r++) {
                    bloque.add(new Position(r, cheminAv.getCol()));
                }
            }

            cheminAv = next;

            if (this.allCells.size() >= minLen && isBord(next) && !next.equals(pointDeDepart)) {
                fin = true;
            }
        }

        this.allPath.put(0, this.allCells);
    }

    /**
     * Return the path as a list of positions.
     *
     * @return list of positions in the path
     */
    public List<Position> getPathPositions() {
        List<Position> path = new ArrayList<>();

        for (Cell cell : allCells) {
            path.add(cell.getPosition());
        }

        return path;
    }
    /**
     * Check if a cell is available (no path and no tower).
     *
     * @param row row index
     * @param col column index
     * @return true if available, false otherwise
     */
    public boolean isCellAvailable(int row, int col) {
        if (!isInside(row, col)) {
            return false;
        }

        for (Position p : getPathPositions()) {
            if (p.getRow() == row && p.getCol() == col) {
                return false;
            }
        }

        for (Tower t : towers) {
            if (t.getPosition().getRow() == row &&
                t.getPosition().getCol() == col) {
                return false;
            }
        }
        return true;
    }
}