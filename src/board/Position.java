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
    private int x;
    private int y;

    /**
     * Le constructeur de la classe
     * @param x coor x 
     * @param y coor y
     */
    public Position ( int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Fonction pour get x
     * @return x
     */
    public int getX() {
        return this.x;
    }

    /**
     * Fonction pour get y
     * @return y
     */
    public int getY() {
        return this.y;
    }

}
