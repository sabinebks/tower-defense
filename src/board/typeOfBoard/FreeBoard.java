package board.typeOfBoard;

import board.*;
import entity.balloon.Balloon;
import entity.tower.Tower;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class FreeBoard extends Board {

    /**
     * Les attributs de FreeBoard
     * Le nombre static de chemin
     * Un map des pos de chemin et ses id
     * Un map des positions du chemins
     * Le nombre de chemin voulu
     */
    private static final int NBCHEMIN = 5;
    private Map<Integer, Position> numChemins;
    private int cmp = 0;
    private Map<Position, Position> chemins;
    private int nbCheminsVoulus;

    /**
     * Constructeur de FreeBoard    
     * @param height hauteur du board
     * @param width largeur du board
     */
    public FreeBoard(int height, int width, int nbChemin) {
        super(height, width);
        this.nbCheminsVoulus = nbChemin;
        init();
    }

    /**
     * Retourner la direction du chemin
     * @param depart la position depart
     * @param fin la position fini
     * @return la direction
     */
    public Direction getDirection(Position depart, Position fin) {
        if (depart.getRow() == fin.getRow())
            return depart.getCol() < fin.getCol() ? Direction.RIGHT : Direction.LEFT;
        else if (depart.getCol() == fin.getCol())
            return depart.getRow() < fin.getRow() ? Direction.DOWN : Direction.UP;
        else
            throw new IllegalArgumentException("Les positions doivent être alignées");
    }

    /**
     * Un setter pour le nombre de chemins voulu
     * @param nb
     */
    public void setNbCheminsVoulus(int nb) { 
        this.nbCheminsVoulus = nb; 
    }

    @Override
    /**
     * Initialiser le board
     */
    public void init() {
        grid = new Cell[height][width];
        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++)
                grid[i][j] = new Cell(new Position(i, j));
        calculerPath();
    }

    @Override
    /**
     * Une methode pour auto genere le chemin aleatoirement
     */
    public void calculerPath() {
        this.cmp = 0;
        this.numChemins = new HashMap<>();
        this.chemins = new HashMap<>();
        this.allPath.clear();

        // FIX : nbCheminsVoulus pas encore initialisé par le constructeur
        // car calculerPath() est appelé AVANT via super() → on force la valeur
        if (this.nbCheminsVoulus == 0) this.nbCheminsVoulus = NBCHEMIN;

        Random rand = new Random();
        List<Position> listPositions = new ArrayList<>();

        for (int j = 0; j < width; j++) {
            listPositions.add(new Position(0, j));
            listPositions.add(new Position(height - 1, j));
        }
        for (int i = 1; i < height - 1; i++) {
            listPositions.add(new Position(i, 0));
            listPositions.add(new Position(i, width - 1));
        }

        while (cmp < nbCheminsVoulus && !listPositions.isEmpty()) {
            Position pRandom = listPositions.get(rand.nextInt(listPositions.size()));
            Position pFin = getFinChemin(pRandom);

            if (listPositions.contains(pFin)) {
                marquerCheminDroit(pRandom, pFin);
                listPositions.remove(pRandom);
                listPositions.remove(pFin);
                chemins.put(pRandom, pFin);
                numChemins.put(cmp, pRandom);
                cmp++;
            } else {
                listPositions.remove(pRandom);
            }
        }

        // Construire allPath une seule fois
        for (int i = 0; i < cmp; i++) {
            Position start = numChemins.get(i);
            Position end = chemins.get(start);
            if (start == null || end == null) continue;

            Direction dir = getDirection(start, end);
            List<Cell> list = new ArrayList<>();

            switch (dir) {
                case RIGHT:
                    for (int c = start.getCol(); c <= end.getCol(); c++)
                        list.add(getCell(start.getRow(), c));
                    break;
                case LEFT:
                    for (int c = start.getCol(); c >= end.getCol(); c--)
                        list.add(getCell(start.getRow(), c));
                    break;
                case DOWN:
                    for (int r = start.getRow(); r <= end.getRow(); r++)
                        list.add(getCell(r, start.getCol()));
                    break;
                case UP:
                    for (int r = start.getRow(); r >= end.getRow(); r--)
                        list.add(getCell(r, start.getCol()));
                    break;
            }
            allPath.put(i, list);
            this.allCells.addAll(list);
        }
    }

    /**
     * Fonction pour marquer le droit du chemin
     * @param start le debut du chemin
     * @param end la fin du chemin
     */
    public void marquerCheminDroit(Position start, Position end) {
        Direction dir = getDirection(start, end);
        switch (dir) {
            case RIGHT:
                for (int c = start.getCol(); c <= end.getCol(); c++)
                    getCell(start.getRow(), c).assignChemin();
                break;
            case LEFT:
                for (int c = start.getCol(); c >= end.getCol(); c--)
                    getCell(start.getRow(), c).assignChemin();
                break;
            case DOWN:
                for (int r = start.getRow(); r <= end.getRow(); r++)
                    getCell(r, start.getCol()).assignChemin();
                break;
            case UP:
                for (int r = start.getRow(); r >= end.getRow(); r--)
                    getCell(r, start.getCol()).assignChemin();
                break;
            default:
                throw new IllegalArgumentException("Positions non alignées !");
        }
    }

    /**
     * Retourner la fin du chemin
     * @param start le depart du chemin
     * @return la position fini
     */
    public Position getFinChemin(Position start) {
        if (start.getRow() == 0) return new Position(height - 1, start.getCol());
        if (start.getRow() == height - 1) return new Position(0, start.getCol());
        if (start.getCol() == 0) return new Position(start.getRow(), width - 1);
        return new Position(start.getRow(), 0);
    }

    /**
     * Retourner une liste des positions des chemins
     * @param num le nombre de chemin
     * @return une liste des positions
     * @throws IllegalArgumentException si le nombre voulu est plus grand qu'au nombre cmp
     */
    public Position[] retourneLesChemins(int num) throws IllegalArgumentException {
        if (num >= 0 && num < cmp) {
            Position debut = numChemins.get(num);
            return new Position[]{debut, chemins.get(debut)};
        }
        throw new IllegalArgumentException("Numéro de chemin invalide");
    }

    /**
     * Retourner le nombre de chemin
     * @return nombre de chemin
     */
    public int retourneNbrChemins() { return cmp; }

    /**
     * Retourner le map de chemin
     * @return le map de chemin
     */
    public Map<Position, Position> getCheminsMap() { return chemins; }


    @Override
    /**
     * Retourner la liste des positions du chemin
     * @return une liste des positions
     */
    public List<Position> getPathPositions() {
        if (allPath.isEmpty()) return new ArrayList<>();

        Random rand = new Random();
        List<Cell> randomPath = new ArrayList<>(allPath.get(rand.nextInt(allPath.size())));

        List<Position> positions = new ArrayList<>();
        for (Cell cell : randomPath) {
            positions.add(cell.getPosition());
        }

        return positions;
    }

    @Override
    /**
     * Retourner si la position est disponible
     * @param row le row du cell
     * @param col la colonne du cell
     * @return true si disponible, false sinon
     */
    public boolean isCellAvailable(int row, int col) {
        if (!isInside(row, col)) return false;
        for (Tower t : towers)
            if (t.getPosition().getRow() == row && t.getPosition().getCol() == col) return false;
        return true;
    }


    /**
     * Retourner la liste des cells dans le board
     * @return liste des cells
     */
    @Override
    public List<Cell> allCells(){
        return allCells;
    }

}
