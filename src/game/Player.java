package game;
import object.tower.*;
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
     * Retourner le nombre de vie du joueur
     * @return nombre de vie
     */
    public int getVie() {
        return this.vie;
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
}
