package choose.action;


import board.Position;
import game.GameContext;
import player.Player;
import entity.tower.Tower;


/**
 * buy tower action
 */
public class BuyTowerAction extends Action {
    /**
     * tower to buy
     * position to place it
     */
    private Tower tower;
    private Position pos;

    /**
     * constructor
     * @param t the tower to buy
     * @param p the position to place it
     */
    public BuyTowerAction(Tower t, Position p) {
        this.tower = t;
        this.pos = p;
    }

    @Override
    /**
     * execute the action: buy and place the tower
     * @param game the game context
     */
    public void execute(GameContext game) {
        Player player = game.getPlayer();

        if (player.getCredit() >= tower.getCost()) {
            player.buyTower(this.tower);
            game.getBoard().placeTower(this.tower, this.pos);
        }
    }

    @Override
    /**
     * description of the action
     * @return text description
     */
    public String getDescription() {
        return "buy " + tower.getName() + " (" + tower.getCost() + " credits)";
    }
}