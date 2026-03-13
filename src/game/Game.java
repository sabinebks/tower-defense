package game;

import board.*;
import board.typeOfBoard.*;
import object.balloon.*;
import object.tower.*;
import object.tower.evolution.TypeEvolution;
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
    public static final int CREDIT_INITIAL = 1000;
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
        this.credits = CREDIT_INITIAL;
    }

    /**
     * Cree et ajoute un ballon au jeu en fonction du type de plateau
     * @param type le type de ballon
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

            int nb = fb.retourneNbrChemins();
            if (nb <= 0) return;

            // choose a random straight path
            int id = new Random().nextInt(nb);
            Position[] positions = fb.retourneLesChemins(id);

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
    public void play(int height, int width) throws InvalidTowerPlacementException{
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
            System.out.println("--- MENU ---");
            System.out.println("1 : Commencer le round ");
            System.out.println("2 : Placer une tour");
            System.out.println("3 : Améliorer / Vendre évolution d'une tour");
            System.out.println("4 : Quitter");

            int choix = Integer.parseInt(scan.nextLine().trim());

            if (choix == 4) {
                System.out.println("Merci de jouer!");
                scan.close();
                break;
            } else if (choix == 1) {
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
                    System.out.println(" Le tick numero " + tick);
                    gameTick(); // dans 1 tic : ballon bouge, les tours attaquent,render le board
                    tick++;
                }

                System.out.println("=== ROUND FINISHED ===");
                System.out.println("Ticks joues = " + tick);
                continue;
            } else if (choix == 2) {
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
                boolean positionValide = false;

                while (!positionValide) {
                    System.out.println("--Veuillez entrer la position--");
                    System.out.print("Colonne : ");
                    col = Integer.parseInt(scan.nextLine().trim());
                    System.out.print("Ligne : ");
                    row = Integer.parseInt(scan.nextLine().trim());

                    if (!board.isInside(row, col)) {
                        System.out.println("Position hors du plateau !");
                        continue;
                    }

                    if (board instanceof DefinedBoard) {
                        DefinedBoard db = (DefinedBoard) board;
                        if (db.getCell(row, col).isPath()) {
                            System.out.println("Erreur ! Vous ne pouvez pas placer une tour sur le chemin.");
                            continue;
                        }
                    }


                    boolean occupe = false;
                    for (Tower tExisting : towers) {
                        if (tExisting.getPosition().getRow() == row && tExisting.getPosition().getCol() == col) {
                            occupe = true;
                            break;
                        }
                    }

                    if (occupe) {
                        System.out.println("Il y a déjà une tour ici !");
                    } else {
                        positionValide = true;
                    }
                }

                Tower t = null;
                Position p = new Position(row, col);
                switch (tour) {
                    case 1:
                        t = new BombTower(p);
                        break;
                    case 2:
                        t = new DartMonkey(p);
                        break;
                    case 3:
                        t = new EliteSniper(p);
                        break;
                    case 4:
                        t = new Gorilla(p);
                        break;
                    case 5:
                        t = new IceTower(p);
                        break;
                    case 6:
                        t = new SlowTower(p);
                        break;
                    case 7:
                        t = new TackShooter(p);
                        break;
                    default:
                        System.out.println("Choix invalide");
                        continue;
                }
                if (this.credits >= t.getCout()) {
                    this.credits -= t.getCout();
                    this.towers.add(t);
                    System.out.println("Tour place en |" + row + " : " + col + "|" + " : (-" + t.getCout() + " crédits)");
                }else{
                    System.out.println("Vous n'avez pas assez de credits pour acheter cette tour!");
                }

                render();
            } else if (choix == 3) {
                System.out.println("Entrez la position de la tour (Ligne puis Colonne) :");
                System.out.print("Ligne : ");
                int row = Integer.parseInt(scan.nextLine().trim());
                System.out.print("Colonne : ");
                int col = Integer.parseInt(scan.nextLine().trim());

                Tower tourCible = null;
                for (Tower t : towers) {
                    if (t.getPosition().getRow() == row && t.getPosition().getCol() == col) {
                        tourCible = t;
                        break;
                    }
                }

                if (tourCible == null) {
                    System.out.println("Aucune tour à cette position !");
                    return;
                }

                if (!tourCible.getEvolution()) {
                    System.out.println("Cette tour ne peut pas évoluer.");
                    return;
                }

                System.out.println("--- GESTION TOUR " + tourCible.getClass().getSimpleName() + " ---");
                System.out.println("1 : Acheter une évolution");
                System.out.println("2 : Revendre une évolution");
                int action = Integer.parseInt(scan.nextLine().trim());

                if (action == 1) {
                    ArrayList<TypeEvolution> dispos = tourCible.getEvolutions().getEvolutions();
                    if (dispos.isEmpty()) {
                        System.out.println("Toutes les évolutions sont déjà achetées !");
                        return;
                    }

                    System.out.println("Évolutions disponibles :");
                    for (int i = 0; i < dispos.size(); i++) {
                        System.out.println(i + " : " + dispos.get(i));
                    }

                    int choixEvo = Integer.parseInt(scan.nextLine().trim());
                    if (choixEvo >= 0 && choixEvo < dispos.size()) {
                        TypeEvolution typeChoisi = dispos.get(choixEvo);
                        int cout = tourCible.appliquerEvolution(typeChoisi);

                        if (cout > 0 && this.credits >= cout) {
                            this.credits -= cout;
                            System.out.println("Évolution " + typeChoisi + " appliquée ! (-" + cout + " crédits)");
                        } else if (this.credits < cout) {
                            System.out.println("Crédits insuffisants !");
                        }
                    }
                } else if (action == 2) {
                    Map<TypeEvolution, Integer> possedees = tourCible.getEvolutions().getEvolutionsPossedees();
                    if (possedees.isEmpty()) {
                        System.out.println("Aucune évolution à revendre.");
                        return;
                    }

                    System.out.println("Évolutions possédées :");
                    List<TypeEvolution> cles = new ArrayList<>(possedees.keySet());
                    for (int i = 0; i < cles.size(); i++) {
                        System.out.println(i + " : " + cles.get(i) + " (Valeur revente : " + (possedees.get(cles.get(i)) / 2) + ")");
                    }

                    int choixVente = Integer.parseInt(scan.nextLine().trim());
                    if (choixVente >= 0 && choixVente < cles.size()) {
                        TypeEvolution typeAVendre = cles.get(choixVente);
                        int remboursement = possedees.get(typeAVendre) / 2;

                        this.credits += remboursement;
                        System.out.println("Évolution revendue ! (+" + remboursement + " crédits)");
                    }
                }
            }
        }
    }

    /**
     * Lance une simulation automatique du jeu sur un plateau défini.
     * Crée le plateau avec les dimensions données, place des tours aléatoires,
     * génère des ballons puis exécute la boucle du jeu jusqu'à leur destruction
     * ou sortie du plateau.
     *
     * @param height hauteur du plateau
     * @param width largeur du plateau
     * @param nbBallons nombre de ballons à générer
     */


    public void autoPlayA(int height, int width, int nbBallons){
        this.board = new DefinedBoard(height, width);
        render();

        // Liste des positions possibles pour placer les tours
        ArrayList<Position> pathPositions = new ArrayList<>();
        ArrayList<Position> nonPathPositions = new ArrayList<>();
        Cell[][] grid = board.getGrid();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                Position pos = new Position(i, j);
                if (grid[i][j].isPath()) pathPositions.add(pos);
                else nonPathPositions.add(pos);
            }
        }

        Random rand = new Random();

        // Placer 2 tours de chaque type aléatoirement
        for (int t = 1; t <= 7; t++) { // 1 à 7 = types de tours
            for (int k = 0; k < 2; k++) {
                Position pos;
                if (t == 1 || t == 2 || t == 5) { // tours pouvant être sur le chemin par exemple
                    if (pathPositions.isEmpty()) break;
                    pos = pathPositions.remove(rand.nextInt(pathPositions.size()));
                } else {
                    if (nonPathPositions.isEmpty()) break;
                    pos = nonPathPositions.remove(rand.nextInt(nonPathPositions.size()));
                }

                Tower tour = null;
                switch (t) {
                    case 1: tour = new BombTower(pos); break;
                    case 2: tour = new DartMonkey(pos); break;
                    case 3: tour = new EliteSniper(pos); break;
                    case 4: tour = new Gorilla(pos); break;
                    case 5: tour = new IceTower(pos); break;
                    case 6: tour = new SlowTower(pos); break;
                    case 7: tour = new TackShooter(pos); break;
                }
                if (tour != null) addTower(tour);
            }
        }

        // Créer les ballons
        for (int i = 0; i < nbBallons; i++) {
            BalloonType type = BalloonType.values()[rand.nextInt(BalloonType.values().length)];
            spawnBalloon(type);
        }

        int tick = 0;
        int max = 10000;

        // Boucle principale du jeu
        while (!(balloons.isEmpty() && activeProjectils.isEmpty()) && tick < max) {
            gameTick();

            // Affichage simple des événements
            for (Balloon b : balloons) {
                if (!b.isActive()) {
                    System.out.println("Tick " + tick + ": Ballon détruit | type=" + b.getType());
                } else if (!board.isInside((int)b.getLigne(), (int)b.getColonne())) {
                    System.out.println("Tick " + tick + ": Ballon sorti | type=" + b.getType());
                    b.remove(); // retirer le ballon
                } else if (b.getEffectiveSpeed() < b.getSpeed()) {
                    System.out.println("Tick " + tick + ": Ballon ralenti | type=" + b.getType() + " vitesse=" + b.getEffectiveSpeed());
                }
            }

            tick++;
        }

        System.out.println("=== AUTO PLAY FINISHED ===");
        System.out.println("Ticks joues = " + tick);
    }
    /**
     * Lance une simulation automatique du jeu sur un plateau libre.
     * Génère des chemins, crée des ballons suivant ces chemins,
     * place des tours aléatoires et exécute la simulation jusqu'à
     * destruction de tous les ballons.
     *
     * @param height hauteur du plateau
     * @param width largeur du plateau
     * @param nbBallons nombre de ballons à générer
     */
    public void autoPlayB(int height, int width, int nbBallons) {
        // Initialisation du board
        this.board = new FreeBoard(height, width);
        FreeBoard fb = (FreeBoard) this.board;
        fb.init();
        fb.setNbCheminsVoulus(3); // on peut générer 3 chemins
        fb.calculerPath();

        // Initialisation des ballons droits
        balloons.clear();
        Random rand = new Random();
        for (int i = 0; i < nbBallons; i++) {
            int cheminNum = rand.nextInt(fb.retourneNbrChemins());
            Position start = fb.retourneLesChemins(cheminNum)[0];
            Position end = fb.retourneLesChemins(cheminNum)[1];
            BalloonStraight b = new BalloonStraight(idCounter++, start, end, BalloonType.values()[rand.nextInt(BalloonType.values().length)]);
            balloons.add(b);
        }

        // Placer quelques tours aléatoires (peu importe le chemin)
        towers.clear();
        for (int t = 1; t <= 5; t++) { // quelques tours
            int r = rand.nextInt(height);
            int c = rand.nextInt(width);
            Position pos = new Position(r, c);
            Tower tower = null;
            switch (t) {
                case 1: tower = new BombTower(pos); break;
                case 2: tower = new DartMonkey(pos); break;
                case 3: tower = new IceTower(pos); break;
                case 4: tower = new SlowTower(pos); break;
                case 5: tower = new TackShooter(pos); break;
            }
            if (tower != null) towers.add(tower);
        }

        int tick = 0;
        boolean finished = false;

        while (!finished) {
            tick++;

            // Avancer les ballons
            for (Balloon b : balloons) {
                if (b.isActive()) {
                    b.updateState();
                    b.move(height, width);
                }
            }

            // Attaque des tours
            for (Tower t : towers) {
                try {
                    t.update(balloons, activeProjectils);
                } catch (NotMatchException e) {
                    System.err.println("Erreur lors de l'attaque d'une tour : " + e.getMessage());

                }
            }
            // Vol des projectiles
            for (Projectil p : activeProjectils) {
                if (p.isActive()) p.update();
            }

            // Nettoyage
            for (Balloon b : balloons) {
                if (!b.isActive() && b.getHealth() <= 0) credits += 10;
            }
            balloons.removeIf(b -> !b.isActive());
            activeProjectils.removeIf(p -> !p.isActive());

            // Affichage du board
            afficherFreePlateau(fb);


            System.out.println("--- STATS ---");
            System.out.println("Ticks : " + tick + " | Ballons actifs : " + balloons.size() + " | Credits : " + credits);
            System.out.println();

            // Fin si tous les ballons sont morts
            finished = balloons.isEmpty();
        }

        System.out.println("=== AUTO PLAY B FINISHED ===");
        System.out.println("Ticks joues = " + tick);
    }

}