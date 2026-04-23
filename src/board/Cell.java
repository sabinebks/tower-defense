package board;
import java.util.*;

import entity.balloon.*;;
/**
 * Represents a cell on the game board.
 * Each cell has a position and can either be part of a path or empty.
 */
public class Cell {

    private Position position;
    private boolean isPath;
    private List<Balloon> balloons; // la liste des ballons

    /**
     * creates a new cell at the given position
     * @param position Position of the cell on the board
     */
    public Cell(Position position) {
        this.position = position;
        this.isPath = false; // by default, the cell is not part of a path
        this.balloons = new ArrayList<>();
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

    /**
     * Ajouter un ballon dans le cell
     * @param b ballon a ajouter
     */
    public void addBalloon(Balloon b) {
        balloons.add(b);
    }

    /**
     * Effacer un ballon dans le cell
     * @param b ballon a effacer
     */
    public void removeBalloon(Balloon b) {
        balloons.remove(b);
    }

    /**
     * Retourner la liste des ballons dans ce cell
     * @return liste des ballons
     */
    public List<Balloon> getBalloons() {
        return this.balloons;
    }

    /**
     * Retourner le nombre de ballons dans ce cell
     * @return n le nombre de ballons
     */
    public int getNumBalloon() {
        return balloons.size();
    }
    
    @Override
    public boolean equals(Object o){
        if(!(o instanceof Cell)){
            return false;
        }
        Cell other = (Cell) o;
        if(this.position.equals(other.getPosition()) && this.isPath == other.isPath){
            return true;
        }
        return false;
    }

}
