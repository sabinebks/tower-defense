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
     * retourne le symbole de l'entite a la position donnee pour l'affichage
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
        if ((int)b.getLigne() == r && (int)b.getColonne() == c) {
            return "?";
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
     * methode pour gerer les evolutions des tours de maniere aleatoire
     * parcourt toutes les tours et applique ou supprime une evolution par type de tour
     * @param ajouter true pour ajouter une evolution, false pour en supprimer une
     */
    private void gererEvolutionAleatoire(boolean ajouter) {
        if (towers.isEmpty()) return;
        Random rand = new Random();
        Set<String> dejTraite = new HashSet<>();
        for (Tower t : towers) {
            if (t.getEvolutions() == null) continue;
            if (dejTraite.contains(t.getSymbol())) continue;
            if (ajouter) {
                ArrayList<TypeEvolution> evols = t.getEvolutions().getEvolutions();
                if (evols == null || evols.isEmpty()) continue;
                
                HashMap<TypeEvolution, Integer> possedees = t.getEvolutions().getEvolutionsPossedees();
                List<TypeEvolution> possibles = new ArrayList<>();
                
                for (TypeEvolution e : evols) {
                    if (!possedees.containsKey(e)) possibles.add(e);
                }
                
                if (possibles.isEmpty()) continue;
                TypeEvolution evo = possibles.get(rand.nextInt(possibles.size()));
                int cout = t.appliquerEvolution(evo);
                if (cout > 0) {
                    System.out.println("Evolution ajoutée : " + evo + " sur " + t.getSymbol());
                    dejTraite.add(t.getSymbol());
                }
            } else {
                HashMap<TypeEvolution, Integer> possedees = t.getEvolutions().getEvolutionsPossedees();
                if (possedees == null || possedees.isEmpty()) continue;
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
     * @param height hauteur du plateau
     * @param width largeur du plateau
     * @param param nombre de ballons (modeA) ou nombre de chemins (modeB)
     * @param modeA true pour DefinedBoard, false pour FreeBoard
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
                autoPlayA(height, width, param);   // param = nbBallons
            } else {
                autoPlayB(height, width, param);   // param = nbChemins
            }
            balloons.clear();
            activeProjectils.clear();
        }
    }

    /**
     * methode pour afficher les evenements des ballons a un tick donne
     * affiche : TOUCHE, DETRUIT, SORTI, RALENTI, ARRETE, REDEMARRE
     * @param tick le numero du tick actuel
     * @param oldSpeed les vitesses des ballons avant le tick
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
     * @param height hauteur du plateau
     * @param width largeur du plateau
     * @param nbBallons nombre de ballons à générer
     */



    public void autoPlayA(int height, int width, int nbBallons) {

    //  recréer le board à chaque manche pour éviter les incohérences
    this.board = new DefinedBoard(height, width);

    if (towers.isEmpty()) {
       placerToursAleatoires();
    }

    Random rand = new Random();

    for (int i = 0; i < nbBallons; i++) {
        BalloonType type = BalloonType.values()[
                rand.nextInt(BalloonType.values().length)
        ];
        spawnBalloon(type);
    }

    int tick = 0;
    int max = 10000;
    Map<Integer, Double> oldSpeed = new HashMap<>();

    render();
    while (!(balloons.isEmpty() && activeProjectils.isEmpty()) && tick < max) {

        oldSpeed.clear();
        Map<Integer, Integer> oldHealth = new HashMap<>();

        for (Balloon b : balloons) {
            oldSpeed.put(b.getId(), b.getEffectiveSpeed());
            oldHealth.put(b.getId(), b.getHealth());
        }

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
     * @param height hauteur du plateau
     * @param width largeur du plateau
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
                Position end = chemin[1];           

                BalloonStraight b = new BalloonStraight(
                    ++idCounter,
                    start,    
                    end,
                    BalloonType.values()[rand.nextInt(BalloonType.values().length)]
                );

                balloons.add(b);
            }
        }

        int tick = 0;
        Map<Integer, Double> oldSpeed = new HashMap<>();

        render();

        while (!(balloons.isEmpty() && activeProjectils.isEmpty())) {

            oldSpeed.clear();
            Map<Integer, Integer> oldHealth = new HashMap<>();

            for (Balloon b : balloons) {
                oldSpeed.put(b.getId(), b.getEffectiveSpeed());
                oldHealth.put(b.getId(), b.getHealth());
            }

            gameTick();

            afficherEvenementsBallons(tick, oldSpeed, oldHealth);

            // (pas modifié même si redondant volontairement)

            tick++;
        }

        System.out.println("=== FIN DE MANCHE ===");
    }
/** 
 * Cree une tour en fonction d'un id
 * @param typeId l'identifiant du type de tour (1 à 7)
 * @param pos la position de la tour
 * @return la tour correspondante ou null si invalide
 */
private Tower createTower(int typeId, Position pos) {
    switch (typeId) {
        case 1: return new BombTower(pos);
        case 2: return new DartMonkey(pos);
        case 3: return new EliteSniper(pos);
        case 4: return new Gorilla(pos);
        case 5: return new IceTower(pos);
        case 6: return new SlowTower(pos);
        case 7: return new TackShooter(pos);
        default: return null;
    }
}
private void placerToursAleatoires() {

    // FIX : éviter crash si board null
    if (board == null) return;

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
            if (nonPathPositions.isEmpty()) break;

            Position pos = nonPathPositions.remove(
                rand.nextInt(nonPathPositions.size())
            );

            Tower tower = createTower(t, pos);

            if (tower != null) {
                addTower(tower);
            }
        }
    }
}


}