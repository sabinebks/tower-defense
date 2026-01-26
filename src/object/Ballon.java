package object;
/**
 * Une classe des ballons, caracterise par leur couleur
 */
public class Ballon {
    /**
     * Les attributs
     * La resistance ( health )
     * La vitesse
     * La couleur
     */
    private int res;
    private int vit;
    private BallonColor col;

    /**
     * Le constructeur
     * La resistance et la vitesse va etre cree selon la couleur
     * choisie par le computer
     * @param col la couleur du ballon
     */
    public Ballon ( BallonColor col) {
        this.col = col;
        if (this.col == BallonColor.RED) {
            this.res = 0;
            this.vit = 0;
        }
        else if ( this.col == BallonColor.BLUE) {
            this.res = 0;
            this.vit = 0;
        }
        else if ( this.col == BallonColor.YELLOW) {
            this.res = 0;
            this.vit = 0;
        }
    }

    /**
     * Fonction pour prendre la couleur du ballon
     * @return couleur du ballon
     */
    public BallonColor getCol() {
        return this.col;
    }

    /**
     * Fonction pour prendre la resistance du ballon
     * @return resistance du ballon 
     */
    public int getRes() {
        return this.res;
    }

    /**
     * Fonction pour prendre la vitesse du ballon
     * @return vitesse du ballon
     */
    public int getVit() {
        return this.vit;
    }
}
