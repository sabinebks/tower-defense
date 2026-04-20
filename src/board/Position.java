package board;

/**
 * This class represents a position on a game board.
 * A position is defined by a row and a column.
 */
public class Position {

    private int row; // row number
    private int col; // column number

    /**
     * Creates a new Position with a given row and column.
     *
     * @param row the row of the position
     * @param col the column of the position
     */
    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /**
     * Returns the row of the position.
     *
     * @return the row value
     */
    public int getRow() {
        return row;
    }

    /**
     * Returns the column of the position.
     *
     * @return the column value
     */
    public int getCol() {
        return col;
    }

    /**
     * Compares this position with another object.
     * Two positions are equal if they have the same row and column.
     *
     * @param obj the object to compare
     * @return true if the positions are the same, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Position other = (Position) obj;
        return this.row == other.row && this.col == other.col;
    }

    /**
     * Returns a string representation of the position.
     * Format: (row,col)
     *
     * @return the position as a string
     */
    @Override
    public String toString() {
        return "(" + row + "," + col + ")";
    }

    /**
     * Generates a hash code for this position.
     *
     * @return hash code based on row and column
     */
    @Override
    public int hashCode() {
        return 31 * row + col;
    }
}