package board;

import object.balloon.BalloonStraight;
import object.balloon.BalloonType;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Livrable 2b - Simulation de ballons rectilignes sur un FreeBoard.
 */
public class Livrable2b {

    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("Arguments manquants : <hauteur> <largeur> <nbChemins>");
            return;
        }

        int height = Integer.parseInt(args[0]);
        int width = Integer.parseInt(args[1]);
        int nbTarget = Integer.parseInt(args[2]);

        // 1. Initialisation du plateau
        FreeBoard board = new FreeBoard(height, width);
        board.init();
        board.setNbCheminsVoulus(nbTarget);
        board.calculerPath();

        // 2. Affichage du plateau (Logique identique au Livrable1b)
        System.out.println("Plateau " + height + "x" + width);
        afficherPlateau(board);
        System.out.println();

        // 3. Création des ballons (un par chemin)
        Random random = new Random();
        List<BalloonStraight> balloons = new ArrayList<>();
        int actualPaths = board.retourneNbrChemins();

        int[] ticParCellList = {1,2,3};

        for (int i = 0; i < actualPaths; i++) {
            Position[] chemin = board.retourneLesChemins(i);
            Position start = chemin[0];
            Position end = chemin[1];

            // Vitesse aléatoire entre 0.5 et 3.0
            int ticParCell = ticParCellList[random.nextInt(ticParCellList.length)];
            double speed = 1.0 / ticParCell;

            // Type aléatoire parmi les 3 types disponibles
            BalloonType type = BalloonType.values()[random.nextInt(3)];

            BalloonStraight b = new BalloonStraight(i, start, end, speed, type);
            balloons.add(b);

            System.out.print("Bloon " + i + " at " + start + " speed: ");
            System.out.printf("%.1f", speed);
            System.out.println(" health: " + b.getHealth());
        }
        System.out.println("Spawned " + actualPaths + " bloons.\n");

        // 4. Simulation du mouvement
        int maxTick = 5000;
        int currentTick = 0;
        int printTic = 1;

        //Initialiser les ballons et les placers dans board
        placeActiveBalloonsOnBoard(board, balloons);
        System.out.println("TIC = 0");
        afficherPlateau(board);
        System.out.println();

        while (hasActiveBalloons(balloons) && currentTick < maxTick) {
            currentTick++;

            for (BalloonStraight b : balloons) {
                if ( !b.isActive()) {
                    continue;
                }
                else if (b.isActive()) {
                    Position before = b.getCurrentPosition();
                    b.move(); // Déplace le ballon
                    Position after = b.getCurrentPosition();

                    //On ne affiche ce message que quand un ballon change son cell, sinon il n'affiche pas
                    if (!after.equals(before)) {
                    System.out.println("TIC " + currentTick
                            + " : Bloon " + b.getId()
                            + " change de case " + before + " -> " + after);
                    }

                    // Si le ballon vient d'arriver (active devient false dans move)
                    if (!b.isActive() && b.getExitTime() == -1) {
                        System.out.print("Bloon " + b.getId() + " (speed: ");
                        System.out.printf("%.1f", b.getSpeed());
                        System.out.println(") exits at time: " + currentTick);
                    }
                }
                placeActiveBalloonsOnBoard(board, balloons);

                if (currentTick % printTic == 0) {
                    System.out.println("TIC = " + currentTick);
                    afficherPlateau(board);
                    System.out.println();
                }
            }
        }
    }

    /**
     * Affiche le plateau avec D, F, C ou . selon la nature de la cellule.
     */
    private static void afficherPlateau(FreeBoard board) {
        Cell[][] grid = board.getGrid();
        int nbAfficher = board.retourneNbrChemins();

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                Cell cell = grid[i][j];
                boolean estDebut = false;
                boolean estFin = false;

                // On vérifie si la coordonnée (i,j) est un début ou une fin de chemin
                for (int num = 0; num < nbAfficher; num++) {
                    Position[] chemin = board.retourneLesChemins(num);
                    if (i == chemin[0].getRow() && j == chemin[0].getCol()) {
                        estDebut = true;
                        break;
                    }
                    if (i == chemin[1].getRow() && j == chemin[1].getCol()) {
                        estFin = true;
                        break;
                    }
                }

                if (estDebut) System.out.print("D ");
                else if (estFin) System.out.print("F ");
                else if (cell.isPath()) System.out.print("c ");
                else System.out.print(". ");
            }
            System.out.println();
        }
    }

    /**
     * Vérifie si au moins un ballon est encore sur le plateau.
     */
    private static boolean hasActiveBalloons(List<BalloonStraight> balloons) {
        for (BalloonStraight b : balloons) {
            if (b.isActive()) return true; //
        }
        return false;
    }

    /**
     * Place tous les ballons actives sur le board
     */
    public static void placeActiveBalloonsOnBoard(FreeBoard board, List<BalloonStraight> balloons) {
        for (BalloonStraight b : balloons) {
            if (!b.isActive()) {
                 continue;
            }
            Position p = b.getCurrentPosition();
            if (board.isInside(p.getRow(), p.getCol())) {
                board.getCell(p.getRow(), p.getCol()).addBalloon(b);
            }
        }
    }
}