package game;

import board.*;
import board.typeOfBoard.*;
import object.balloon.*;
import object.tower.*;
import object.tower.projectil.*;

import java.util.*;

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
     */
    public Game() {
        this.balloons = new ArrayList<>();
        this.towers = new ArrayList<>();
        this.activeProjectils = new ArrayList<>();
        this.board = null;
        this.credits = 100;
    }

    /**
     * Cree et ajoute un ballon au jeu en fonction du type de plateau
     * @param type le type de ballon 
     * @param speed la vitesse du ballon
     */
    public void spawnBalloon(BalloonType type) {
        idCounter++;

        //voir le type de board
        if (this.board instanceof DefinedBoard) {
            DefinedBoard df = (DefinedBoard) board;
            List<Cell> path = df.getChemins();

            if (path != null && !path.isEmpty()) {

                BalloonPath b = new BalloonPath(idCounter, path.get(0), type, path);
                balloons.add(b);
            }
        }
        else if (this.board instanceof FreeBoard) {
            FreeBoard fb = (FreeBoard) board;
            Position[] positions = fb.retourneLesChemins(0);

            if (positions != null && positions.length >= 2) {

                BalloonStraight b = new BalloonStraight(idCounter, positions[0], positions[1], type);
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
                    b.move(this.board.getHeight(), this.board.getWidth());
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
        if (this.board instanceof DefinedBoard) {
            afficherDefinedPlateau((DefinedBoard) this.board);
        } else if (this.board instanceof FreeBoard) {
            afficherFreePlateau((FreeBoard) this.board);
        }

        System.out.println("--- STATS ---");
        System.out.println("Credits : " + this.credits + " | Ballons : " + balloons.size());
    }

    /**
     * On utilise checkEntityAt pour afficher "B" au bon endroit sur la grille
     * @param r la ligne 
     * @param c la colonne
     */
    private String checkEntityAt(int r, int c) {
        for (Balloon b : balloons) {
            // on cast en int pour l'affichage sur la grille de caractères
            if ((int)b.getLigne() == r && (int)b.getColonne() == c) {
                return "?";
            }
        }
        //pour les towers
        for (Tower t : towers) {
            if (t.getPosition().getRow() == r && t.getPosition().getCol() == c && (t instanceof BombTower)) {
                return "B";
            }
            else if (t.getPosition().getRow() == r && t.getPosition().getCol() == c && (t instanceof DartMonkey)) {
                return "A";
            }
            else if (t.getPosition().getRow() == r && t.getPosition().getCol() == c && (t instanceof EliteSniper)) {
                return "E";
            }
            else if (t.getPosition().getRow() == r && t.getPosition().getCol() == c && (t instanceof Gorilla)) {
                return "G";
            }
            else if (t.getPosition().getRow() == r && t.getPosition().getCol() == c && (t instanceof IceTower)) {
                return "I";
            }
            else if (t.getPosition().getRow() == r && t.getPosition().getCol() == c && (t instanceof SlowTower)) {
                return "S";
            }
            else if (t.getPosition().getRow() == r && t.getPosition().getCol() == c && (t instanceof TackShooter)) {
                return "T";
            }

        }
        return null;
    }

    /**
     * methode pour affciher exactement la grille DefinedBoard
     * @param board le board de type Defined
     */
    public void afficherDefinedPlateau(DefinedBoard board) {
        List<Cell> path = board.getChemins();

        for (int r = 0; r < board.getHeight(); r++) {
            for (int c = 0; c < board.getWidth(); c++) {
                String entity = checkEntityAt(r, c);
                if (entity != null) {
                    System.out.print(entity + " ");
                }
                else {
                    Cell cell = board.getCell(r, c);
                    if (cell.isPath()) {
                    if (!path.isEmpty() && cell == path.get(0)) {
                        System.out.print("D ");
                    } 
                    else if (!path.isEmpty() && cell == path.get(path.size() - 1)) {
                        System.out.print("F ");
                    } 
                    else {
                        System.out.print("c ");
                    }
                } else {
                    System.out.print(". ");
                }
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
        int nbDeChemins = board.retourneNbrChemins();
        
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                String entity = checkEntityAt(i, j);
                if (entity != null) { 
                    System.out.print(entity + " ");
                }
                else {
                    boolean isStart = false;
                    boolean isEnd = false;

                    for (int k = 0; k < nbDeChemins; k++) {
                        Position[] chemin = board.retourneLesChemins(k);
                        Position start = chemin[0];
                        Position end = chemin[1];

                        if (i == start.getRow() && j == start.getCol()) {
                            isStart = true;
                            break;
                        }
                        if (i == end.getRow() && j == end.getCol()) {
                            isEnd = true;
                            break;
                        }
                    }

                    if (isStart) {
                        System.out.print("D ");
                    } 
                    else if (isEnd) {
                        System.out.print("F ");
                    }
                    else if (grid[i][j].isPath()) {
                        System.out.print("c ");
                    }            
                    else {
                        System.out.print(". ");
                    }
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
    /**
     * Methode play de la classe Game, qui jerer le core du jeu
     * Il y a 3 mode ( pour maintenat ) de DefinedBoard
     * 1 pour Commencer le round, 2 pour placer les tours, 3 pour sortir
     * @param height la hauteur du board
     * @param width la largeur du board
     */
    public void play(int height, int width){
        Scanner scan = new Scanner(System.in);
        Random rand = new Random();

        System.out.println("=== TOWER DEFENSE (Livrable 3) ===");
        System.out.println("Veuillez choisir un mode :");
        System.out.println("1 : DefinedBoard ( Rue defini ) ");
        System.out.println("2 : FreeBoard ( Pas de rue defini ) ");

        int mode = Integer.parseInt(scan.nextLine().trim());
        //Mode default
        if ( mode != 1 && mode != 2) {
            mode = 1;
        }

        if (mode == 1) {
            this.board = new DefinedBoard(height, width);
        } 
        else {
            this.board = new FreeBoard(height, width);
            FreeBoard fb = (FreeBoard) this.board;
            fb.init();
            fb.setNbCheminsVoulus(5);
            fb.calculerPath();
        }
        System.out.println("Mode choisi:");
        if ( mode == 1) {
            System.out.println("DefinedBoard ");
        }
        else {
            System.out.println("FreeBoard ");
        }
        System.out.println("Plateau de hauteur " + height + " et largeur " + width);
    
        render();
        
        while (true) {
            System.out.println("--- OPTION HOLA MI AMIGOS ---");
            System.out.println("1 : Commencer le round ");
            System.out.println("2 : Placer une tour");
            System.out.println("3 : Quitter");
            
            int choix = Integer.parseInt(scan.nextLine().trim());

            if (choix == 3) {
                System.out.println("Merci de jouer ce super cool jeu!");
                scan.close();
                break;
            }

            else if (choix == 1) {
                System.out.print("Combien de ballons tu veux spawn au debut du round ? > ");
                int nb = Integer.parseInt(scan.nextLine().trim());
                if (nb < 0) nb = 0;

                System.out.println("=== NEW ROUND ===");
                System.out.println("Generer = " + nb + " ballons");

                for (int i = 0; i < nb; i++) {
                    BalloonType type = BalloonType.values()[rand.nextInt(BalloonType.values().length)];
                    spawnBalloon(type);
                    System.out.println("Balloon " + (i + 1) + " | type = " + this.balloons.get(i).getType() + " | speed = " + this.balloons.get(i).getSpeed() + " | health = " + this.balloons.get(i).getHealth());
                }



                int tick = 0;
                int max = 10000; // ca aide a eviter des boucles trop longs

                while (!(balloons.isEmpty() && activeProjectils.isEmpty()) && tick < max) {
                    gameTick(); // dans 1 tic : ballon bouge, les tours attaquent,render le board
                    System.out.println(" Le tick numero " + tick);
                    tick++;
                }

                System.out.println("=== ROUND FINISHED ===");
                System.out.println("Ticks joues = " + tick);
                continue;
            }
            else if (choix == 2){
                System.out.println("Veuillez choisir la tour :");
                System.out.println("1 : Bomb Tower");
                System.out.println("2 : Dart Monkey");
                System.out.println("3 : Elite Sniper");
                System.out.println("4 : Gorilla");
                System.out.println("5 : Ice Tower");
                System.out.println("6 : Slow Tower");
                System.out.println("7 : Tack Shooter");
                
                int tour = Integer.parseInt(scan.nextLine().trim());
                int row = -1;
                int col = -1;
                while ( !board.isInside(row, col)){
                    System.out.println("--Veuillez entrer la position--");
                    System.out.println("Colonne : ");
                    col = Integer.parseInt(scan.nextLine().trim());
                    System.out.println("Ligne : ");
                    row = Integer.parseInt(scan.nextLine().trim());
                }
                if (this.board instanceof DefinedBoard) {
                    DefinedBoard db = (DefinedBoard) board;
                    if (db.getCell(row, col).isPath()) {
                        System.out.println("Error! Vous ne pouvez pas placer un tour sur le chemin");
                        System.out.println("Veuillez refaire votre choix! Merci");
                        continue;
                    }
                }
                boolean isTour = false;
                for (Tower t : towers) {
                    if (t.getPosition().getRow() == row && t.getPosition().getCol() == col) {
                    isTour = true;
                    break;
                    }
                }
                if (isTour) {
                    System.out.println("Il y a deja une tour ici!");
                    System.out.println("Veuillez refaire votre choix! Merci");
                    continue;
                }
                Tower t = null;
                switch (tour) {
                    case 1:
                        t = new BombTower(new Position(row, col));   
                        break;
                    case 2:
                        t = new DartMonkey(new Position(row, col));
                        break;
                    case 3:
                        t = new EliteSniper(new Position(row, col));
                        break;
                    case 4:
                        t = new Gorilla(new Position(row, col));
                        break;
                    case 5:
                        t = new IceTower(new Position(row, col));
                        break;
                    case 6:
                        t = new SlowTower(new Position(row, col));
                        break;
                    case 7:
                        t = new TackShooter(new Position(row, col));
                        break;
                }
                this.towers.add(t);
                System.out.println("Tour place en |" + row + " : " + col + "|");
                render();
            }
        } 
    }
}