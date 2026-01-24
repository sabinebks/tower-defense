package board;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * This class represents a FreeBoard where paths are straight lines 
 * going from one side of the board to the opposite side.
 */
public class FreeBoard extends Board {

    // Crée une map qui enregistre la position de départ pour chaque numéro de chemin
    private Map<Integer, Position> numChemins = new HashMap<>();
    private int cmp = 0; // Compteur pour le nombre de chemins

    // Crée une map où on enregistre les premières et les dernières
    // cellules du chemin créées par calculerPath
    private Map<Position, Position> chemins = new HashMap<>();

    /**
     * Constructor for FreeBoard.
     * @param height The height of the board
     * @param width  The width of the board
     */
    public FreeBoard(int height, int width) {
        super(height, width);
    }

    /**
     * Initializes the board by creating a grid of Cells.
     */
    @Override
    public void init() {
        grid = new Cell[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                grid[i][j] = new Cell(new Position(i, j));
            }
        }
    }

    /**
     * Calculates all straight paths across the board.
     * Each path is created randomly until no more border cells are available.
     */
    @Override
    public void calculerPath() {
        // Reset counters and maps if the method is called again
        this.cmp = 0;
        this.numChemins.clear();
        this.chemins.clear();

        Random rand = new Random();

        // Crée toutes les cellules de départ possibles (tous les bords)
        // Pris en compte les cellules qui peuvent être rajoutées deux fois 
        // pour que le rand ne soit pas biaisé
        List<Position> listPositions = new ArrayList<>();
        
        // Sauvegarder la liste de la première ligne et la dernière
        for (int j = 0; j < width; j++) {
            listPositions.add(new Position(0, j));
            listPositions.add(new Position(height - 1, j));
        }
        // Sauvegarder la liste de la première colonne et la dernière
        for (int i = 1; i < height - 1; i++) {
            listPositions.add(new Position(i, 0));
            listPositions.add(new Position(i, width - 1));
        }

        boolean fin = false;

        while (!fin) {
            // Si la liste est vide, sortir de la boucle (fin de création de chemins)
            if (listPositions.isEmpty()) {
                fin = true;
                break;
            }

            // Choix random du point de départ
            Position pRandom = listPositions.get(rand.nextInt(listPositions.size()));
            Position pFin = getFinChemin(pRandom);

            // Marquer toutes les cellules entre le départ et la fin comme faisant partie du chemin
            marquerCheminDroit(pRandom, pFin);

            // Enlever les positions déjà utilisées de la liste listPositions 
            // (ne peuvent pas être re-choisies comme début ou fin d'un autre chemin)
            rmPositions(pRandom, pFin, listPositions, width, height);
            
            // Enregistrer les données du chemin
            chemins.put(pRandom, pFin);
            numChemins.put(cmp, pRandom);
            cmp++;
        }
    }

    /**
     * Helper method to mark all cells in a straight line as path cells.
     * @param start Starting position
     * @param end   Ending position
     */
    private void marquerCheminDroit(Position start, Position end) {
        Direction dir = getDirection(start, end);

        switch (dir) {
            case RIGHT:
                for (int c = start.getCol(); c <= end.getCol(); c++) {
                    getCell(start.getRow(), c).assignChemin();
                }
                break;
            case LEFT:
                for (int c = start.getCol(); c >= end.getCol(); c--) {
                    getCell(start.getRow(), c).assignChemin();
                }
                break;
            case DOWN:
                for (int r = start.getRow(); r <= end.getRow(); r++) {
                    getCell(r, start.getCol()).assignChemin();
                }
                break;
            case UP:
                for (int r = start.getRow(); r >= end.getRow(); r--) {
                    getCell(r, start.getCol()).assignChemin();
                }
                break;
            default:
                throw new IllegalArgumentException("Positions non alignées !");
        }
    }

    /**
     * Returns the start and end positions of a specific path using its ID.
     * @param num The path index
     * @return An array containing [startPosition, endPosition]
     * @throws IllegalArgumentException if the index is invalid
     */
    public Position[] retourneLesChemins(int num) {
        // On vérifie si num n'est pas négatif ou dépasse le compteur
        if (num >= 0 && num < cmp) {
            Position debut = numChemins.get(num);
            Position fin = chemins.get(debut);
            // On retourne le couple début, fin de positions 
            return new Position[]{debut, fin};
        } else {
            throw new IllegalArgumentException("Numéro de chemin invalide");
        }
    }

    /**
     * Returns the total number of paths generated on the board.
     * @return Number of paths
     */
    public int retourneNbrChemins() {
        return cmp;
    }

    /**
     * Returns the map that links starting positions to ending positions.
     * @return The map of paths
     */
    public Map<Position, Position> getCheminsMap() {
        return chemins;
    }
}