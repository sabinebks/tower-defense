package choose.action;

import board.Position;
import entity.tower.Tower;
import game.GameContext;
import player.Shop;

public class BuyTower extends Action {

    private Shop shop; // pour trouver une position disponible

    /**
     * constructeur
     * @param tower la tour à acheter
     * @param shop le shop pour trouver une position disponible
     */
    public BuyTower(Tower tower, Shop shop) {
        super(tower);
        this.shop = shop;
    }

    /**
     * methode pour acheter une tour
     * @param game le contexte du jeu
     */
    @Override
    public void execute(GameContext game) {
        Tower tower = getTower();
        if (tower == null) {
            return;
        }
        int cost = tower.getCost();
        if (game.getPlayer().getCredit() < cost) {
            System.out.println("Pas assez de crédits pour acheter cette tour.");
            return;
        }
        Position position = shop.findAvailablePosition();
        if (position == null) {
            System.out.println("Aucune position disponible.");
            return;
        }
        tower.setPosition(position);
        game.getBoard().placeTower(tower, position);
        game.getPlayer().addTower(tower);
        game.getPlayer().removeCredit(cost);
    }

    /**
     * methode pour obtenir la description de l'action
     * @return description de l'action
     */
    @Override
    public String getDescription() {
        Tower tower = getTower();
        String name = (tower == null) ? "tour" : tower.getName();
        return "Acheter " + name;
    }
}