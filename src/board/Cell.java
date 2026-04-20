package board;

import java.util.*;
import entity.balloon.*;

/**
 * This class represents a cell on the game board.
 * Each cell has a position and can be a path or not.
 * A cell can also contain balloons.
 */
public class Cell {

    private Position position;   // position of the cell
    private boolean isPath;      // true if the cell is part of a path
    private List<Balloon> balloons; // list of balloons in the cell

    /**
     * Creates a new cell with a given position.
     *
     * @param position the position of the cell
     */
    public Cell(Position position) {
        this.position = position;
        this.isPath = false; // default: not a path
        this.balloons = new ArrayList<>();
    }

    /**
     * Returns the position of the cell.
     *
     * @return the position
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Checks if the cell is a path.
     *
     * @return true if it is a path, false otherwise
     */
    public boolean isPath() {
        return isPath;
    }

    /**
     * Sets the cell as a path.
     */
    public void assignChemin() {
        this.isPath = true;
    }

    /**
     * Checks if the cell is empty (not a path).
     *
     * @return true if empty, false otherwise
     */
    public boolean estVide() {
        return !isPath;
    }

    /**
     * Returns a string representation of the cell.
     * "C" means path, "X" means not a path.
     *
     * @return the cell as a string
     */
    @Override
    public String toString() {
        return (isPath ? "C" : "X") + "(" + position.getRow() + "," + position.getCol() + ")";
    }

    /**
     * Adds a balloon to the cell.
     *
     * @param b the balloon to add
     */
    public void addBalloon(Balloon b) {
        balloons.add(b);
    }

    /**
     * Removes a balloon from the cell.
     *
     * @param b the balloon to remove
     */
    public void removeBalloon(Balloon b) {
        balloons.remove(b);
    }

    /**
     * Returns the list of balloons in the cell.
     *
     * @return list of balloons
     */
    public List<Balloon> getBalloons() {
        return this.balloons;
    }

    /**
     * Returns the number of balloons in the cell.
     *
     * @return number of balloons
     */
    public int getNumBalloon() {
        return balloons.size();
    }

    /**
     * Compares this cell with another object.
     * Two cells are equal if they have the same position and same path state.
     *
     * @param o the object to compare
     * @return true if equal, false otherwise
     */
    @Override
    public boolean equals(Object o){
        if(!(o instanceof Cell)){
            return false;
        }
        Cell other = (Cell) o;
        return this.position.equals(other.getPosition()) && this.isPath == other.isPath;
    }
}