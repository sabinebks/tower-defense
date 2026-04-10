package board;

<<<<<<< HEAD
=======
import entity.balloon.Balloon;
import entity.tower.Tower;
import entity.tower.projectil.NotMatchException;
import entity.tower.projectil.Projectil;

>>>>>>> origin/main
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

<<<<<<< HEAD
import entity.balloon.Balloon;
import entity.tower.Tower;
import entity.tower.projectil.NotMatchException;
import entity.tower.projectil.Projectil;

/**
 * Abstract class representing a game board.
 * Contains the grid of cells and common methods.
 */
=======
>>>>>>> origin/main
public abstract class Board {

    /**
     * Les attributs de Board
     * La hauteur
     * La largeur
     * Liste des Cells
     * Un map des chemins
     * Liste des towers
     * Liste des ballons
     * Liste des projectiles actives
     * Liste des ballons deja sorti sans mort
     */
    protected int width;
    protected int height;
    protected Cell[][] grid;
    protected Map<Integer, List<Cell>> allPath;
    protected List<Tower> towers;
    protected List<Balloon> balloons;
    protected List<Projectil> activeProjectiles;
    protected List<Balloon> escapedBalloons = new ArrayList<>();
    protected List<Cell> allCells;

    /**
     * Constructeur
     * @param height La hauteur 
     * @param width la largeur
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

    public abstract void calculerPath();
    public abstract void init();
    public abstract List<Position> getPathPositions();
    public abstract List<Cell> allCells();

    /**
     * Retourner un cell dans le board
     * @param row le row du cell
     * @param col la colonne du cell
     * @return le cell voulu
     */
    public Cell getCell(int row, int col) {
        if (!isInside(row, col))
            throw new IllegalArgumentException("Coordonnees hors du plateau");
        return this.grid[row][col];
    }

    /**
     * Retourner la largeur du board
     * @return largeur
     */
    public int getWidth() { return width; }
    /**
     * Retourner la hauteur du board
     * @return hauteur
     */
    public int getHeight() { return height; }
    /**
     * Retourner le tableau de Cell, donc le display
     * @return tableau de Cell
     */
    public Cell[][] getGrid() { return this.grid; }
    /**
     * Retourner tous les chemins
     * @return un map des chemins
     */
    public Map<Integer, List<Cell>> getAllPath() { return this.allPath; }
    /**
     * Retourner les projectiles actives
     * @return projectiles actives
     */
    public List<Projectil> getActiveProjectiles() { return activeProjectiles; }

    /**
     * Verifier si la pos est dans le board
     * @param row ligne du pos
     * @param col colonne du pos
     * @return true si dans board, false sinon
     */
    public boolean isInside(int row, int col) {
        return row >= 0 && row < height && col >= 0 && col < width;
    }
    /**
     * Placer un tower 
     * @param tower tower a place
     * @param position la position voulu a placer
     */
    public void placeTower(Tower tower, Position position) {
        tower.setPosition(position);
        towers.add(tower);
    }

    /**
     * Effacer un tower
     * @param tower le tower a effacer
     */
    public void removeTower(Tower tower) {
        towers.remove(tower);
    }

    /**
     * Methode a generer les ballons du jeu
     * @param balloon un ballon voulu a generer
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

    /**
     * Retourner la liste des towers
     * @return liste des towers
     */
    public List<Tower> getTowers() { return towers; }
    /**
     * Retourner la liste des ballons
     * @return liste des ballons
     */
    public List<Balloon> getBalloons() { return balloons; }

    /**
     * Methode pour mettre a jour les ballons, si un ballon sort du board, ou
     * est mort, il va etre effacer
     * @return la liste des ballons efface
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

<<<<<<< HEAD
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

    

=======
>>>>>>> origin/main
    public abstract boolean isCellAvailable(int row, int col);

    /**
     * Retourner la liste des ballons sortent dans mourir
     * @return liste des ballons
     */
    public List<Balloon> getEscapedBalloons() { return this.escapedBalloons; }

    /**
     * Effacer les ballons sortent
     */
    public void clearEscapedBalloons() { this.escapedBalloons.clear(); }

}