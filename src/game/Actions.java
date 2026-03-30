package game;
import java.util.*;

import object.tower.*;


public abstract class Actions {
    

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
}
