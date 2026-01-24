package board;

/**
 * Represents a cell on the game board.
 * Each cell has a position and can either be part of a path or empty.
 */
public class Cell {

    private Position position;
    private boolean isPath;

    /**
     * creates a new cell at the given position
     * @param position Position of the cell on the board
     */
    public Cell(Position position) {
        this.position = position;
        this.isPath = false; // by default, the cell is not part of a path
    }

    /**
     * Returns the position of the cell.
     * @return the cell position
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Checks if the cell is part of a path.
     * @return true if the cell is on a path, false otherwise
     */
    public boolean isPath() {
        return isPath;
    }

    /**
     * Marks the cell as part of a path.
     */
    public void assignChemin() {
        this.isPath = true;
    }

    /**
     * Checks if the cell is free (not used in a path).
     * @return true if the cell is empty, false if it is part of a path
     */
    public boolean estVide() {
        return !isPath;
    }

    /**
     * Returns a string representation of the cell.
     * @return a string describing the cell
     */
    @Override
    public String toString() {
        return (isPath ? "C" : "X") + "(" + position.getRow() + "," + position.getCol() + ")";
    }

}
