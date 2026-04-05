package choose;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import board.Position;
import object.tower.evolution.TypeEvolution;

/**
 * Version automatique de list chooser
 */
public class AutoListChooser implements ListChooser {
    /**
     * Les attributs de la classe
     * un random pour choisir aleaatoirement les choix
     */
    private Random random;
    
    /**
     * Constructeur de cette classe
     */
    public AutoListChooser() {
        this.random = new Random();
    }

    /**
     * Methode pour choisir les actions aleatoirement
     * @param game le game on jouait
     * @param player le joueur
     */
    @Override
    public List<Action> choisirActions(Game game, Player player) {
        List<Action> laction = new ArrayList<>();
        int nbdeactions = random.nextInt(4); 
        for (int i = 0; i < nbdeactions; i++) {
            int n = random.nextInt(4) + 1;
            switch (n) {
                case 1:
                    Action achat = achatAuto(game);
                    if (achat != null) {
                        laction.add(achat);
                    }
                    break;
                case 2:
                    Action upgrade = upgradeAuto(game);
                    if (upgrade != null) {
                        laction.add(upgrade);
                    }
                    break;
                case 3:
                    Action downgrade = downgradeAuto(game);
                    if (downgrade != null) laction.add(downgrade);
                    break;
                case 4:
                    Action vente = venteAuto(game);
                    if (vente != null) {
                        laction.add(vente);
                    }
                    break;
                default:
                    break;
            }
        }
        return laction;
    }

    /**
     * Methode pour acheter automatiquement
     * @param game le jeu on jouait
     * @return l'action d'acheter
     */
    public Action achatAuto(Game game) {
        Position p = posValidAlea(game);
        if (p == null) {
            return null;
        }
        int type = random.nextInt(7) + 1;
        Tower t = game.createTower(type, p);
        if (t == null) {
            return null;
        }
        return new Acheter(t, p);
    }

    /**
     * Methode pour faire un upgrade aleatoirement
     * @param game le jeu on jouait
     * @return l'action d'upgrade
     */
    public Action upgradeAuto(Game game) {
        List<Tower> t = game.getTowers();
        List<Tower> tour = new ArrayList<>();
        for (Tower tow : t) {
            if (tow.getEvolution() && tow.getEvolutions() != null && !tow.getEvolutions().getEvolutions().isEmpty()) {
                tour.add(tow);
            }
        }
        if (tour.isEmpty()) {
            return null;
        }
        Tower tower = tour.get(random.nextInt(tour.size()));
        ArrayList<TypeEvolution> e = tower.getEvolutions().getEvolutions();
        TypeEvolution res = e.get(random.nextInt(e.size()));
        return new Upgrade(tower, res);
    }

    /**
     * Methode pour vendre automatiquement un tower 
     * @param game le game on jouait
     * @return l'action vendre
     */
    public Action venteAuto(Game game) {
        List<Tower> t = game.getTowers();
        if (t.isEmpty()) {
            return null;
        }
        Tower res = t.get(random.nextInt(t.size()));
        return new Vendre(res);
    }
    /**
     * Methode pour faire un downgrade automatiquement d'un tower
     * @param game le jeu on jouait
     * @return l'action de downgrade
     */
    public Action downgradeAuto(Game game) {
        List<Tower> t = game.getTowers();

        List<Tower> tour = new ArrayList<>();
        for (Tower tow : t) {
            if (tow.getEvolution() && tow.getEvolutions() != null && !tow.getEvolutions().getEvolutionsPossedees().isEmpty()) {
                tour.add(tow);
            }
        }
        if (tour.isEmpty()) {
            return null;
        }
        Tower tower = tour.get(random.nextInt(tour.size()));
        ArrayList<TypeEvolution> possedees = new ArrayList<>(tower.getEvolutions().getEvolutionsPossedees().keySet());
        TypeEvolution res = possedees.get(random.nextInt(possedees.size()));
        return new Downgrade(tower, res);
    }
    /**
     * Methode pour creer une position valide dans le board aleatoirement
     * @param game le game on jouait    
     * @return la position valide alea
     */
    public Position posValidAlea(Game game) {
        for (int i = 0; i < 100; i++) {
            int row = random.nextInt(game.getBoard().getHeight());
            int col = random.nextInt(game.getBoard().getWidth());
            Position p = new Position(row, col);
            if (game.posValide(p)) {
                return p;
            }
        }
        return null;
    }
}
