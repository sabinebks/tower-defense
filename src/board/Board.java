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
     * Retourne la cellule aux coordonnées (x, y)
     */
    public Cell getCell(int row, int col) {
        if (!isInside(row, col)) {
            throw new IllegalArgumentException("Coordonnées hors du plateau");
        }
        return grid[row][col];
    }

    /**
     * Vérifie si les coordonnées sont à l'intérieur du plateau
     */
    public boolean isInside(int row, int col) {
        return row >= 0 && row < height && col >= 0 && col < width;
    }

    /**
     * Retourne la largeur
     */
    public int getWidth() {
        return width;
    }

    /**
     * Retourne la hauteur
     */
    public int getHeight() {
        return height;
    }
}
