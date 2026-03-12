package Livrables;
import game.Game;
import game.InvalidTowerPlacementException;

public class MainTest {
    public static void main(String[] args) throws InvalidTowerPlacementException {
        if (args.length < 2) {
            System.out.println("Usage: java Livrables.Livrable3b <largeur> <hauteur>");
            return;
        }
        int width = Integer.parseInt(args[0]);
        int height = Integer.parseInt(args[1]);
        Game g = new Game();
        g.play(height, width);
    }
}
