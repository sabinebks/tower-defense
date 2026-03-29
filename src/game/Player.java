package game;
import board.Board;
import board.Cell;
import board.Position;
import object.tower.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Une class player pour gerer les actions du joueurs
 */
public class Player {
    /**
     * Les attributs du joueurs
     * Le nombre de cerdit, init en 2500
     * Le nombre de vie, init en 20
     * Le joueurs va gerer avec les towers dans game, donc il a pas besoin d'un liste de towers
     */
    public static final int CREDIT_INITIAL = 2500;
    public static final int VIE_INIT = 20;
    public static final double REVENDRE = 0.25;

    private int credit;
    private int vie;
    private List<Tower> towers;
    /** Le constructeur du joueur, init le credit et la vie */
    public Player () {
        this.credit = CREDIT_INITIAL;
        this.vie = VIE_INIT;
        this.towers = new ArrayList<>();
    }

    /**
     * Methode pour acheter une tour
     * Verifie si le joueur a assez de credit pour acheter la tour
     * Si oui, le credit du joueur va diminuer du cout de la tour
     * @param t la tour a acheter
     * @return true si l'achat est reussi, false sinon
     */
    public int calculInvestissementTotal(Tower t) {
        int total = t.getCout();
        if (t.getEvolutions() != null) {
            for (int c : t.getEvolutions().getEvolutionsPossedees().values()) {
                total += c;
            }
        }
        return total;
    }

    public void buyTower(int t, Position p) {
        Tower tow = createTower(t, new Position(0, 0));
        if (tow == null) {
            System.out.println("Type de tour invalide");
            return;
        }
        if (this.credit < tow.getCout()) {
            System.out.println("Credit insuffisant");
            return;
        }

        if (p == null ) {
            System.out.println("Position invalide");
            return;
        }
        Tower tower = createTower(t, p);
        addTower(tower);
        credit -= tower.getCout();
    }

    /**
     * Place des tours aléatoirement sur le plateau, en évitant les chemins.
     * Place jusqu'à 2 tours de chaque type (1 à 7) si possible.
     */
    private void randomTower(Board board) {

        // FIX : éviter crash si board null
        if ( board == null)
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

    //===== GETTERS =====
    public List<Tower> getTowers(){
        return towers;
    }


    /**
     * ajouter une tower t
     * @param t
     */
    public void addTower(Tower t) {
        towers.add(t);
    }


    /**
     * Retourner le nombre de credit du joueur
     * @return nombre de credit
     */
    public int getCredit() {
        return this.credit;
    }

    /**
     * Retourner le nombre de vie du joueur
     * @return nombre de vie
     */
    public int getVie() {
        return this.vie;
    }



    //===== SETTERS =====
    public void setCredit(int n){
        this.credit = n;
    }

}
