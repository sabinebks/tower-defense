package Livrables;

import game.*;
import player.Shop;
/**
 * Main game
 */
public class Livrable6 {
    /**
     * Main function of the whole game
     * @param args input from terminal
     */
    public static void main(String[] args) {

        if (args.length < 4) {
            System.out.println("Usage plateau a : largeur hauteur mode plateau");
            System.out.println("Usage plateau b : largeur hauteur nbChemins mode plateau");
            System.out.println("mode : interactive | random");
            System.out.println("plateau : a | b");
            return;
        }

        int width;
        int height;
        int nbChemins = 1;
        boolean isAuto;
        boolean isPlateauB;

        try {
            width = Integer.parseInt(args[0]);
            height = Integer.parseInt(args[1]);

            if (args.length == 5) {
                nbChemins = Integer.parseInt(args[2]);
                isAuto = args[3].equalsIgnoreCase("random");
                isPlateauB = args[4].equalsIgnoreCase("b");
            } else {
                isAuto = args[2].equalsIgnoreCase("random");
                isPlateauB = args[3].equalsIgnoreCase("b");
            }
        } catch (NumberFormatException e) {
            System.out.println("Erreur: largeur/hauteur/nbChemins doivent etre des nombres.");
            System.out.println("mode : interactive | random");
            System.out.println("plateau : a | b");
            return;
        }

        if (isPlateauB && args.length != 5) {
            System.out.println("Erreur: pour le plateau b, donne aussi nbChemins.");
            System.out.println("Exemple: 11 5 6 interactive b");
            return;
        }

        Game game = GameFactory.build(height, width, nbChemins, isPlateauB, isAuto);

        GameLoop loop = new GameLoop(
                game,
                game.getPlayer(),
                new Shop(game.getBoard())
        );

        loop.run();
    }
}
