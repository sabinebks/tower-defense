package game;

import board.*;
import board.typeOfBoard.*;
import object.balloon.*;
import object.tower.*;
import object.tower.evolution.TypeEvolution;
import object.tower.projectil.*;

import java.util.*;

public class Game {
    private static int NBBALLOONS = 5;
    private static short MAXTIC = 10000;
    private static int NBROUNDS = 10;
    /**
     * les attributs :
     * ballons : les ballons qui existent dans la grille
     * towers : les towers qui existe dans la grille
     * activeProjectils : les projectil qui seront lancer par les towers
     * cerdits : cerdit du joueur
     * board : la grille de jeux
     * idCounter : pour les Id des ballons
     * vie : nombre de vie du joueur, commencer avec 20
     */
    private Player player;;
    private List<Round> rounds;
    private Round currentRound;

    /**
     * Le constructeur de la classe Game
     */
    public Game() {
        this.player = new Player();
        this.rounds = new ArrayList<>();
        for(int i = 0 ; i < NBROUNDS; i++){
            this.rounds.add(new Round(i));
        }
        this.currentRound = this.rounds.get(0);
    }

    /**
     * Met à jour les positions des ballons, les actions des tours et les
     * projectiles actifs.
     * Lance une NotMatchException si une tour tente d'attaquer un ballon hors de
     * portée.
     */
    private void updateEntities() throws NotMatchException {
        for (Balloon b : currentRound.getBalloons()) {
            if (b.isActive()) {
                b.move(currentRound.getBoard().getHeight(),currentRound.getBoard().getWidth());
            }
        }
        for (Tower t : player.getTowers()) {
            t.update(currentRound.getBalloons(), currentRound.getActiveProjectils()); // lance NotMatchException
        }
        for (Projectil p : currentRound.getActiveProjectils()) {
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
        for (Balloon b : currentRound.getBalloons()) {
            if (!b.isActive() && b.getHealth() <= 0) {
                this.player.setCredit(this.player.getCredit() + 10);
            }
        }

        // Retirer les projectiles et ballons inactifs
        currentRound.getActiveProjectils().removeIf(p -> !p.isActive());
        currentRound.getBalloons().removeIf(b -> !b.isActive() && b.getHealth() <= 0);
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

    /**
     * Methode principale pour l'affichage
     */
    private void render() {
        printBoard(this.currentRound.getBoard());
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
        for (Tower t : player.getTowers()) {
            if (t.getPosition().getRow() == r && t.getPosition().getCol() == c) {
                return t.getSymbol();
            }
        }
        for (Balloon b : currentRound.getBalloons()) {
            if ((int) b.getLigne() == r && (int) b.getColonne() == c) {
                return "?";
            }
        }

        return null;
    }

    /**
     * methode pour affciher exactement la grille DefinedBoard
     * 
     * @param board le board de type Defined
     */
    public void printBoard(Board board) {
        for (int r = 0; r < board.getHeight(); r++) {
            for (int c = 0; c < board.getWidth(); c++) {

                String entity = checkEntityAt(r, c);
                if (entity != null) {
                    System.out.print(entity + " ");
                    continue;
                }

                char symbol = ' ';
                boolean special = false;

                for (List<Cell> path : board.getAllPath().values()) {
                    if (path == null || path.isEmpty()) continue;

                    Position start = path.get(0).getPosition();
                    Position end = path.get(path.size() - 1).getPosition();

                    if (r == start.getRow() && c == start.getCol()) {
                        symbol = 'D';
                        special = true;
                        break;
                    }
                    if (r == end.getRow() && c == end.getCol()) {
                        symbol = 'F';
                        special = true;
                        break;
                    }
                }

                if (!special) {
                    if (board.getGrid()[r][c].isPath()) {
                        symbol = 'c';
                    } else {
                        symbol = '.';
                    }
                }

                System.out.print(symbol + " ");
            }
            System.out.println();
        }
    }

    public void play(Board board, Round round, int Mode){
        Scanner scan = new Scanner(System.in);
        Random rand = new Random();
        System.out.println("=== TOWER DEFENSE ===");
        board.init();
        board.calculerPath();
        System.out.println("Plateau de hauteur " + this.currentRound.getBoard().getHeight()+ " et largeur " + this.currentRound.getBoard().getWidth());
        while(true){
            System.out.println("--- MENU ---");
            System.out.println("1 : Commencer le round ");
            System.out.println("2 : Placer une tour");
            System.out.println("3 : Améliorer / Vendre évolution d'une tour");
            System.out.println("4 : Quitter");
            int n = Integer.parseInt(scan.nextLine().trim());
            if(n == 4){
                System.out.println("Merci de jouer!");
                scan.close();
                return;
            }
            if(n == 1){
                System.out.println("=== NEW ROUND ===");
                for(int i = 0; i < NBBALLOONS; i++){
                    BalloonType type = BalloonType.values()[rand.nextInt(BalloonType.values().length)];
                    this.currentRound.spawnBalloon(type);
                    System.out.println("Balloon " + (i + 1) + " | type = " + String.valueOf(((Balloon)this.currentRound.getBalloons().get(i)).getType()) + " | speed = " + ((Balloon)this.currentRound.getBalloons().get(i)).getSpeed() + " | health = " + ((Balloon)this.balloons.get(i)).getHealth());
                }
                int i = 0;

                for(short max = MAXTIC; (!this.currentRound.getBalloons().isEmpty() || !this.currentRound.getActiveProjectils().isEmpty()) && i < max; ++i) {
                    System.out.println(" Le tick numero " + i);
                    this.gameTick();
                }

                System.out.println("=== ROUND FINISHED ===");
                System.out.println("Ticks joues = " + i);
            }
            if(n == 2){
                System.out.println("Veuillez choisir la tour :");
                System.out.println("1 : Bomb Tower");
                System.out.println("2 : Dart Monkey");
                System.out.println("3 : Elite Sniper");
                System.out.println("4 : Gorilla");
                System.out.println("5 : Ice Tower");
                System.out.println("6 : Slow Tower");
                System.out.println("7 : Tack Shooter");
                int n = Integer.parseInt(scan.nextLine().trim());
                System.out.println("--Veuillez entrer la position--");
                System.out.print("Colonne : ");
                int c = Integer.parseInt(scan.nextLine().trim());
                System.out.print("Ligne : ");
                int l = Integer.parseInt(scan.nextLine().trim());

                player.buyTower(n,);
            }
        }

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

    //===== GETTERS =====
    public Board getBoard(){
        return this.board;
    }
}