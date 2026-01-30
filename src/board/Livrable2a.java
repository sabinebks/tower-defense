package board;

import object.balloon.BalloonPath;
import object.balloon.BalloonType;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Livrable 2a - Simulation avec DefinedBoard.
 */
public class Livrable2a {
    
    public static void main(String[] args) {
        if (args.length != 3) {
            System.err.println("Usage: java board.Livrable2a <hauteur> <largeur> <nombre_ballons>");
            System.exit(1);
        }

        int height = Integer.parseInt(args[0]);
        int width = Integer.parseInt(args[1]);
        int numBalloons = Integer.parseInt(args[2]);

        // Créer le plateau avec chemin aléatoire
        DefinedBoard board = new DefinedBoard(height, width);
        List<Cell> path = board.getChemins();

        // Afficher le plateau
        System.out.println("Plateau " + height + "x" + width);
        afficherPlateau(board);
        System.out.println();

        // Afficher le chemin
        System.out.println("Chemin :");
        for (int i = 0; i < path.size(); i++) {
            System.out.print(path.get(i).getPosition());
            if (i < path.size() - 1) {
                System.out.print(" -> ");
            }
        }
        System.out.println();
        System.out.println();

        // Créer les ballons
        Random random = new Random();
        List<BalloonPath> balloons = new ArrayList<>();
        
        for (int i = 0; i < numBalloons; i++) {
            double speed = 0.5 + random.nextDouble() * 2.5;
            BalloonType type = BalloonType.values()[random.nextInt(3)];
            BalloonPath balloon = new BalloonPath(i, path.get(0), speed, type, path);
            balloons.add(balloon);
            
            System.out.println(String.format("Bloon %d at %s speed: %.1f health: %d", 
                i, path.get(0).getPosition(), speed, balloon.getHealth()));
        }
        System.out.println("Spawned " + numBalloons + " bloons.");
        System.out.println();

        // Simulation
        int currentTick = 0;
        int maxTicks = 10000;
        
        while (hasActiveBalloons(balloons) && currentTick < maxTicks) {
            currentTick++;
            
            for (BalloonPath b : balloons) {
                if (b.isActive()) {
                    b.move();
                    
                    if (!b.isActive() && b.getExitTime() == -1) {
                        b.setExitTime(currentTick);
                        System.out.println("Bloon " + b.getId() + 
                            " (speed: " + String.format("%.1f", b.getSpeed()) + 
                            ") exits at time: " + currentTick);
                    }
                }
            }
        }
    }
    
    /**
     * Affiche le plateau : D = début, F = fin, c = chemin, . = vide
     */
    private static void afficherPlateau(DefinedBoard board) {
        List<Cell> path = board.getChemins();
        
        for (int r = 0; r < board.getHeight(); r++) {
            for (int c = 0; c < board.getWidth(); c++) {
                Cell cell = board.getCell(r, c);
                if (cell.isPath()) {
                    int index = path.indexOf(cell);
                    if (index == 0) {
                        System.out.print("D ");  // Départ
                    } else if (index == path.size() - 1) {
                        System.out.print("F ");  // Fin
                    } else {
                        System.out.print("c ");  // Chemin
                    }
                } else {
                    System.out.print(". ");
                }
            }
            System.out.println();
        }
    }
    
    private static boolean hasActiveBalloons(List<BalloonPath> balloons) {
        for (BalloonPath b : balloons) {
            if (b.isActive()) {
                return true;
            }
        }
        return false;
    }
}