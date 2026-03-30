package game;
import java.util.*;

import object.tower.*;


public abstract class Action {
    
    /**
     * Attributs
     * un int pour gerer le cout total d'une action
     */
    private int c;
    private Tower t;
    /**
     * Constructeur de la classes
     */
    public Action(Tower t) {
        this.c = 0;
        this.t = t;
    }

    /**
     * Methode rendre le nombre de credit de l'action
     * @return le nombre de credit
     */
    public int getCout() {
        return this.c;
    }

    /**
     * Methode pour acheter une tour
     * Verifie si le joueur a assez de credit pour acheter la tour
     * Si oui, le credit du joueur va diminuer du cout de la tour
     * @param t la tour a acheter
     * @return true si l'achat est reussi, false sinon
     */
    public int calculInvestissementTotal(Tower t) {
        int total = t.getCout();
        if (t.getEvolutions() != null) {
            for (int c : t.getEvolutions().getEvolutionsPossedees().values()) {
                total += c;
            }
        }
        return total;
    }

    /**Methode abstract gerer l'action */
    public abstract void doAction();
}
