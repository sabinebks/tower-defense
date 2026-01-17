package board;

/**
 * Une case du plateau
 */
public class Cell {

    private Position position;
    private boolean isPath; // true si la cellule fait partie du chemin

    public Cell(Position position) {
        this.position = position;
        this.isPath = false;
    }

    public Position getPosition() {
        return position;
    }

    public boolean isPath() {
        return isPath;
    }

    public void assignChemin() {
        this.isPath = true;
    }

    /**
     * Vérifie si la cellule est libre (pas encore dans le chemin)
     */
    public boolean estVide() {
        return !isPath;
    }
}
