package game;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


import game.action.*;
import object.tower.Tower;
import object.tower.evolution.TypeEvolution;
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
            System.out.println("4 : Downgrade une tour");
            System.out.println("5 : Commencer la manche");
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
                    Action upgrade = choisirUpgrade(game);
                    if (upgrade != null) {
                        actions.add(upgrade);
                    }
                    break;
                case 3:
                    Action vente = choisirVente(game);
                    if (vente != null) {
                        actions.add(vente);
                    }
                    break;
                case 4:
                    Action downgrade = choisirDowngrade(game);
                    if (downgrade != null) {
                        actions.add(downgrade);
                    }
                    break;
                case 5:
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
    public Action choisirAcheter(Game game) {
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

    /**
     * Methode pour afficher les tours et leurs id
     * @param game le game on jouait
     */
    public void afficheTour(Game game) {
        List<Tower> t = game.getTowers();
        System.out.println("Tour deja place :");
        for (int i = 0; i < t.size(); i++) {
            Tower tow = t.get(i);
            System.out.println((i + 1) + " : " + t.getClass().getName()+ " en " + tow.getPosition());
        }
    }

    /**
     * Methode pour faire un upgrade d'un tower
     * @param game le game on jouait
     * @return action upgrade
     */
    public Action choisirUpgrade(Game game) {
        List<Tower> t = game.getTowers();
        if (t.isEmpty()) {
            System.out.println("Pas de tour a upgrade");
            return null;
        }
        afficheTour(game);
        System.out.print("Choisi la tour a upgrade avec un index : ");
        int i = Integer.parseInt(scanner.nextLine().trim());
        if (i < 1 || i > t.size()) {
            System.out.println("Invalide");
            return null;
        }
        Tower tower = t.get(i - 1);
        if (tower.getEvolutions() == null|| tower.getEvolutions().getEvolutions().isEmpty()) {
            System.out.println("Cette tour n'a pas d'evolution disponible");
            return null;
        }
        ArrayList<TypeEvolution> evol = tower.getEvolutions().getEvolutions();
        System.out.println("Evolution dispo :");
        for (int j = 0; j < evol.size(); j++) {
            System.out.println((j + 1) + " : " + evol.get(j));
        }
        System.out.print("Choix de l'evolution : ");
        int choix = Integer.parseInt(scanner.nextLine().trim());
        if (choix < 1 || choix > evol.size()) {
            System.out.println("Choix invalide");
            return null;
        }
        TypeEvolution res = evol.get(choix - 1);
        return new Upgrade(tower, res);
    }

    /**
     * Methode pour gerer la vente d'un tower
     * @param game
     * @return action vendre
     */
    public Action choisirVente(Game game) {
        List<Tower> t = game.getTowers();
        if (t.isEmpty()) {
            System.out.println("Aucune tour");
            return null;
        }
        afficheTour(game);
        System.out.print("Choisir le tour a vendre : ");
        int i = Integer.parseInt(scanner.nextLine().trim());
        if (i < 1 || i > t.size()) {
            System.out.println("Invalide");
            return null;
        }
        return new Vendre(t.get(i - 1));
    }

    /**
     * Methode pour faire un downgrade d'un tower
     * @param game le jeu on jouait
     * @return action downgrade
     */
    public Action choisirDowngrade(Game game) {
        List<Tower> t = game.getTowers();
        if (t.isEmpty()) {
            System.out.println("Aucune tour a downgrade");
            return null;
        }
        afficheTour(game);
        System.out.print("Choisir un tour a downgrade : ");
        int i = Integer.parseInt(scanner.nextLine().trim());
        if (i < 1 || i > t.size()) {
            System.out.println("Invalide");
            return null;
        }
        Tower tower = t.get(i - 1);
        if (tower.getEvolutions() == null|| tower.getEvolutions().getEvolutionsPossedees()== null) {
            System.out.println("Cette tour n'a pas d'evolution");
            return null;
        }
        ArrayList<TypeEvolution> possedees = new ArrayList<>(tower.getEvolutions().getEvolutionsPossedees().keySet());
        System.out.println("Evolutions :");
        for (int j = 0; j < possedees.size(); j++) {
            System.out.println((j + 1) + " : " + possedees.get(j));
        }
        System.out.print("Choix de l'evolution a doowngrade : ");
        int n = Integer.parseInt(scanner.nextLine().trim());
        if (n < 1 || n > possedees.size()) {
            System.out.println("Invalide");
            return null;
        }
        TypeEvolution res = possedees.get(n - 1);
        return new Downgrade(tower, res);
    }
}   

