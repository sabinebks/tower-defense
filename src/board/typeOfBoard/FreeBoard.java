package board.typeOfBoard;

import board.*;
import object.tower.Tower;

import java.util.*;

/**
 * This class represents a FreeBoard where paths are straight lines
 * going from one side of the board to the opposite side.
 */
public class FreeBoard extends Board {

    private static final int NBCHEMIN = 5;
    private Map<Integer, Position> numChemins;
    private int cmp = 0;
    private Map<Position, Position> chemins;
    private int nbCheminsVoulus = 5;

    /**
     * Constructor for FreeBoard.
     *
     * @param height The height of the board
     * @param width  The width of the board
     */
    public FreeBoard(int height, int width) {
        super(height, width);
    }

    /**
     * Définit le nombre de chemins rectilignes à créer.
     *
     * @param nb nombre de chemins
     */
    public void setNbCheminsVoulus(int nb) {
        this.nbCheminsVoulus = nb;
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
        this.calculerPath();
    }

    /**
     * Calculates all straight paths across the board.
     * Each path is created randomly until no more border cells are available.
     */
    public void calculerPath() {
        this.cmp = 0;
        this.numChemins = new HashMap();
        this.chemins = new HashMap();
        this.allPath.clear();
        if (this.nbCheminsVoulus == 0) {
            this.nbCheminsVoulus = NBCHEMIN;
        }

        Random rand = new Random();
        ArrayList chemin = new ArrayList();

        for (int i = 0; i < this.width; ++i) {
            chemin.add(new Position(0, i));
            chemin.add(new Position(this.height - 1, i));
        }

        for (int var9 = 1; var9 < this.height - 1; ++var9) {
            chemin.add(new Position(var9, 0));
            chemin.add(new Position(var9, this.width - 1));
        }

        while (this.cmp < this.nbCheminsVoulus && !chemin.isEmpty()) {
            Position randp = (Position) chemin.get(rand.nextInt(chemin.size()));
            Position lastp = this.getFinChemin(randp);
            if (chemin.contains(lastp)) {
                this.marquerCheminDroit(randp, lastp);
                chemin.remove(randp);
                chemin.remove(lastp);
                this.chemins.put(randp, lastp);
                this.numChemins.put(this.cmp, randp);
                ++this.cmp;
            } else {
                chemin.remove(randp);
            }
        }

        for (int i = 0; i < this.cmp; ++i) {
            Position pathi = (Position) this.numChemins.get(i);
            Position position = (Position) this.chemins.get(pathi);
            if (pathi != null && position != null) {
                Direction direction = this.getDirection(pathi, position);
                ArrayList list = new ArrayList();
                switch (direction) {
                    case RIGHT:
                        for (int col = pathi.getCol(); col <= position.getCol(); ++col) {
                            list.add(this.getCell(pathi.getRow(), col));
                        }
                        break;
                    case LEFT:
                        for (int col = pathi.getCol(); col >= position.getCol(); --col) {
                            list.add(this.getCell(pathi.getRow(), col));
                        }
                        break;
                    case DOWN:
                        for (int row = pathi.getRow(); row <= position.getRow(); ++row) {
                            list.add(this.getCell(row, pathi.getCol()));
                        }
                        break;
                    case UP:
                        for (int row = pathi.getRow(); row >= position.getRow(); --row) {
                            list.add(this.getCell(row, pathi.getCol()));
                        }
                }

                this.allPath.put(i, list);
                this.allCells.addAll(list);
            }
        }

    }

    /**
     * Helper method to mark all cells in a straight line as path cells.
     *
     * @param start Starting position
     * @param end   Ending position
     */
    public void marquerCheminDroit(Position start, Position end) {
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
     * Calcule la cellule de fin à l'opposé du départ.
     */
    public Position getFinChemin(Position start) {
        if (start.getRow() == 0)
            return new Position(height - 1, start.getCol());

        if (start.getRow() == height - 1)
            return new Position(0, start.getCol());

        if (start.getCol() == 0)
            return new Position(start.getRow(), width - 1);

        return new Position(start.getRow(), 0);
    }

    /**
     * Returns the start and end positions of a specific path using its ID.
     *
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
     *
     * @return Number of paths
     */
    public int retourneNbrChemins() {
        return cmp;
    }

    /**
     * Returns the map that links starting positions to ending positions.
     *
     * @return The map of paths
     */
    public Map<Position, Position> getCheminsMap() {
        return chemins;
    }

    /**
     * Returns the list of all cells that form paths on the board.
     *
     * @return List of path cells
     */
    public List<Cell> getChemins() {
        List<Cell> result = new ArrayList<>();

        for (int i = 0; i < cmp; i++) {
            Position start = numChemins.get(i);
            Position end = chemins.get(start);
            if (start == null || end == null)
                continue;

            Direction dir = getDirection(start, end);

            switch (dir) {
                case RIGHT:
                    for (int c = start.getCol(); c <= end.getCol(); c++) {
                        result.add(getCell(start.getRow(), c));
                    }
                    break;

                case LEFT:
                    for (int c = start.getCol(); c >= end.getCol(); c--) {
                        result.add(getCell(start.getRow(), c));
                    }
                    break;

                case DOWN:
                    for (int r = start.getRow(); r <= end.getRow(); r++) {
                        result.add(getCell(r, start.getCol()));
                    }
                    break;

                case UP:
                    for (int r = start.getRow(); r >= end.getRow(); r--) {
                        result.add(getCell(r, start.getCol()));
                    }
                    break;
            }
        }

        return result;
    }


    public List<Position> getPathPositions() {
        if (this.allPath.isEmpty()) {
            return new ArrayList();
        } else {
            Random rand = new Random();
            List<Cell> path = new ArrayList<>(
                    this.allPath.get(rand.nextInt(this.allPath.size()))
            );

            ArrayList list = new ArrayList();

            for (Cell cell : path) {
                list.add(cell.getPosition());
            }

            return list;
        }
    }

    public boolean isCellAvailable(int row, int col) {
        if (!this.isInside(row, col)) {
            return false;
        } else {
            for (Tower var4 : this.towers) {
                if (var4.getPosition().getRow() == row && var4.getPosition().getCol() == col) {
                    return false;
                }
            }

            return true;
        }
    }


    public List<Cell> allCells () {
        return this.allCells;
    }
}