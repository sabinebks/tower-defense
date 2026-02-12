package board;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

import object.Horloge;
import object.balloon.*;
import board.typeOfBoard.*;

public class Game {
    private Horloge horloge;
    private int mode;
    
    
    public Game(int mode) {
        this.mode = mode;
        this.horloge = new Horloge();
    }

    /**
     * Placer tous les ballons actifs (BalloonPath) sur le plateau
     * @param board le board du jeu
     * @param ballons la liste des ballons
     */
    public static void placeActiveBalloonsOnBoardPath(Board board, List<BalloonPath> balloons) {
        for (BalloonPath b : balloons) {
            if (!b.isActive()) continue;
            Position p = b.getCurrentPosition();
            if (board.isInside(p.getRow(), p.getCol())) {
                board.getCell(p.getRow(), p.getCol()).addBalloon(b);
            }
        }
    }

    /**
     * Placer tous les ballons actifs (BalloonStraight) sur le plateau
     * @param board le board du jeu
     * @param balloons la liste des ballons
     */
    public static void placeActiveBalloonsOnBoardStraight(Board board, List<BalloonStraight> balloons) {
        for (BalloonStraight b : balloons) {
            if (!b.isActive()) continue;
            Position p = b.getCurrentPosition();
            if (board.isInside(p.getRow(), p.getCol())) {
                board.getCell(p.getRow(), p.getCol()).addBalloon(b);
            }
        }
    }

    /**
     * Afficher le plateau défini (mode chemin unique)
     * @param board le board du jeu
     */
    public static void afficherDefinedPlateau(DefinedBoard board) {
        List<Cell> path = board.getChemins();
        for (int r = 0; r < board.getHeight(); r++) {
            for (int c = 0; c < board.getWidth(); c++) {
                Cell cell = board.getCell(r, c);
                if (cell.isPath()) {
                    int index = path.indexOf(cell);
                    if (index == 0) System.out.print("D | ");
                    else if (index == path.size() - 1) System.out.print("F | ");
                    else System.out.print("c | ");
                } else {
                    System.out.print(". | ");
                }
            }
            System.out.println();
        }
    }

    /**
     * Afficher le plateau libre (mode multi-chemins)
     * @param board le board du jeu
     */
    public static void afficherFreePlateau(FreeBoard board) {
        Cell[][] grid = board.getGrid();
        int nbAfficher = board.retourneNbrChemins();

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                Cell cell = grid[i][j];
                boolean estDebut = false, estFin = false;

                for (int num = 0; num < nbAfficher; num++) {
                    Position[] chemin = board.retourneLesChemins(num);
                    if (i == chemin[0].getRow() && j == chemin[0].getCol()) estDebut = true;
                    if (i == chemin[1].getRow() && j == chemin[1].getCol()) estFin = true;
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
     * Vérifie si au moins un BalloonPath est actif
     * @param balloons la liste des ballons a verifie
     */
    public static boolean hasActiveBalloonsPath(List<BalloonPath> balloons) {
        for (BalloonPath b : balloons) if (b.isActive()) return true;
        return false;
    }

    /**
     * Vérifie si au moins un BalloonStraight est actif
     * @param balloons la liste des ballons a verifier
     */
    public static boolean hasActiveBalloonsStraight(List<BalloonStraight> balloons) {
        for (BalloonStraight b : balloons) if (b.isActive()) return true;
        return false;
    }

    /**
     * Lancer une partie
     * @param h la hauteur du board
     * @param w la largeur du board
     * @param b le nombre des ballons
     */
    public void play(int h, int w, int b) {
        int height = h, width = w, numBalloons = b;

        if (this.mode == 1) {
            // Mode plateau défini
            DefinedBoard board = new DefinedBoard(height, width);
            List<Cell> path = board.getChemins();

            System.out.println("Plateau " + height + "x" + width);
            afficherDefinedPlateau(board);
            System.out.println();

            // Création des ballons
            Random random = new Random();
            List<BalloonPath> balloons = new ArrayList<>();
            int[] ticParCellList = {1, 2, 3};

         for (int i = 0; i < numBalloons; i++) {
    int ticParCell = ticParCellList[random.nextInt(ticParCellList.length)];
    double speed = 1.0 / ticParCell;

    BalloonType type = BalloonType.values()[random.nextInt(3)];
    BalloonPath balloon = new BalloonPath(i, path.get(0), speed, type, path);
    balloons.add(balloon);
   System.out.println(
    "baloon " + i +
    " at " + path.get(0).getPosition() +
    " speed: " + speed +
    " health: " + balloon.getHealth()
);

   
}

            System.out.println("Spawned " + numBalloons + " bloons.\n");

            // Simulation
            int currentTick = 0, maxTicks = 10000, printTic = 1;
            placeActiveBalloonsOnBoardPath(board, balloons);
            System.out.println("TIC = 0");
            afficherDefinedPlateau(board);
            System.out.println();

            while (hasActiveBalloonsPath(balloons) && currentTick < maxTicks) {
                horloge.addTime();
                for (BalloonPath bPath : balloons) {
                    if (!bPath.isActive()) continue;
                    Position before = bPath.getCurrentPosition();
                    bPath.move();
                    Position after = bPath.getCurrentPosition();
                    if (!after.equals(before)) {
                        System.out.println("TIC " + horloge.getTime() + " : Bloon " + bPath.getId()
                                + " change de case " + before + " -> " + after);
                    }
                    if (!bPath.isActive() && bPath.getExitTime() == -1) {
                        bPath.setExitTime(horloge.getTime());
                        System.out.println("Bloon " + bPath.getId() + " exits at time: " + horloge.getTime());
                    }
                }
                placeActiveBalloonsOnBoardPath(board, balloons);
                if (horloge.getTime() % printTic == 0) {
                    System.out.println("TIC = " + horloge.getTime());
                    afficherDefinedPlateau(board);
                    System.out.println();
                }
            }
        } else if (this.mode == 2) {
            // Mode plateau libre
            FreeBoard board = new FreeBoard(height, width);
            board.init();
            board.setNbCheminsVoulus(numBalloons);
            board.calculerPath();

            System.out.println("Plateau " + height + "x" + width);
            afficherFreePlateau(board);
            System.out.println();

            Random random = new Random();
            List<BalloonStraight> balloons = new ArrayList<>();
            int actualPaths = board.retourneNbrChemins();
            int[] ticParCellList = {1, 2, 3};

            for (int i = 0; i < actualPaths; i++) {
                Position[] chemin = board.retourneLesChemins(i);
                Position start = chemin[0], end = chemin[1];

                int ticParCell = ticParCellList[random.nextInt(ticParCellList.length)];
                double speed = 1.0 / ticParCell;
                BalloonType type = BalloonType.values()[random.nextInt(3)];

                BalloonStraight bS = new BalloonStraight(i, start, end, speed, type);
                balloons.add(bS);

                System.out.print("Bloon " + i + " at " + start + " speed: ");
                System.out.printf("%.1f", speed);
                System.out.println(" health: " + bS.getHealth());
            }
            System.out.println("Spawned " + actualPaths + " bloons.\n");

            int maxTick = 5000, currentTick = 0, printTic = 1;
            placeActiveBalloonsOnBoardStraight(board, balloons);
            System.out.println("TIC = 0");
            afficherFreePlateau(board);
            System.out.println();

            while (hasActiveBalloonsStraight(balloons) && currentTick < maxTick) {
                currentTick++;
                horloge.addTime();
                for (BalloonStraight bPath : balloons) {
                    if (!bPath.isActive()) continue;
                    Position before = bPath.getCurrentPosition();
                    bPath.move();
                    Position after = bPath.getCurrentPosition();
                    if (!after.equals(before)) {
                        System.out.println("TIC " + currentTick + " : Bloon " + bPath.getId()
                                + " change de case " + before + " -> " + after);
                    }
                    if (!bPath.isActive() && bPath.getExitTime() == -1) {
                        bPath.setExitTime(currentTick);
                        System.out.println("Bloon " + bPath.getId() + " exits at time: " + currentTick);
                    }
                }
                placeActiveBalloonsOnBoardStraight(board, balloons);
                if (currentTick % printTic == 0) {
                    System.out.println("TIC = " + currentTick);
                    afficherFreePlateau(board);
                    System.out.println();
                }
            }
        }
    }
}
