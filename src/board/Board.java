package board;

import java.util.List;

/**
 * Classe abstraite représentant un plateau de jeu.
 * Contient la grille de cellules et les méthodes communes.
 */
public abstract class Board {

    protected int width; // largeur du plateau
    protected int height; // hauteur du plateau
    protected Cell[][] grid; // grille de cellules

    /**
     * Constructeur du plateau
     * 
     * @param height la hauteur du plateau
     * @param width  la largeur du plateau
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
     * 
     * @param row Ligne
     * @param col Colonne
     * @return Cell correspondante
     * @throws IllegalArgumentException si la position est hors plateau
     */
    public Cell getCell(int row, int col) {
        if (!isInside(row, col)) {
            throw new IllegalArgumentException("Coordonnées hors du plateau");
        }
        return this.grid[row][col];
    }

    /**
     * Retourne la cellule à partir d'une Position
     * 
     * @param p Position
     * @return Cell correspondante
     * @throws IllegalArgumentException si la position est hors plateau
     */
    public Cell getCell(Position p) {
        return getCell(p.getRow(), p.getCol());
    }

    /**
     * Vérifie si les coordonnées sont à l'intérieur du plateau
     * 
     * @param row Ligne
     * @param col Colonne
     * @return true si la position est dans le plateau, false sinon
     */
    public boolean isInside(int row, int col) {
        return row >= 0 && row < height && col >= 0 && col < width;
    }

    /**
     * Retourne la largeur du plateau
     * 
     * @return largeur
     */
    public int getWidth() {
        return width;
    }

    /**
     * Retourne la hauteur du plateau
     * 
     * @return hauteur
     */
    public int getHeight() {
        return height;
    }

    /**
     * Retourne la grille complète
     * 
     * @return tableau 2D de cellules
     */
    public Cell[][] getGrid() {
        return this.grid;
    }

    /**
     * Méthode abstraite pour générer le chemin du plateau
     * Chaque sous-classe doit implémenter sa propre logique de chemin
     */
    public abstract void calculerPath();

    /**
     * //concerne juste le plateau FreeBoard
     * Retourne la position finale d'un chemin droit partant dune celulle du bord
     * Le chemin va directement vers le bord oppose
     *
     * @param depart La position de départ sur un bord
     * @return La position finale sur le bord oppose
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
     * concerne seulement le free board
     * enleve dans la liste bloque les positions depart et fin
     * ainsi que leurs 4 voisins, sans dépasser les bords du plateau
     *
     * @param depart Position de départ
     * @param fin    Position de fin
     * @param bloque Liste existante dans laquelle on sipprime les positions
     * @param width  Largeur du plateau
     * @param height Hauteur du plateau
     */
    public static void rmPositions(Position depart, Position fin, List<Position> bloque, int width, int height) {
        // Ajouter les positions elles-mêmes
        bloque.remove(depart);
        bloque.remove(fin);
        // re on verifie si les voisin sne depasse pas les bords avant de les ajouter
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
     * Retourne la direction d'un chemin droit entre deux positions alignee
     * 
     * @param depart position de depart
     * @param fin    position de fin
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
