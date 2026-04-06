package choose.action;

import board.Position;
import game.GameContext;
import object.tower.Tower;
import player.Shop;

public class BuyTower extends Action {

    private Shop shop;

    public BuyTower(Tower tower, Shop shop) {
        super(tower);
        this.shop = shop;
    }

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

    @Override
    public String getDescription() {
        Tower tower = getTower();
        String name = (tower == null) ? "tour" : tower.getName();
        return "Acheter " + name;
    }
}