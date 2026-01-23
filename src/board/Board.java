package board;


/**
 * Classe abstraite représentant un plateau de jeu.
 * Contient la grille de cellules et les méthodes communes.
 */
public abstract class Board {

    protected int width;    // largeur du plateau
    protected int height;   // hauteur du plateau
    protected Cell[][] grid; // grille de cellules

    /**
     * Constructeur du plateau
     * @param height la hauteur
     * @param width la largeur
     */
    public Board(int height, int width) {
        this.width = width;
        this.height = height;
        init(); // chaque sous-classe définit comment initialiser le plateau
    }

    /**
     * Méthode abstraite : chaque plateau initialise la grille à sa façon
     */
    protected abstract void init();

    /**
     * Retourne la cellule aux coordonnées (row, col)
     * @param row
     * @param col
     * @return la cellule 
     */
    public Cell getCell(int row, int col) {
        if (!isInside(row, col)) {
            throw new IllegalArgumentException("Coordonnées hors du plateau");
        }
        return this.grid[row][col];
    }

    /**
     * Vérifie si les coordonnées sont à l'intérieur du plateau
     * @param row la ligne
     * @param col la colonne
     * @return true si coor est dedans, false sinon
     */
    public boolean isInside(int row, int col) {
        return row >= 0 && row < height && col >= 0 && col < width;
    }

    /**
     * Retourne la largeur
     * @return largeur
     */
    public int getWidth() {
        return width;
    }

    /**
     * Retourne la hauteur
     * @return hauteur
     */
    public int getHeight() {
        return height;
    }

    /**
     * Retourne la grille
     * @return la grille
     */
    public Cell[][] getGrid() {
        return this.grid;
    }
    /**
     * Methode abstract a override
     */
    public abstract void calculerPath();
}
