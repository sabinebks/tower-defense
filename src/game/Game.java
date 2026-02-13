package game;

import board.Board;
import board.Cell;
import board.Position;
import board.typeOfBoard.DefinedBoard;
import board.typeOfBoard.FreeBoard;
import object.balloon.*;
import object.tower.Tower;
import object.tower.projectil.NotMatchException;
import object.tower.projectil.Projectil;

import java.util.ArrayList;
import java.util.List;

public class Game {
    /**
     * les attributs :
     * ballons : les ballons qui existent dans la grille
     * towers : les towers qui existe dans la grille
     * activeProjectils : les projectil qui seront lancer par les towers
     * cerdits : cerdit du joueur
     * board : la grille de jeux
     * idCounter : pour les Id des ballons
     *
     */
    private List<Balloon> balloons;
    private List<Tower> towers;
    private List<Projectil> activeProjectils;
    private Board board;
    private int credits;
    private int idCounter = 0;

    /**
     * Le constructeur de la classe Game
     * @param board le board du jeu
     */
    public Game(Board board) {
        this.balloons = new ArrayList<>();
        this.towers = new ArrayList<>();
        this.activeProjectils = new ArrayList<>();
        this.board = board;
        this.credits = 100;
    }

    /**
     * Cree et ajoute un ballon au jeu en fonction du type de plateau
     * @param type le type de ballon 
     * @param speed la vitesse du ballon
     */
    public void spawnBalloon(BalloonType type, double speed) {
        idCounter++;

        //voir le type de board
        if (this.board instanceof DefinedBoard) {
            DefinedBoard df = (DefinedBoard) board;
            List<Cell> path = df.getChemins();

            if (path != null && !path.isEmpty()) {

                BalloonPath b = new BalloonPath(idCounter, path.get(0), speed, type, path);
                balloons.add(b);
            }
        }
        else if (this.board instanceof FreeBoard) {
            FreeBoard fb = (FreeBoard) board;
            Position[] positions = fb.retourneLesChemins(0);

            if (positions != null && positions.length >= 2) {

                BalloonStraight b = new BalloonStraight(idCounter, positions[0], positions[1], speed, type);
                balloons.add(b);
            }
        }
    }

    /**
     * Methode pour gerer tout le jeu en fontion des tick d'horloge
     */

    public void gameTick() {
        try {
            // mise à jour des ballons en utilisant move de ballons
            for (Balloon b : balloons) {
                if (b.isActive()) {
                    b.updateState();
                    b.move();
                }
            }

            // attaque des tours
            for (Tower t : towers) {
                t.update(balloons, activeProjectils);
            }

            // Vol des projectiles
            for (Projectil p : activeProjectils) {
                if (p.isActive()) {
                    p.update();
                }
            }

            // nettoyage
            for (Balloon b : balloons) {
                if (!b.isActive() && b.getHealth() <= 0) {
                    this.credits += 10;
                }
            }
            activeProjectils.removeIf(p -> !p.isActive());
            balloons.removeIf(b -> !b.isActive());

            render();

        } catch (NotMatchException e) {
            System.err.println("Erreur : " + e.getMessage());
        }
    }

    /**
     * Methode principale pour l'affichage
     */
    private void render() {
        //pour effacer le contenu du terminal
        System.out.print("\033[H\033[2J");
        //elle garde qlq caractere dans les buffer de processeur elle va les afficher apres
        System.out.flush();

        if (this.board instanceof DefinedBoard) {
            afficherDefinedPlateau((DefinedBoard) this.board);
        } else if (this.board instanceof FreeBoard) {
            afficherFreePlateau((FreeBoard) this.board);
        }

        System.out.println("\n--- STATS ---");
        System.out.println("Crédits : " + this.credits + " | Ballons : " + balloons.size());
    }

    /**
     * On utilise checkEntityAt pour afficher "B" au bon endroit sur la grille
     * @param r la ligne 
     * @param c la colonne
     */
    private String checkEntityAt(int r, int c) {
        for (Balloon b : balloons) {
            // on cast en int pour l'affichage sur la grille de caractères
            if ((int)b.getPosY() == r && (int)b.getPosX() == c) return "B";
        }
        //pour les towers
        for (Tower t : towers) {
            if (t.getPosition().getRow() == r && t.getPosition().getCol() == c) return "T";
        }
        return null;
    }

    /**
     * methode pour affciher exactement la grille DefinedBoard
     * @param board le board de type Defined
     */
    public void afficherDefinedPlateau(DefinedBoard board) {
        for (int r = 0; r < board.getHeight(); r++) {
            for (int c = 0; c < board.getWidth(); c++) {
                String entity = checkEntityAt(r, c);
                if (entity != null) System.out.print(entity + " ");
                else {
                    Cell cell = board.getCell(r, c);
                    if (cell.isPath()) System.out.print("c ");
                    else System.out.print(". ");
                }
            }
            System.out.println();
        }
    }

    /**
     * pour afficher FreeBoard
     * @param board
     */
    public void afficherFreePlateau(FreeBoard board) {
        Cell[][] grid = board.getGrid();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                String entity = checkEntityAt(i, j);
                if (entity != null) System.out.print(entity + " ");
                else {
                    if (grid[i][j].isPath()) System.out.print("c ");
                    else System.out.print(". ");
                }
            }
            System.out.println();
        }
    }

    /**
     * ajouter une tower t
     * @param t
     */
    public void addTower(Tower t) { 
        towers.add(t);
    }


}