package object;

import board.Position;

/**
 * Une classe des ballons, caracterise par leur couleur
 */
public class Ballon {
    /**
     * Les attributs
     * La resistance lev
     * La vitesse vit
     * l'id du ballon idBal
     */

    private int vit;
    private LevelBalloon lev;
    private boolean isFrozen;
    private boolean isSlowed;
    private int nbTirs;
    //pour connaitre la position du ballon  
    private Position position;
    //pour compter les ticks d'horloge
    private int tickCounter;

    /**
     * Le constructeur
     * La resistance et la vitesse va etre cree selon le niveau
     * choisie par le computer
     * 
     * @param lev la couleur du ballon
     */
    public Ballon(LevelBalloon lev, Position position) {
        this.lev = lev;
        this.position = position;
        this.nbTirs=0;
        this.isSlowed=false;
        this.isFrozen=false;
        this.tickCounter=0;
        switch (lev) {
            case L1:
                /* va avance une case a chque tick d'horloge */
                this.vit = 1;
                break;
            case L2:
                /* va avance une cases a chque 2 tick d'horloge */
                this.vit = 2;
                break;

            /* va avance une cases a chque 3 tick d'horloge */
            default:
                this.vit = 3;
                break;
        }
    }
    


    /**
     * Fonction pour rendre le level de resistance
     * 
     * @return couleur du ballon
     */
    public LevelBalloon getLev() {
        return this.lev;
    }

    /**
     * Fonction pour prendre la vitesse du ballon
     * 
     * @return vitesse du ballon
     */
    public int getVit() {
        return this.vit;
    }

    /* fonction qui retourne si le ballon est freezed ou pas */
    public boolean isFrozen() {
        return isFrozen;
    }

    /* fonction qui retourne si le ballon est ralenti ou pas */
    public boolean isSlowed() {
        return isSlowed;
    }

    /* fonction qui retourne le nombre de tirs sur le ballon */
    public int getNbTirs() {
        return nbTirs;
    }

    /*
     * procedure pour incrementer le nombre de tirs sur le ballon
     ** 
     * @return le nombre detirs restant pour popper le ballon
     ** si le ballon est poppee on retourne 0
     */
    public int tirer() {
        nbTirs++;
        int res = 0;
        switch (this.lev) {
            case L1:
                res = 0;
                break;
            case L2:
                res = 1;
                break;
            case L3:
                res = 2;
                break;
        }
        return res;
    }

}
