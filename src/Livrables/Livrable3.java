package Livrables;

import board.*;
import board.typeOfBoard.*;
import object.balloon.*;
import object.tower.*;
import object.tower.projectil.*;
import game.*;

import java.util.Scanner;

public class Livrable3 {
    public static void main(String[] args) {
        // nitialisation du plateau (juste un exemple 10*10)
        DefinedBoard board = new DefinedBoard(10, 10);

        // initialisation du jeu
        Game game = new Game(board);

        // scanner pour lire tes entrées
        Scanner scanner = new Scanner(System.in);

        //le premier affichage qui va aider le joueur a jouer
        System.out.println("=== BIENVENUE DANS TOWER DEFENSE DEBUG MODE ===");
        System.out.println("Commandes disponibles :");
        System.out.println(" - [Entree vide] : Avancer d'un tic (50ms)");
        System.out.println(" - 'balloon'     : Faire apparaitre un ballon au départ");
        System.out.println(" - 'tower R C'   : Placer une BombTower à la ligne R et colonne C");
        System.out.println(" - 'quit'        : Quitter le jeu");
        System.out.println("================================================");

        //la boucle principale qui gere tout le jeu
        while (true) {
            System.out.print("\nCommande > ");
            //trim() supprime tout les espace inutile au debut et a la fin
            String input = scanner.nextLine().trim();

            //commande pour quitter le jeu
            if (input.equalsIgnoreCase("quit")) {
                System.out.println("Fin de la session de test.");
                break;
            }

            //commande = la touche entrer sans rien saisir
            if (input.isEmpty()) {
                // avancer le jeu d'un pas
                game.gameTick();
            } //commande pour ajouter un balloon
            else if (input.equalsIgnoreCase("balloon")) {
                game.spawnBalloon(BalloonType.BASIC, 0.3);
                System.out.println("->Ballon ajouté !! Appuyez sur Entrée pour le voir bouger.");
            } // commande pour ajouter une tower
            else if (input.startsWith("tower")) {
                try {
                    String[] parts = input.split(" ");
                    int r = Integer.parseInt(parts[1]);
                    int c = Integer.parseInt(parts[2]);

                    game.addTower(new BombTower(new Position(r, c)));
                    System.out.println("->Tour placée en [" + r + "," + c + "]");
                } catch (Exception e) {
                    System.out.println("->Erreur format : tower [ligne] [colonne]");
                }
            }
            else {
                System.out.println("->Commande inconnue");
            }
        }

        scanner.close();
    }

}