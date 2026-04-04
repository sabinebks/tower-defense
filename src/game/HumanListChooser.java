package game;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import game.action.Acheter;
import object.tower.Tower;
import object.tower.*;
import board.*;

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
                    Action achat = choisirAcheter(game);
                    if (achat != null) {
                        actions.add(achat);
                    }
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
    /**
     * Methode pour acheter un tower
     * @param game le game qu'on jouait
     * @return l'action d'achat 
     */
    private Action choisirAcheter(Game game) {
        System.out.println("Choisir le type de tour :");
        System.out.println("1 : BombTower");
        System.out.println("2 : DartMonkey");
        System.out.println("3 : EliteSniper");
        System.out.println("4 : Gorilla");
        System.out.println("5 : IceTower");
        System.out.println("6 : SlowTower");
        System.out.println("7 : TackShooter");
        System.out.print("Type : ");
        int type = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Ligne : ");
        int row = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Colonne : ");
        int col = Integer.parseInt(scanner.nextLine().trim());
        Position p = new Position(row, col);
        if (!game.posValide(p)) {
            System.out.println("Position invalide");
            return null;
        }
        Tower t = game.createTower(type, p);
        if (t == null) {
            System.out.println("Type de tour invalide");
            return null;
        }
        return new Acheter(t, p);
    }
}   

