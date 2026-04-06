package choose.action;

import game.GameContext;
import object.tower.Tower;

public class SellTower extends Action {

    public SellTower(Tower tower) {
        super(tower);
    }

    @Override
    public void execute(GameContext game) {
        Tower tower = getTower();
        if (tower == null) {
            return;
        }
        int refund = tower.getSellPrice();
        game.getBoard().removeTower(tower);
        game.getPlayer().removeTower(tower);
        game.getPlayer().addCredit(refund);
    }

    @Override
    public String getDescription() {
        Tower tower = getTower();
        String name = (tower == null) ? "tour" : tower.getName();
        return "Vendre " + name;
    }
}