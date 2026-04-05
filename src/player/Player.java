package player;

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
  
    
        /**
        * Constructeur de la classe
        * @param actionChooser le type de list chooser pour choisir les actions du joueur
        */
    public Player(ListChooser actionChooser) {
    this.credit = CREDIT_INITIAL;
    this.vie = VIE_INIT;
    this.actionChooser = actionChooser;
    this.towers = new ArrayList<>();
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

    /**
     * Methode pour ajouter une tour a la liste de tour du joueur
     * @param tower la tour a ajouter
     */
    public void addTower(Tower tower) {
        this.towers.add(tower);
    }

    /**
     * Methode pour retourner la liste de tour du joueur
     * @return la liste de tour du joueur
     */
    public List<Tower> getTowers() {
        return this.towers;
    }

    /**
     * Methode pour choisir les actions du joueur
     * @param game le game qu'on jouait
     * @return les actions que le joueur a fait
     */
    public List<Action> choisirActions(Game game) {
        return actionChooser.choisirActions(game, this);
    }
}
    