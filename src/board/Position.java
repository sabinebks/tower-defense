package board;

/**
 * La classe Position pour gerer les positions des ballons et tours
 */
public class Position {
    /**
     * Les attributs
     * La coordonation x
     * La coordonation y
     */
    private int col;
    private int row;

    /**
     * Le constructeur de la classe
     * @param col coor x 
     * @param row coor y
     */
    public Position ( int row, int col) {
        this.col = col;
        this.row = row;
    }

    /**
     * Fonction pour get x
     * @return x
     */
    public int getColonne() { // La colonne
        return this.col;
    }

    /**
     * Fonction pour get y
     * @return y
     */
    public int getRow() { // La ligne
        return this.row;
    }

}
