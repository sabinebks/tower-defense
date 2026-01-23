package board;

/**
 * Une case du plateau
 */
public class Cell {

    private Position position;//La position de Cell
    private boolean isPath; // true si la cellule fait partie du chemin

    /**
     * Le constructeur de la classes
     * @param position
     */
    public Cell(Position position) {
        this.position = position;
        this.isPath = false;
    }
    /**
     * Retourner la position du Cell    
     * @return  position
     */
    public Position getPosition() {
        return position;
    }
    /**
     * Retourner si la cellule est le chemin ou non
     * @return  true si chemin, false sinon
     */
    public boolean isPath() {
        return isPath;
    }  
    /**
     * Modifier une cellule pour le met comme chemin
     */
    public void assignChemin() {
        this.isPath = true;
    }

    /**
     * Vérifie si la cellule est libre (pas encore dans le chemin)
     * On va modifier ca apres pour verifier qu'il n'y a pas de tour/ballon
     * @return true si pas dans chemin, false sinon
     */
    public boolean estVide() {
        return !isPath;
    }
}
