package board;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entity.balloon.Balloon;
import entity.tower.Tower;
import entity.tower.projectil.NotMatchException;
import entity.tower.projectil.Projectil;

/**
 * Abstract class representing a game board.
 * Contains the grid of cells and common methods.
 */
public abstract class Board {

    protected int width;
    protected int height;
    protected Cell[][] grid;
    protected Map<Integer, List<Cell>> allPath;
    protected List<Tower> towers;
    protected List<Balloon> balloons;
    protected List<Projectil> activeProjectiles;
    protected List<Balloon> escapedBalloons = new ArrayList<>();
    protected List<Cell> allCells;


    public Board(int height, int width) {
        this.width = width;
        this.height = height;
        this.allPath = new HashMap<>();
        this.towers = new ArrayList<>();
        this.balloons = new ArrayList<>();
        this.activeProjectiles = new ArrayList<>();
        this.allCells = new ArrayList();
        init();
    }

    public abstract void calculerPath();
    public abstract void init();
    public abstract List<Position> getPathPositions();



    public abstract List<Cell> allCells();
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


    public void placeTower(Tower tower, Position position) {
        tower.setPosition(position);
        towers.add(tower);
    }

    public void removeTower(Tower tower) {
        towers.remove(tower);
    }

    public void spawnBalloon(Balloon balloon) {
        // Ne PAS écraser le path si le ballon en a déjà un (assigné par Main)
        List<Position> path = balloon.getPath();
        if (path == null || path.isEmpty()) {
            balloon.setPath(getPathPositions());
            path = balloon.getPath();
        }
        // Positionner le ballon au début de son chemin
        if (path != null && !path.isEmpty()) {
            Position start = path.get(0);
            balloon.setStartPosition(start.getRow() + 0.5, start.getCol() + 0.5);
        }
        balloons.add(balloon);
    }

    public List<Balloon> update() {
        for (Balloon balloon : new ArrayList<>(balloons)) {
            balloon.update();
            balloon.move();
        }

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

        // Collecter AVANT de supprimer
        List<Balloon> toRemove = new ArrayList<>();
        for (Balloon balloon : this.balloons) {
            if (balloon.getHealth() <= 0 || !balloon.isActive()) {
                if (balloon.hasEscaped()) {
                    escapedBalloons.add(balloon);
                }
                toRemove.add(balloon);
            }
        }
        this.balloons.removeAll(toRemove);
        this.activeProjectiles.removeIf(p -> !p.isActive());

        return toRemove;
    }

    public List<Tower> getTowers() { return towers; }
    public List<Balloon> getBalloons() { return balloons; }

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

    public abstract boolean isCellAvailable(int row, int col);

    public List<Balloon> getEscapedBalloons() { return new ArrayList<>(escapedBalloons); }
    public void clearEscapedBalloons() { escapedBalloons.clear(); }
   
}