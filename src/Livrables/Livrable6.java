package Livrables;

import game.*;
import player.Shop;

public class Livrable6 {

    public static void main(String[] args) {

        if (args.length < 5) {
            System.out.println("Usage: largeur hauteur mode plateau");
            System.out.println("mode : interactive | random");
            System.out.println("plateau : a | b");
            return;
        }

        int width = Integer.parseInt(args[0]);
        int height = Integer.parseInt(args[1]);
        int nbBallon = Integer.parseInt(args[2]);
        boolean isAuto = args[3].equalsIgnoreCase("random");
        boolean isPlateauB = args[4].equalsIgnoreCase("b");

        Game game = GameFactory.build(height, width, nbBallon,isPlateauB, isAuto);

        GameLoop loop = new GameLoop(
                game,
                game.getPlayer(),
                new Shop(game.getBoard())
        );

        loop.run();
    }
}