package board;

/**
 * Une cellule du plateau.
 * Chaque cellule peut faire partie d’un chemin ou être vide.
 */
public class Cell {

    /** Position de la cellule dans la grille */
    private Position position;

    /** true si la cellule fait partie du chemin */
    private boolean isPath;
   


    /**
     * Constructeur de la cellule
     *
     * @param position La position de la cellule dans le plateau
     */
    public Cell(Position position) {
        this.position = position;
        this.isPath = false; // par défaut, la cellule n’est pas sur le chemin
    }

    /**
     * Retourne la position de la cellule
     *
     * @return Objet Position de la cellule
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Vérifie si la cellule fait partie du chemin
     *
     * @return true si la cellule est sur le chemin, false sinon
     */
    public boolean isPath() {
        return isPath;
    }

    /**
     * Marque la cellule comme faisant partie du chemin
     */
    public void assignChemin() {
        this.isPath = true;
    }


    /**
     * Vérifie si la cellule est libre (pas encore utilisée dans le chemin)
     *
     * @return true si la cellule est vide, false si elle fait déjà partie du chemin
     */
    public boolean estVide() {
        return !isPath;
    }

    /**
     * Retourne une représentation lisible de la cellule pour debug/affichage
     * Exemple : "C(2,3)" si chemin à la position (2,3) ou "X(2,3)" si vide
     *
     * @return String représentant la cellule
     */
    @Override
    public String toString() {
        return (isPath ? "C" : "X") + "(" + position.getRow() + "," + position.getCol() + ")";
    }
}
