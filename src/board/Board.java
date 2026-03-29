package board;

import java.util.List;

/**
 * Abstract class representing a game board.
 * Contains the grid of cells and common methods.
 */
public abstract class Board {

    protected int width; // largeur du plateau
    protected int height; // hauteur du plateau
    protected Cell[][] grid; // grille de cellules

    /**
     * Board constructor.
     * @param height the height of the board
     * @param width  the width of the board
     */
    public Board(int height, int width) {
        this.width = width;
        this.height = height;
        init(); // chaque sous-classe définit comment initialiser le plateau
    }

    /**
     * Abstract method: each board initializes the grid in its own way.
     */
    protected abstract List<Cell> getChemins();
    protected abstract void init();
    public abstract  List<Position> getPathPositions();

    /**
     * Returns the cell at the specified coordinates (row, col).
     * @param row Row index
     * @param col Column index
     * @return The corresponding Cell
     * @throws IllegalArgumentException if coordinates are outside the board
     */
    public Cell getCell(int row, int col) {
        if (!isInside(row, col)) {
            throw new IllegalArgumentException("Coordonnées hors du plateau");
        }
        return this.grid[row][col];
    }

    /**
     * Returns the cell at a given Position.
     * @param p Position object
     * @return The corresponding Cell
     * @throws IllegalArgumentException if position is outside the board
     */
    public Cell getCell(Position p) {
        return getCell(p.getRow(), p.getCol());
    }

    /**
     * Checks if coordinates are inside the board boundaries.
     * @param row Row index
     * @param col Column index
     * @return true if inside, false otherwise
     */
    public boolean isInside(int row, int col) {
        return row >= 0 && row < height && col >= 0 && col < width;
    }

    /**
     * Returns the board width.
     * @return width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Returns the board height.
     * @return height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Returns the full grid.
     * @return 2D array of cells
     */
    public Cell[][] getGrid() {
        return this.grid;
    }

    /**
     * Abstract method to generate the board path.
     * Each subclass must implement its own path logic.
     */
    public abstract void calculerPath();

    /**
     * Returns the end position of a straight path starting from a border cell.
     * The path goes directly to the opposite border.
     * (concerne juste le plateau FreeBoard)
     * @param depart The starting position on a border
     * @return The final position on the opposite border
     */
    protected Position getFinChemin(Position depart) {
        int row = depart.getRow();
        int col = depart.getCol();

        // Si depart sur un bord droite ou gauche
        if (col == 0)
            return new Position(row, width - 1); // gauche vers droite
        if (col == width - 1)
            return new Position(row, 0); // droite vers gauche

        // Si depart sur un bord haut ou bas
        if (row == 0)
            return new Position(height - 1, col); // haut vers bas
        if (row == height - 1)
            return new Position(0, col); // bas vers haut

        // dans le cas ou depart=fin
        return depart;
    }

    /**
     * Removes the start, end, and their 4 neighbors from the blocked list.
     * concerne seulement le free board
     * @param depart Start position
     * @param fin    End position
     * @param bloque List of blocked positions
     * @param width  Board width
     * @param height Board height
     */
    public static void rmPositions(Position depart, Position fin, List<Position> bloque, int width, int height) {
        // Ajouter les positions elles-mêmes
        bloque.remove(depart);
        bloque.remove(fin);
        // on verifie si les voisin ne depassent pas les bords avant de les ajouter
        // Ajouter voisins de depart
        if (depart.getRow() > 0)
            bloque.remove(new Position(depart.getRow() - 1, depart.getCol())); // haut
        if (depart.getRow() < height - 1)
            bloque.remove(new Position(depart.getRow() + 1, depart.getCol())); // bas
        if (depart.getCol() > 0)
            bloque.remove(new Position(depart.getRow(), depart.getCol() - 1)); // gauche
        if (depart.getCol() < width - 1)
            bloque.remove(new Position(depart.getRow(), depart.getCol() + 1)); // droite

        // Ajouter voisins de fin
        if (fin.getRow() > 0)
            bloque.remove(new Position(fin.getRow() - 1, fin.getCol())); // haut
        if (fin.getRow() < height - 1)
            bloque.remove(new Position(fin.getRow() + 1, fin.getCol())); // bas
        if (fin.getCol() > 0)
            bloque.remove(new Position(fin.getRow(), fin.getCol() - 1)); // gauche
        if (fin.getCol() < width - 1)
            bloque.remove(new Position(fin.getRow(), fin.getCol() + 1)); // droite
    }

    /**
     * Returns the direction of a straight path between two aligned positions.
     * @param depart Starting position
     * @param fin   End position
     * @return Direction (UP, DOWN, LEFT, RIGHT)
     */
    public static Direction getDirection (Position depart, Position fin) {
        
        // Même ligne gauche ou droite
        if (depart.getRow() == fin.getRow()) {
            if (depart.getCol() < fin.getCol()) {

                return Direction.RIGHT;
            } else {
                return Direction.LEFT;
            }
        }
        // Même colonne up or down
        else if (depart.getCol() == fin.getCol()) {
            if (depart.getRow() < fin.getRow()) {
                return Direction.DOWN;
            } else {
                return Direction.UP;
            }
        }
         // Cas impossible : positions non alignées
    else {
        throw new IllegalArgumentException("Les positions doivent être aligne");
    }

    }
   
}