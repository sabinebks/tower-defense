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
     * Methode pour gerer les actions du joueur
     * 1 pour acheter un tower + placer le
     * 2 pour faire des upgrades d'un tower choisi
     * 3 pour vendre un tower
     * 4 pour commencer le jeu
     * @param choix le choix de l'action du joueur (1 à 4)
     */
    public void actionJoueur(int choix) {
        boolean fin = false;
        while (!fin) {
            switch (choix) {
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    fin = true;
                    break;
                default:
                    System.out.println("Choix invalide");
            }
        }
    }
}
