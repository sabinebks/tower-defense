package board;

/**
 * Represents a specific location (row, column) on the game board.
 */
public class Position {

    private int row; // the row index
    private int col; // the column index

    /**
     * Constructor for Position.
     * @param row The row index of the cell
     * @param col The column index of the cell
     */
    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /**
     * Returns the row index.
     * @return Row of the cell
     */
    public int getRow() {
        return row;
    }

    /**
     * Returns the column index.
     * @return Column of the cell
     */
    public int getCol() {
        return col;
    }

    /**
     * Compares two positions.
     * @param obj Object to compare
     * @return true if positions are identical, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Position other = (Position) obj;
        return this.row == other.row && this.col == other.col;
    }

    /**
     * Returns the position as a String.
     * @return String formatted as "(row,col)"
     */
    @Override
    public String toString() {
        return "(" + row + "," + col + ")";
    }
    @Override
public int hashCode() {
    return 31 * row + col;
}
}