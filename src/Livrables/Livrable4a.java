package Livrables;
import game.Game;

public class Livrable4a {
    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("Usage: java Livrables.Livrable3b <largeur> <hauteur> <nb de ballons>");
            return;
        }
        int width = Integer.parseInt(args[0]);
        int height = Integer.parseInt(args[1]);
        Game g = new Game();
        
    }
}
