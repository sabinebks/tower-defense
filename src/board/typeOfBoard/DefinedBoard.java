package board.typeOfBoard;

import board.*;
import object.tower.Tower;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A board with a randomly generated path.
 * The path can go up, down, left, or right.
 * Each cell is used only once to avoid loops.
 */
public class DefinedBoard extends Board {

    // Last cell of the path (used to continue the generation)
    private Position cheminAv;

    // Starting position of the path
    private Position pointDeDepart;

    /**
     * Constructor for DefinedBoard.
     *
     * @param height Height of the board (top to bottom)
     * @param width  Width of the board (left to right)
     */
    public DefinedBoard(int height, int width) {
        super(height, width);
    }

    /**
     * Initializes the board by creating the grid
     * and starting the path generation.
     */
    @Override
    public void init() {
        grid = new Cell[height][width]; // tableau 2D des cellules
        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                grid[r][c] = new Cell(new Position(r, c));
            }
        }
        calculerPath();
    }



    /**
     * Returns the length of the path.
     *
     * @return path length
     */
    public int getCheminLong() {
        return this.allCells.size();
    }

    /**
     * Checks if a position is on the board border.
     *
     * @param p the position to check
     * @return true if the position is on a border, false otherwise
     */
    public boolean isBord(Position p) {
        return p.getRow() == 0 || p.getRow() == this.height - 1 || p.getCol() == 0 || p.getCol() == this.width - 1;
    }

    /**
     * Generates a random path.
     * The path starts on a border and ends on a different border cell.
     */
    @Override
    public void calculerPath() {
        Random rand = new Random();

        int minLen = (this.width + this.height) / 2;

        // Crée toutes les cellules de départ possibles (tous les bords)
        // Certaines cellules peuvent apparaître deux fois pour éviter un biais du random
        List<Position> listPositions = new ArrayList<>();

        // Première et dernière ligne
        for (int j = 0; j < width; j++) {
            listPositions.add(new Position(0, j));
            listPositions.add(new Position(height - 1, j));
        }

        // Première et dernière colonne
        for (int i = 1; i < height - 1; i++) {
            listPositions.add(new Position(i, 0));
            listPositions.add(new Position(i, width - 1));
        }

        // Choix aléatoire du point de départ
        Position pRandom = listPositions.get(rand.nextInt(listPositions.size()));
        this.pointDeDepart = pRandom;
        this.cheminAv = pRandom;

        // Marquer la cellule de départ comme faisant partie du chemin
        getCell(pRandom.getRow(),pRandom.getCol()).assignChemin();
        this.allCells.add(getCell(pRandom.getRow(),pRandom.getCol()));

        // Liste des positions bloquées (non utilisables)
        List<Position> bloque = new ArrayList<>();
        boolean fin = false;

        while (!fin) {
            // Liste des voisins valides (haut, bas, gauche, droite)
            List<Position> mesChoix = new ArrayList<>();

            // voisin gauche
            if (cheminAv.getCol() != 0) {
                Position voisin = new Position(cheminAv.getRow(), cheminAv.getCol() - 1);
                if (getCell(voisin.getRow(),voisin.getCol()).estVide() && !bloque.contains(voisin)) {
                    mesChoix.add(voisin);
                }
            }

            // voisin droit
            if (cheminAv.getCol() != width - 1) {
                Position voisin = new Position(cheminAv.getRow(), cheminAv.getCol() + 1);
                if (getCell(voisin.getRow(),voisin.getCol()).estVide() && !bloque.contains(voisin)) {
                    mesChoix.add(voisin);
                }
            }

            // voisin haut
            if (cheminAv.getRow() != 0) {
                Position voisin = new Position(cheminAv.getRow() - 1, cheminAv.getCol());
                if (getCell(voisin.getRow(),voisin.getCol()).estVide() && !bloque.contains(voisin)) {
                    mesChoix.add(voisin);
                }
            }

            // voisin bas
            if (cheminAv.getRow() != height - 1) {
                Position voisin = new Position(cheminAv.getRow() + 1, cheminAv.getCol());
                if (getCell(voisin.getRow(),voisin.getCol()).estVide() && !bloque.contains(voisin)) {
                    mesChoix.add(voisin);
                }
            }

            // Aucun voisin valide : fin du chemin
            if (mesChoix.isEmpty()) {
                break;
            }

            /* Empêche le chemin de se terminer trop tôt
             * juste après le point de départ
             */
            if (this.allCells.size() < minLen) {
                List<Position> filtre = new ArrayList<>();
                for (Position p : mesChoix) {
                    if (!(this.isBord(p) && !p.equals(pointDeDepart))) {
                        filtre.add(p);
                    }
                }
                if (!filtre.isEmpty()) {
                    mesChoix = filtre;
                }
            }

            // Choix aléatoire du prochain voisin
            Position randi = mesChoix.get(rand.nextInt(mesChoix.size()));
            getCell(randi.getRow(),randi.getCol()).assignChemin();
            this.allCells.add(getCell(randi.getRow(),randi.getCol()));

            // Bloque la colonne traversée pour éviter les croisements
            if (cheminAv.getCol() != randi.getCol()) {
                for (int rr = 0; rr < height; rr++) {
                    bloque.add(new Position(rr, cheminAv.getCol()));
                }
            }

            // Mise à jour de la cellule précédente
            cheminAv = randi;

            // Arrêt si on atteint un autre bord après la taille minimale
            if (this.allCells.size() >= minLen && isBord(randi) && !randi.equals(pointDeDepart)) {
                fin = true;
            }
        }
        this.allPath.put(0,this.allCells);
    }

    public List<Position> getPathPositions() {
        ArrayList list = new ArrayList();

        for(Cell var3 : this.allCells) {
            list.add(var3.getPosition());
        }

        return list;
    }

    @Override
    public boolean isCellAvailable(int row, int col) {
        if (!this.isInside(row, col)) {
            return false;
        } else {
            for(Position var4 : this.getPathPositions()) {
                if (var4.getRow() == row && var4.getCol() == col) {
                    return false;
                }
            }

            for(Tower var6 : this.towers) {
                if (var6.getPosition().getRow() == row && var6.getPosition().getCol() == col) {
                    return false;
                }
            }

            return true;
        }
    }

    @Override
    public List<Cell> allCells() {
        return this.allCells;
    }

}
