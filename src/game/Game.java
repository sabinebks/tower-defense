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
     * board : la grille de jeux
     * idCounter : pour les Id des ballons
     * player : le joueur
     */
    private List<Balloon> balloons;
    private List<Tower> towers;
    private List<Projectil> activeProjectils;
    private Board board;
    private int idCounter = 0;
    private Player player;

    /**
     * Le constructeur de la classe Game
     */
    public Game() {
        this.balloons = new ArrayList<>();
        this.towers = new ArrayList<>();
        this.activeProjectils = new ArrayList<>();
        this.board = null;
        this.player = new Player();
    }

    /**
     * Cree et ajoute un ballon au jeu en fonction du type de plateau
     * 
     * @param type le type de ballon
     */

    public void spawnBalloon(BalloonType type) {
        if (board == null)
            return;

        idCounter++;

        if (board instanceof DefinedBoard df) {

            List<Cell> pathCells = df.getChemins();
            if (pathCells == null || pathCells.size() < 2)
                return;

            Position start = pathCells.get(0).getPosition();

            Balloon b = new Balloon(
                    idCounter,
                    start.getRow(),
                    start.getCol(),
                    type);

            List<Position> path = new ArrayList<>();
            for (Cell c : pathCells) {
                path.add(c.getPosition());
            }

            b.setPath(path);
            balloons.add(b);
        }

        else if (board instanceof FreeBoard fb) {

            int nb = fb.retourneNbrChemins();
            if (nb <= 0)
                return;

            int id = new Random().nextInt(nb);
            Position[] chemin = fb.retourneLesChemins(id);

            if (chemin == null || chemin.length < 2)
                return;

            Position start = chemin[0];

            Balloon b = new Balloon(
                    idCounter,
                    start.getRow(),
                    start.getCol(),
                    type);

            List<Position> path = new ArrayList<>();
            for (Position p : chemin) {
                path.add(p);
            }

            b.setPath(path);
            balloons.add(b);
        }
    }

    /**
     * Methode pour gerer tout le jeu en fontion des tick d'horloge
     */

    public void gameTick() {
        try {
            updateEntities();

            // nettoyage
            cleanupEntities();

        } catch (NotMatchException e) {
            System.err.println("Erreur : " + e.getMessage());
        }
    }

    public void render() {
    for (int r = 0; r < board.getHeight(); r++) {
        for (int c = 0; c < board.getWidth(); c++) {
            String entity = checkEntityAt(r, c);
            System.out.print((entity != null ? entity : board.getSymbolAt(r, c)) + " ");
        }
        System.out.println();
    }
    System.out.println("--- STATS ---");
    System.out.println("Credits : " + this.player.getCredit() + " | Ballons : " + balloons.size());
}

    /**
     * retourne le symbole de l'entite a la position donnee pour l'affichage
     * 
     * @param r la ligne
     * @param c la colonne
     */
    public String checkEntityAt(int r, int c) {
        for (Tower t : towers) {
            if (t.getPosition().getRow() == r && t.getPosition().getCol() == c) {
                return t.getSymbol();
            }
        }
        for (Balloon b : balloons) {
            if ((int) b.getLigne() == r && (int) b.getColonne() == c) {
                return "?";
            }
        }

        return null;
    }

    /**
     * ajouter une tower t
     * 
     * @param t
     */
    public void addTower(Tower t) {
        towers.add(t);
    }

    /**
     * methode pour gerer les evolutions des tours de maniere aleatoire
     * parcourt toutes les tours et applique ou supprime une evolution par type de
     * tour
     * 
     * @param ajouter true pour ajouter une evolution, false pour en supprimer une
     */
    private void gererEvolutionAleatoire(boolean ajouter) {
        if (towers.isEmpty())
            return;
        Random rand = new Random();
        Set<String> dejTraite = new HashSet<>();
        for (Tower t : towers) {
            if (t.getEvolutions() == null)
                continue;
            if (dejTraite.contains(t.getSymbol()))
                continue;
            if (ajouter) {
                ArrayList<TypeEvolution> evols = t.getEvolutions().getEvolutions();
                if (evols == null || evols.isEmpty())
                    continue;

                HashMap<TypeEvolution, Integer> possedees = t.getEvolutions().getEvolutionsPossedees();
                List<TypeEvolution> possibles = new ArrayList<>();

                for (TypeEvolution e : evols) {
                    if (!possedees.containsKey(e))
                        possibles.add(e);
                }

                if (possibles.isEmpty())
                    continue;
                TypeEvolution evo = possibles.get(rand.nextInt(possibles.size()));
                int cout = t.appliquerEvolution(evo);
                if (cout > 0) {
                    System.out.println("Evolution ajoutée : " + evo + " sur " + t.getSymbol());
                    dejTraite.add(t.getSymbol());
                }
            } else {
                HashMap<TypeEvolution, Integer> possedees = t.getEvolutions().getEvolutionsPossedees();
                if (possedees == null || possedees.isEmpty())
                    continue;
                List<TypeEvolution> list = new ArrayList<>(possedees.keySet());
                TypeEvolution evo = list.get(rand.nextInt(list.size()));
                t.getEvolutions().vendreEvolution(evo);
                System.out.println("Evolution retirée : " + evo + " sur " + t.getSymbol());
                dejTraite.add(t.getSymbol());
            }
        }
    }

    /**
     * methode pour jouer 10 manches
     * manches 1 a 5 : applique une evolution a chaque tour si possible
     * manches 6 a 10 : supprime une evolution a chaque tour si possible
     * 
     * @param height hauteur du plateau
     * @param width  largeur du plateau
     * @param param  nombre de ballons (modeA) ou nombre de chemins (modeB)
     * @param modeA  true pour DefinedBoard, false pour FreeBoard
     */
    public void run10Manches(int height, int width, int param, boolean modeA) {
        for (int manche = 1; manche <= 10; manche++) {
            System.out.println("===== MANCHE " + manche + " =====");
            if (manche <= 5) {
                gererEvolutionAleatoire(true);
            } else {
                gererEvolutionAleatoire(false);
            }
            if (modeA) {
                autoPlayA(height, width, param); // param = nbBallons
            } else {
                autoPlayB(height, width, param); // param = nbChemins
            }
            balloons.clear();
            activeProjectils.clear();
        }
    }

    /**
     * methode pour afficher les evenements des ballons a un tick donne
     * affiche : TOUCHE, DETRUIT, SORTI, RALENTI, ARRETE, REDEMARRE
     * 
     * @param tick      le numero du tick actuel
     * @param oldSpeed  les vitesses des ballons avant le tick
     * @param oldHealth les santes des ballons avant le tick
     */
    public void afficherEvenementsBallons(
            int tick,
            Map<Integer, Double> oldSpeed,
            Map<Integer, Integer> oldHealth) {

        Set<Integer> dejaTouche = new HashSet<>();

        for (Balloon b : balloons) {
            double old = oldSpeed.getOrDefault(b.getId(), b.getEffectiveSpeed());
            int oldH = oldHealth.getOrDefault(b.getId(), b.getHealth());

            if (b.getHealth() < oldH && !dejaTouche.contains(b.getId())) {
                System.out.println("Tick " + tick + " : Ballon " + b.getId() + " TOUCHE");
                dejaTouche.add(b.getId());
            }

            if (!b.isActive()) {
                if (b.getHealth() <= 0) {
                    System.out.println("Tick " + tick + " : Ballon " + b.getId() + " DETRUIT");
                } else {
                    System.out.println("Tick " + tick + " : Ballon " + b.getId() + " SORTI");
                }
                continue;
            }

            if (b.getEffectiveSpeed() < b.getSpeed() && b.getEffectiveSpeed() > 0) {
                System.out.println("Tick " + tick + " : Ballon " + b.getId() + " RALENTI");
            }

            if (b.getEffectiveSpeed() == 0) {
                System.out.println("Tick " + tick + " : Ballon " + b.getId() + " ARRETE");
            }

            if (old == 0 && b.getEffectiveSpeed() > 0) {
                System.out.println("Tick " + tick + " : Ballon " + b.getId() + " REDEMARRE");
            }
        }
    }

    /**
     * Lance une simulation automatique du jeu sur un plateau défini.
     * Crée le plateau avec les dimensions données, place des tours aléatoires,
     * génère des ballons puis exécute la boucle du jeu jusqu'à leur destruction
     * ou sortie du plateau.
     *
     * @param height    hauteur du plateau
     * @param width     largeur du plateau
     * @param nbBallons nombre de ballons à générer
     */
    public void autoPlayA(int height, int width, int nbBallons) {
        // recréer le board à chaque manche pour éviter les incohérences
        this.board = new DefinedBoard(height, width);

        if (towers.isEmpty()) {
            placerToursAleatoires();
        }

        Random rand = new Random();

        for (int i = 0; i < nbBallons; i++) {
            BalloonType type = BalloonType.values()[rand.nextInt(BalloonType.values().length)];
            spawnBalloon(type);
        }

        int tick = 0;
        int max = 10000;
        Map<Integer, Double> oldSpeed;
        Map<Integer, Integer> oldHealth;

        render();
        while (!(balloons.isEmpty() && activeProjectils.isEmpty()) && tick < max) {
            oldSpeed = saveOldSpeeds();
            oldHealth = saveOldHealth();
            gameTick();
            afficherEvenementsBallons(tick, oldSpeed, oldHealth);

            tick++;
        }
        System.out.println("=== FIN DE MANCHE ===");
    }

    /**
     * Lance une simulation automatique du jeu sur un plateau libre.
     * Génère des chemins, crée des ballons suivant ces chemins,
     * place des tours aléatoires et exécute la simulation jusqu'à
     * destruction de tous les ballons.
     *
     * @param height    hauteur du plateau
     * @param width     largeur du plateau
     * @param nbChemins nombre de ballons à générer
     */
    public void autoPlayB(int height, int width, int nbChemins) {

        // FIX : recréer le board à chaque manche
        this.board = new FreeBoard(height, width);
        FreeBoard fb = (FreeBoard) this.board;
        fb.init();
        fb.setNbCheminsVoulus(nbChemins);
        fb.calculerPath();

        Random rand = new Random();

        if (towers.isEmpty()) {
            placerToursAleatoires();
        }

        int nbCheminsReels = fb.retourneNbrChemins();

        for (int i = 0; i < nbCheminsReels; i++) {
            Position[] chemin = fb.retourneLesChemins(i);

            if (chemin != null && chemin.length >= 2) {
                Position start = chemin[0];

                Balloon b = new Balloon(
                        ++idCounter,
                        start.getRow(),
                        start.getCol(),
                        BalloonType.values()[rand.nextInt(BalloonType.values().length)]);
                List<Position> path = new ArrayList<>();
                for (Position p : chemin) {
                    path.add(p);
                }
                b.setPath(path);
                balloons.add(b);
            }
        }

        int tick = 0;
        Map<Integer, Double> oldSpeed;
        Map<Integer, Integer> oldHealth;

        render();

        while (!(balloons.isEmpty() && activeProjectils.isEmpty())) {

            oldSpeed = saveOldSpeeds();
            oldHealth = saveOldHealth();
            gameTick();
            afficherEvenementsBallons(tick, oldSpeed, oldHealth);
            tick++;
        }
        System.out.println("=== FIN DE MANCHE ===");
    }

    /**
     * Factory method pour créer une tour en fonction de son typeId
     *
     * @param typeId l'identifiant du type de tour (1 à 7)
     * @param pos    la position où placer la tour
     * @return une instance de Tower correspondante au typeId, ou null si typeId invalide
     */
    private Tower createTower(int typeId, Position pos) {
        switch (typeId) {
            case 1:
                return new BombTower(pos);
            case 2:
                return new DartMonkey(pos);
            case 3:
                return new EliteSniper(pos);
            case 4:
                return new Gorilla(pos);
            case 5:
                return new IceTower(pos);
            case 6:
                return new SlowTower(pos);
            case 7:
                return new TackShooter(pos);
            default:
                return null;
        }
    }

    /**
     * Place des tours aléatoirement sur le plateau, en évitant les chemins.
     * Place jusqu'à 2 tours de chaque type (1 à 7) si possible.
     */
    private void placerToursAleatoires() {

        // FIX : éviter crash si board null
        if (board == null)
            return;

        ArrayList<Position> nonPathPositions = new ArrayList<>();
        Cell[][] grid = board.getGrid();
        Random rand = new Random();

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (!grid[i][j].isPath()) {
                    nonPathPositions.add(new Position(i, j));
                }
            }
        }

        for (int t = 1; t <= 7; t++) {
            for (int k = 0; k < 2; k++) {

                // FIX : ne pas quitter toute la méthode, juste la boucle
                if (nonPathPositions.isEmpty())
                    break;

                Position pos = nonPathPositions.remove(
                        rand.nextInt(nonPathPositions.size()));

                Tower tower = createTower(t, pos);

                if (tower != null) {
                    addTower(tower);
                }
            }
        }
    }

    /**
     * Met à jour les positions des ballons, les actions des tours et les
     * projectiles actifs.
     * Lance une NotMatchException si une tour tente d'attaquer un ballon hors de
     * portée.
     */
    private void updateEntities() throws NotMatchException {
        for (Balloon b : balloons) {
            if (b.isActive()) {
                b.move(board.getHeight(), board.getWidth());
            }
        }
        for (Tower t : towers) {
            t.update(balloons, activeProjectils); // lance NotMatchException
        }
        for (Projectil p : activeProjectils) {
            if (p.isActive())
                p.update();
        }
    }

    /**
     * Nettoie les entités inactives et met à jour les crédits du joueur pour les
     * ballons détruits.
     */
    private void cleanupEntities() {
        // Crédits pour ballons détruits
        for (Balloon b : balloons) {
            if (!b.isActive() && b.getHealth() <= 0) {
                this.player.setCredit(10);
            }
            else {
                this.player.perdreVie();
            }
        }

        // Retirer les projectiles et ballons inactifs
        activeProjectils.removeIf(p -> !p.isActive());
        balloons.removeIf(b -> !b.isActive() && b.getHealth() <= 0);
    }

    /**
     * Fonction pour recuperrer la vitesse ancienne des ballons
     * 
     * @return un map des vitesses anciennes des ballons
     */
    private Map<Integer, Double> saveOldSpeeds() {
        Map<Integer, Double> oldSpeed = new HashMap<>();
        for (Balloon b : balloons) {
            oldSpeed.put(b.getId(), b.getSpeed());
        }
        return oldSpeed;
    }

    /**
     * Fonction pour recuperrer la sante ancienne des ballons
     * 
     * @return un map des santes anciennes des ballons
     */
    private Map<Integer, Integer> saveOldHealth() {
        Map<Integer, Integer> oldHealth = new HashMap<>();
        for (Balloon b : balloons) {
            oldHealth.put(b.getId(), b.getHealth());
        }
        return oldHealth;
    }


    /**
     * Vérifie si une position est valide pour placer une tour.
     * Une position est valide si elle est à l'intérieur du plateau et n'est pas un chemin.
     *
     * @param pos la position à vérifier
     * @return true si valide, false sinon
     */

    public boolean posValide(Position pos) {
        if ((this.board == null) || (!this.board.isInside(pos.getRow(), pos.getCol()))
                || (this.board.getCell(pos).isPath())) {
            return false;
        }
        return true;
    }

    /**
     * Methode pour acheter et placer une tour
     * Verifie le type de tour, les credits du joueur et la position avant de
     * placer la tour
     * 
     * @param t le type de tour a acheter (1 à 7)
     * @param p la position ou placer la tour
     */
    public void acheterEtPlacerTour(int t, Position p) {
        Tower tow = createTower(t, new Position(0, 0));
        if (tow == null) {
            System.out.println("Type de tour invalide");
            return;
        }
        if (this.player.getCredit() < tow.getCout()) {
            System.out.println("Credit insuffisant");
            return;
        }

        if (p == null || !posValide(p)) {
            System.out.println("Position invalide");
            return;
        }
        Tower tower = createTower(t, p);
        addTower(tower);
        this.player.setCredit(0 - tower.getCout());
    }

    /**
     * Methode pour vendre le tower a l'index choisi dans la liste des towers du
     * joueurs
     * 
     * @param i du tower a vendre dans la liste
     * 
     *          public void vendreTour(int i) {
     *          if (this.towers.isEmpty()) {
     *          System.out.println("Aucun tour a vendre");
     *          return;
     *          }
     *          else if (i < 1 || i > this.towers.size()) {
     *          System.out.println("Indice invalide");
     *          return;
     *          }
     *          Tower tower = this.towers.remove(i - 1);
     *          //Math round nous aide a convertir un type double vers un type int,
     *          ce qui nous aide a evite les erreurs
     *          int n = (int)Math.round(calculInvestissementTotal(tower) *
     *          REVENDRE);
     *          this.credits += n;
     *          }
     */
}