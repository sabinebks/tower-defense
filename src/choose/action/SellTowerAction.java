package choose.action;

import game.GameContext;
import player.Player;
import entity.tower.Tower;
/**
 * sell tower action
 */
public class SellTowerAction extends Action {

    /**
     * tower to sell
     */
    private Tower tower;

    /**
     * constructor
     * @param tower the tower to sell
     */
    public SellTowerAction(Tower tower) {
        this.tower = tower;
    }

    @Override
    /**
     * execute sell tower
     * @param game the game context
     */
    public void execute(GameContext game) {
        Player player = game.getPlayer();
        player.sellTower(tower);
        game.getBoard().removeTower(tower);
    }

    @Override
    /**
     * description
     * @return description
     */
    public String getDescription() {
        return "vendre " + tower.getName();
    }
}