package board;

import java.util.List;
import object.balloon.*;
import java.util.ArrayList;
import java.util.Random;


public class Game {

    private int mode;
    public Game (int mode) {
        this.mode = mode;
    }
    /**
     * Placer tout les ballons actives dans leur cells
     * @param board le board du jeu
     * @param balloons les ballons a placer
     */
    public static void placeActiveBalloonsOnBoard(Board board, List<BalloonPath> balloons) {
        for (BalloonPath b : balloons) {
            if (!b.isActive()) {
                continue;
            }
            Position p = b.getCurrentPosition();
            if (board.isInside(p.getRow(), p.getCol())) {
                board.getCell(p.getRow(), p.getCol()).addBalloon(b);
            }
        }
    }
    
    /**
     * Affiche le plateau : D = début, F = fin, c = chemin, . = vide
     * @param board le board a afficher
     */
    public static void afficherDefinedPlateau(DefinedBoard board) {
        List<Cell> path = board.getChemins();
        
        for (int r = 0; r < board.getHeight(); r++) {
            for (int c = 0; c < board.getWidth(); c++) {
                Cell cell = board.getCell(r, c);
                if (cell.isPath()) {
                    int index = path.indexOf(cell);
                    if (index == 0) {
                        System.out.print("D | ");  // Départ
                    } else if (index == path.size() - 1) {
                        System.out.print("F | ");  // Fin
                    } else {
                        System.out.print("c | ");  // Chemin
                    }
                } else {
                    System.out.print(". | ");
                }
            }
            System.out.println();
        }
    }
    
    public static boolean hasActiveBalloons(List<BalloonPath> balloons) {
        for (BalloonPath b : balloons) {
            if (b.isActive()) {
                return true;
            }
        }
        return false;
    }

/**
     * Affiche le plateau avec D, F, C ou . selon la nature de la cellule.
     */
    public static void afficherFreePlateau(FreeBoard board) {
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
     * La methode play gerer les actions du jeu
     * @param h height du plateau   
     * @param w width du plateau
     * @param b nombre de ballons
     */
    public void play(int h, int w , int b) {
        int height = h;
        int width = w;
        int numBalloons = b;

        if ( this.mode == 1) {
            // Créer le plateau avec chemin aléatoire
            DefinedBoard board = new DefinedBoard(height, width);
            List<Cell> path = board.getChemins();

            // Afficher le plateau
            System.out.println("Plateau " + height + "x" + width);
            afficherDefinedPlateau(board);
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

            //La vistesse des ballons
            int[] ticParCellList = { 1, 2, 3};
            
            for (int i = 0; i < numBalloons; i++) {
                int ticParCell = ticParCellList[random.nextInt(ticParCellList.length)];
                double speed = 1.0 / ticParCell;
                
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
            int printTic = 1; // On va afficher le plateau apres chaque n tic
            
            //Ca va detecter quand un ballon change de cell
            Position[] lastCell = new Position[numBalloons];
            for (BalloonPath bPath : balloons) {
                lastCell[bPath.getId()] = bPath.getCurrentPosition();
            }

            //Initialiser les ballons et les placers dans board
            placeActiveBalloonsOnBoard(board, balloons);
            System.out.println("TIC = 0");
            afficherDefinedPlateau(board);
            System.out.println();

            while (hasActiveBalloons(balloons) && currentTick < maxTicks) {
                currentTick++;
                
                for (BalloonPath bPath : balloons) {
                    if (!bPath.isActive()) {
                        continue;
                    }

                    else if (bPath.isActive()) {
                        Position before = bPath.getCurrentPosition();
                        bPath.move();
                        Position after = bPath.getCurrentPosition();

                        //On ne affiche ce message que quand un ballon change son cell, sinon il n'affiche pas
                        if (!after.equals(before)) {
                        System.out.println("TIC " + currentTick
                                + " : Bloon " + bPath.getId()
                                + " change de case " + before + " -> " + after);
                        }

                        if (!bPath.isActive() && bPath.getExitTime() == -1) {
                            bPath.setExitTime(currentTick);
                            System.out.println("Bloon " + bPath.getId() + 
                                " (speed: " + String.format("%.1f", bPath.getSpeed()) + 
                                ") exits at time: " + currentTick);
                        }
                    }
                }
                placeActiveBalloonsOnBoard(board, balloons);

                //On affiche board
                if (currentTick % printTic == 0) {
                    System.out.println("TIC = " + currentTick);
                    afficherDefinedPlateau(board);
                    System.out.println();
                }
            }
        }
        else if ( mode == 2 ) {
            // 1. Initialisation du plateau
        FreeBoard board = new FreeBoard(height, width);
        board.init();
        board.setNbCheminsVoulus(numBalloons);
        board.calculerPath();

        // 2. Affichage du plateau (Logique identique au Livrable1b)
        System.out.println("Plateau " + height + "x" + width);
        afficherFreePlateau(board);
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

            BalloonStraight bS = new BalloonStraight(i, start, end, speed, type);
            balloons.add(bS);

            System.out.print("Bloon " + i + " at " + start + " speed: ");
            System.out.printf("%.1f", speed);
            System.out.println(" health: " + bS.getHealth());
        }
        System.out.println("Spawned " + actualPaths + " bloons.\n");

        // 4. Simulation du mouvement
        int maxTick = 5000;
        int currentTick = 0;
        int printTic = 1;

        //Initialiser les ballons et les placers dans board
        placeActiveBalloonsOnBoard(board, null);
        System.out.println("TIC = 0");
        afficherFreePlateau(board);
        System.out.println();

        while (hasActiveBalloons(balloons) && currentTick < maxTick) {
            currentTick++;

            for (BalloonStraight bPath : balloons) {
                if ( !bPath.isActive()) {
                    continue;
                }
                else if (bPath.isActive()) {
                    Position before = bPath.getCurrentPosition();
                    bPath.move(); // Déplace le ballon
                    Position after = bPath.getCurrentPosition();

                    //On ne affiche ce message que quand un ballon change son cell, sinon il n'affiche pas
                    if (!after.equals(before)) {
                    System.out.println("TIC " + currentTick
                            + " : Bloon " + bPath.getId()
                            + " change de case " + before + " -> " + after);
                    }

                    // Si le ballon vient d'arriver (active devient false dans move)
                    if (!bPath.isActive() && bPath.getExitTime() == -1) {
                        System.out.print("Bloon " + bPath.getId() + " (speed: ");
                        System.out.printf("%.1f", bPath.getSpeed());
                        System.out.println(") exits at time: " + currentTick);
                    }
                }
                placeActiveBalloonsOnBoard(board, null);

                if (currentTick % printTic == 0) {
                    System.out.println("TIC = " + currentTick);
                    afficherFreePlateau(board);
                    System.out.println();
                }
            }
        }
        }
    }
 
}
