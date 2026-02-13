package game;

import board.*;
import board.typeOfBoard.DefinedBoard;
import object.balloon.Balloon;

import object.balloon.BalloonType;
import object.tower.BombTower;
import object.tower.Tower;
import object.tower.EffectType;
import object.tower.projectil.*;

import java.util.Scanner;

// On crée une petite classe concrète pour la tour car Tower est abstraite
class TestTower extends Tower {
    public TestTower(Position p) {
        // Position, Coût (100), Portée (3), Cadence (1000ms), Evolution (false), Effet (AREA), Projectile (BOMBE)
        super(p, 100, 3, 1000, false, EffectType.AREA, ProjectilType.BOMBE);
    }
}

public class Main {
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
        System.out.println(" - [Entrée vide] : Avancer d'un tic (50ms)");
        System.out.println(" - 'balloon'     : Faire apparaître un ballon au départ");
        System.out.println(" - 'tower R C'   : Placer une BombTower à la ligne R et colonne C");
        System.out.println(" - 'quit'        : Quitter le jeu");
        System.out.println("================================================");


}