package Livrables;

import game.*;

public class Livrable3a {

    public static void main(String[] args) {
        
        if (args.length < 3) {
            System.out.println("Usage: java Livrables.Livrable3a <largeur> <hauteur> <nbBallons>");
            return;
        }

        int width = Integer.parseInt(args[0]);
        int height = Integer.parseInt(args[1]);
        int nbBallons = Integer.parseInt(args[2]);

    
        Game game = new Game();
        game.initDefinedBoard(height, width);
        game.spawnBalloon(nbBallons);
        game.addTower();
        game.play();
    }
}