package choose.action;

import game.GameContext;
import player.Player;
import entity.tower.Tower;

public class SellTowerAction extends Action {
    private Tower tower;

    public SellTowerAction(Tower tower) {
        this.tower = tower;
    }

    @Override
    public void execute(GameContext game) {
        Player player = game.getPlayer();
        player.sellTower(tower);
        game.getBoard().removeTower(tower);
    }

    @Override
    public String getDescription() {
        return "Vendre " + tower.getName();
    }
}
