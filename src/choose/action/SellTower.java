package choose.action;

import entity.tower.Tower;
import game.GameContext;

public class SellTower extends Action {

    /**
     * constructeur
     * @param tower la tour a vendre
     */
    public SellTower(Tower tower) {
        super(tower);
    }

    /**
     * methode pour vendre une tour
     * @param game le contexte du jeu
     */
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

    /**
     * methode pour obtenir la description de l'action
     * @return description de l'action
     */
    @Override
    public String getDescription() {
        Tower tower = getTower();
        String name = (tower == null) ? "tour" : tower.getName();
        return "Vendre " + name;
    }
}