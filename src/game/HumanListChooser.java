package game;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * La classe gere les actions avec le joueur les gerent manuellement
 */
public class HumanListChooser implements ListChooser {
    /**
     * Les attribut de la classe
     * Un scanner pour lire les input du terminal
     */
    private Scanner scanner;

    /**
     * Constructeur de la classe
     */
    public HumanListChooser() {
        this.scanner = new Scanner(System.in);
    }
    /**
     * Methode pour choisir les actions d'un joueur
     * @param game le game qu'on jouait
     * @param player le joeur
     * @return les actions que le joueur a fait
     */
    public List<Action> choisirActions(Game game, Player player) {
        List<Action> actions = new ArrayList<>();
        boolean fin = false;
        while (!fin && player.getCredit() > 0) {
            System.out.println("=== ACTIONS JOUEUR ===");
            System.out.println("Credits : " + player.getCredit() + " | Vies : " + player.getVie());
            System.out.println("1 : Acheter une tour");
            System.out.println("2 : Ameliorer une tour");
            System.out.println("3 : Vendre une tour");
            System.out.println("4 : Commencer la manche");
            System.out.print("Choix : ");
            int choix = Integer.parseInt(scanner.nextLine().trim());

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
        return actions;
    }
}   

