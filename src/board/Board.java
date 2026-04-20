package board;

import entity.balloon.Balloon;
import entity.tower.Tower;
import entity.tower.projectil.NotMatchException;
import entity.tower.projectil.Projectil;

import java.util.*;

/**
 * This abstract class represents a game board.
 * The board contains cells, paths, towers, balloons, and projectiles.
 */
public abstract class Board {

    protected int width;   // width of the board
    protected int height;  // height of the board
    protected Cell[][] grid; // grid of cells
    protected Map<Integer, List<Cell>> allPath; // all paths
    protected List<Tower> towers; // list of towers
    protected List<Balloon> balloons; // list of balloons
    protected List<Projectil> activeProjectiles; // active projectiles
    protected List<Balloon> escapedBalloons = new ArrayList<>(); // balloons that escaped
    protected List<Cell> allCells; // list of all cells

    /**
     * Creates a new board with given height and width.
     *
     * @param height the height of the board
     * @param width the width of the board
     */
    public Board(int height, int width) {
        this.width = width;
        this.height = height;
        this.allPath = new HashMap<>();
        this.towers = new ArrayList<>();
        this.balloons = new ArrayList<>();
        this.activeProjectiles = new ArrayList<>();
        this.allCells = new ArrayList<>();
        init();
    }

    /** Calculates the paths of the board */
    public abstract void calculerPath();

    /** Initializes the board */
    public abstract void init();

    /** Returns the path as positions */
    public abstract List<Position> getPathPositions();

    /** Returns all cells */
    public abstract List<Cell> allCells();

    /**
     * Returns a cell at given row and column.
     *
     * @param row row index
     * @param col column index
     * @return the cell
     */
    public Cell getCell(int row, int col) {
        if (!isInside(row, col))
            throw new IllegalArgumentException("Coordinates outside the board");
        return this.grid[row][col];
    }

    /** @return board width */
    public int getWidth() { return width; }

    /** @return board height */
    public int getHeight() { return height; }

    /** @return the grid of cells */
    public Cell[][] getGrid() { return this.grid; }

    /** @return all paths */
    public Map<Integer, List<Cell>> getAllPath() { return this.allPath; }

    /** @return active projectiles */
    public List<Projectil> getActiveProjectiles() { return activeProjectiles; }

    /**
     * Checks if a position is inside the board.
     *
     * @param row row index
     * @param col column index
     * @return true if inside, false otherwise
     */
    public boolean isInside(int row, int col) {
        return row >= 0 && row < height && col >= 0 && col < width;
    }

    /**
     * Places a tower on the board.
     *
     * @param tower the tower to place
     * @param position position to place the tower
     */
    public void placeTower(Tower tower, Position position) {
        tower.setPosition(position);
        towers.add(tower);
    }

    /**
     * Removes a tower from the board.
     *
     * @param tower the tower to remove
     */
    public void removeTower(Tower tower) {
        towers.remove(tower);
    }

    /**
     * Spawns a new balloon on the board.
     *
     * @param balloon the balloon to add
     */
    public void spawnBalloon(Balloon balloon) {

        balloon.setPath(getPathPositions());
        List<Position> path = balloon.getPath();

        if (path != null && !path.isEmpty()) {
            Position start = path.get(0);
            balloon.setStartPosition(start.getRow() + 0.5, start.getCol() + 0.5);
        }
        balloons.add(balloon);
    }

    /** @return list of towers */
    public List<Tower> getTowers() { return towers; }

    /** @return list of balloons */
    public List<Balloon> getBalloons() { return balloons; }

    /**
     * Updates the game state:
     * - towers shoot
     * - projectiles move
     * - balloons move
     * - remove dead or escaped balloons
     *
     * @return list of removed balloons
     */
    public List<Balloon> update() {
        for (Tower tower : towers) {
            try {
                tower.update(this.balloons, this.activeProjectiles);
            } catch (NotMatchException e) {
                e.getMessage();
            }
        }

        for (Projectil projectil : new ArrayList<>(this.activeProjectiles)) {
            projectil.update();
        }
        this.activeProjectiles.removeIf(p -> !p.isActive());

        for (Balloon balloon : new ArrayList<>(balloons)) {
            balloon.update();
            balloon.move();
        }

        List<Balloon> toRemove = new ArrayList<>();
        for (Balloon balloon : this.balloons) {
            if (balloon.hasEscaped()) {
                escapedBalloons.add(balloon);
                toRemove.add(balloon);
            }
            if (balloon.getHealth() <= 0 || !balloon.isActive()) {
                toRemove.add(balloon);
            }
        }
        this.balloons.removeAll(toRemove);

        return toRemove;
    }

    /**
     * Checks if a cell is available to place something.
     */
    public abstract boolean isCellAvailable(int row, int col);

    /** @return escaped balloons */
    public List<Balloon> getEscapedBalloons() { return this.escapedBalloons; }

    /** Clears escaped balloons list */
    public void clearEscapedBalloons() { this.escapedBalloons.clear(); }

    /**
     * Finds a random free position on the board.
     *
     * @return a free position or null if none
     */
    public Position findAvailablePosition(){
        List<Position> availabeCells = new ArrayList<>();
        Random rand = new Random();

        for(int i = 0 ; i < this.height ; i++){
            for(int j = 0; j < this.width; j++){
                if(isCellAvailable(i,j)){
                    availabeCells.add(new Position(i,j));
                }
            }
        }

        if(availabeCells.isEmpty()){
            return null;
        }

        int n = rand.nextInt(availabeCells.size());
        return availabeCells.get(n);
    }
}