package choose.action;

import board.Board;
import board.Position;
import game.GameContext;
import player.Player;
import entity.tower.Tower;
import choose.ListChooser;

import java.util.ArrayList;
import java.util.List;

public class BuyTowerAction extends Action {

    /**
     * Les attributs de BuyTowerAction
     * La tour à acheter
     * Le board pour trouver les positions disponibles
     * Le chooser de position (humain ou auto)
     */
    private final Tower tower;
    private final Board board;
    private final ListChooser<Position> positionChooser;

    /**
     * Constructeur
     * @param t               la tour à acheter
     * @param board           le plateau de jeu
     * @param positionChooser le chooser de position
     */
    public BuyTowerAction(Tower t, Board board, ListChooser<Position> positionChooser) {
        this.tower = t;
        this.board = board;
        this.positionChooser = positionChooser;
    }

    @Override
    /**
     * Exécute l'action : demande une position disponible au joueur puis achète et place la tour.
     * Si aucune position n'est disponible, annule l'achat.
     * @param game le game context
     */
    public void execute(GameContext game) {
        Player player = game.getPlayer();
        if (player.getCredit() < tower.getCost()) {
            System.out.println("Crédits insuffisants pour acheter " + tower.getName() + ".");
            return;
        }

        List<Position> available = new ArrayList<>();
        for (int i = 0; i < board.getHeight(); i++) {
            for (int j = 0; j < board.getWidth(); j++) {
                if (board.isCellAvailable(i, j)) {
                    available.add(new Position(i, j));
                }
            }
        }

        if (available.isEmpty()) {
            System.out.println("Aucune position disponible sur le plateau !");
            return;
        }

        Position chosen = positionChooser.choose(
                "Choisissez une position pour " + tower.getName() + " :", available);

        if (chosen == null) {
            System.out.println("Achat annulé.");
            return;
        }

        player.buyTower(tower);
        game.getBoard().placeTower(tower, chosen);
        System.out.println(tower.getName() + " placé en " + chosen + ".");
    }

    @Override
    /**
     * La description de l'action
     * @return un string de description
     */
    public String getDescription() {
        return "Acheter " + tower.getName() + " (" + tower.getCost() + " crédits)";
    }
}

