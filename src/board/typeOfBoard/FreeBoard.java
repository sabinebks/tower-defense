package board.typeOfBoard;

import board.*;
import entity.tower.Tower;

import java.util.*;

/**
 * This class represents a free board.
 * It generates random straight paths from one border to another.
 */
public class FreeBoard extends Board {

    private static final int NBCHEMIN = 5;

    private Map<Integer, Position> numChemins;
    private Map<Position, Position> chemins;
    private int cmp = 0;
    private int nbCheminsVoulus;

    /**
     * Create a free board with a given size.
     *
     * @param height board height
     * @param width board width
     */
    public FreeBoard(int height, int width, int nbChemin) {
        super(height, width);
        this.nbCheminsVoulus = nbChemin;
        init();
    }

    /**
     * Initialize the board and create all cells.
     */
    @Override
    public void init() {
        grid = new Cell[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                grid[i][j] = new Cell(new Position(i, j));
            }
        }

        calculerPath();
    }

    /**
     * Generate random paths on the board.
     */
    @Override
    public void calculerPath() {
        this.cmp = 0;
        this.numChemins = new HashMap<>();
        this.chemins = new HashMap<>();
        this.allPath.clear();
        this.allCells.clear();

        if (nbCheminsVoulus == 0) nbCheminsVoulus = NBCHEMIN;

        Random rand = new Random();
        List<Position> borders = getBorderPositions();

        while (cmp < nbCheminsVoulus && !borders.isEmpty()) {
            Position start = borders.get(rand.nextInt(borders.size()));
            Position end = getFinChemin(start);

            if (borders.contains(end)) {
                List<Cell> path = buildPath(start, end);

                for (Cell c : path) {
                    c.assignChemin();
                }

                borders.remove(start);
                borders.remove(end);

                chemins.put(start, end);
                numChemins.put(cmp, start);
                allPath.put(cmp, path);
                allCells.addAll(path);

                cmp++;
            } else {
                borders.remove(start);
            }
        }
    }

    /**
     * Build a straight path between two positions.
     *
     * @param start start position
     * @param end end position
     * @return list of cells in the path
     */
    private List<Cell> buildPath(Position start, Position end) {
        List<Cell> list = new ArrayList<>();

        int row = start.getRow();
        int col = start.getCol();

        int dRow = Integer.compare(end.getRow(), row);
        int dCol = Integer.compare(end.getCol(), col);

        while (true) {
            list.add(getCell(row, col));

            if (row == end.getRow() && col == end.getCol())
                break;

            row += dRow;
            col += dCol;
        }

        return list;
    }

    /**
     * Get all border positions of the board.
     *
     * @return list of border positions
     */
    private List<Position> getBorderPositions() {
        List<Position> list = new ArrayList<>();

        for (int j = 0; j < width; j++) {
            list.add(new Position(0, j));
            list.add(new Position(height - 1, j));
        }

        for (int i = 1; i < height - 1; i++) {
            list.add(new Position(i, 0));
            list.add(new Position(i, width - 1));
        }

        return list;
    }

    /**
     * Get the end position of a path.
     *
     * @param start start position
     * @return end position
     */
    public Position getFinChemin(Position start) {
        if (start.getRow() == 0) return new Position(height - 1, start.getCol());
        if (start.getRow() == height - 1) return new Position(0, start.getCol());
        if (start.getCol() == 0) return new Position(start.getRow(), width - 1);
        return new Position(start.getRow(), 0);
    }

    /**
     * Get a path by its index.
     *
     * @param num index of the path
     * @return start and end positions
     */
    public Position[] retourneLesChemins(int num) {
        if (num >= 0 && num < cmp) {
            Position debut = numChemins.get(num);
            return new Position[]{debut, chemins.get(debut)};
        }
        throw new IllegalArgumentException("Invalid path number");
    }

    /**
     * Return number of generated paths.
     *
     * @return number of paths
     */
    public int retourneNbrChemins() {
        return cmp;
    }

    /**
     * Get all paths.
     *
     * @return map of paths
     */
    public Map<Position, Position> getCheminsMap() {
        return chemins;
    }

    /**
     * Get a random path from the board.
     *
     * @return list of positions
     */
    @Override
    public List<Position> getPathPositions() {
        if (allPath.isEmpty()) return new ArrayList<>();

        Random rand = new Random();
        List<Cell> randomPath = allPath.get(rand.nextInt(allPath.size()));

        List<Position> positions = new ArrayList<>();
        for (Cell cell : randomPath) {
            positions.add(cell.getPosition());
        }

        return positions;
    }

    /**
     * Check if a cell is free for a tower.
     *
     * @param row row index
     * @param col column index
     * @return true if free, false otherwise
     */
    @Override
    public boolean isCellAvailable(int row, int col) {
        if (!isInside(row, col)) return false;

        for (Tower t : towers) {
            if (t.getPosition().getRow() == row &&
                t.getPosition().getCol() == col) {
                return false;
            }
        }

        return true;
    }


}