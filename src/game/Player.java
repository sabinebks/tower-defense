package game;
/**
 * Une class player pour gerer les actions du joueurs
 */
public class Player {
    /**
     * Les attributs du joueurs
     * Le nombre de cerdit, init en 2500
     * Le nombre de vie, init en 20
     * Le joueurs va gerer avec les towers dans game, donc il a pas besoin d'un liste de towers
     */
    public static final int CREDIT_INITIAL = 2500;
    public static final int VIE_INIT = 20;
    public static final double REVENDRE = 0.25;
    private int credit;
    private int vie;
    /** Le constructeur du joueur, init le credit et la vie */
    public Player () {
        this.credit = CREDIT_INITIAL;
        this.vie = VIE_INIT;
    }

    /**
     * Retourner le nombre de credit du joueur
     * @return nombre de credit
     */
    public int getCredit() {
        return this.credit;
    }

    /**
     * Methode gerer le nombre de credit du joueur
     * @param n le nombre de credit a ajouter
     */
    public void addCredit(int n) {
        this.credit += n;
    }
    /**
     * Retourner le nombre de vie du joueur
     * @return nombre de vie
     */
    public int getVie() {
        return this.vie;
    }
    
    /**
     * Methode pour gerer la perdu de vie du joueur
     */
    public void perdreVie() {
        this.vie -= 1;
        if (this.vie < 0) {
            this.vie = 0;
        }
    }

    /**
     * Methode pour verifier que le joueur n'a pas perdu
     * @return true si joueur est vivant, false sinon
     */
    public boolean estVivant() {
        return this.vie > 0;
    }
        /**
        * Methode pour gerer la depense de credit du joueur
        * @param n le nombre de credit a depenser
        * @return true si le joueur a assez de credit, false sinon
        */
    public boolean removeCredit(int n) {
    if (credit >= n) {
        credit -= n;
        return true;
    }
    return false;
}
}
    