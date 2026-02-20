package Livrables;

import game.*;

public class Livrable3b {

    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("Usage: java Livrables.Livrable3b <largeur> <hauteur> <nbChemins>");
            return;
        }
        int width = Integer.parseInt(args[0]);
        int height = Integer.parseInt(args[1]);
        int nbChemins = Integer.parseInt(args[2]);

        
        Game game = new Game();
        game.initFreeBoard(height, width, nbChemins);
        game.spawnMultipleBalloon(nbChemins);
        game.addTower();
        game.runGame();
    }
}