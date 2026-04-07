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

    private static final int NBCHEMIN = 5;
    private Map<Integer, Position> numChemins;
    private int cmp = 0;
    private Map<Position, Position> chemins;
    private int nbCheminsVoulus;

    public FreeBoard(int height, int width) {
        super(height, width);
        this.nbCheminsVoulus = NBCHEMIN;
    }

    public Direction getDirection(Position depart, Position fin) {
        if (depart.getRow() == fin.getRow())
            return depart.getCol() < fin.getCol() ? Direction.RIGHT : Direction.LEFT;
        else if (depart.getCol() == fin.getCol())
            return depart.getRow() < fin.getRow() ? Direction.DOWN : Direction.UP;
        else
            throw new IllegalArgumentException("Les positions doivent être alignées");
    }

    public void setNbCheminsVoulus(int nb) { this.nbCheminsVoulus = nb; }

    @Override
    public void init() {
        grid = new Cell[height][width];
        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++)
                grid[i][j] = new Cell(new Position(i, j));
        calculerPath();
    }

    @Override
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

    public Position getFinChemin(Position start) {
        if (start.getRow() == 0) return new Position(height - 1, start.getCol());
        if (start.getRow() == height - 1) return new Position(0, start.getCol());
        if (start.getCol() == 0) return new Position(start.getRow(), width - 1);
        return new Position(start.getRow(), 0);
    }

    public Position[] retourneLesChemins(int num) {
        if (num >= 0 && num < cmp) {
            Position debut = numChemins.get(num);
            return new Position[]{debut, chemins.get(debut)};
        }
        throw new IllegalArgumentException("Numéro de chemin invalide");
    }

    public int retourneNbrChemins() { return cmp; }
    public Map<Position, Position> getCheminsMap() { return chemins; }


    @Override
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
    public boolean isCellAvailable(int row, int col) {
        if (!isInside(row, col)) return false;
        for (Tower t : towers)
            if (t.getPosition().getRow() == row && t.getPosition().getCol() == col) return false;
        return true;
    }


    @Override
    public List<Cell> allCells(){
        return allCells;
    }
}