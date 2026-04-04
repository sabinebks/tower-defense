package game;

import java.util.ArrayList;
import java.util.List;

import object.tower.Tower;


/**
 * Une class player pour gerer les actions du joueurs
 */
public class Player {
    public static final int CREDIT_INITIAL = 2500;
    public static final int VIE_INIT = 20;
    public static final double REVENDRE = 0.25;
    private ListChooser actionChooser;
    private int vie;
    private int credit;
    private List<Tower> towers;
  
    
    public Player(int credit, ListChooser actionChooser) {
        this.credit = credit;
        this.towers = new ArrayList<>();
        this.actionChooser = actionChooser;
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
    