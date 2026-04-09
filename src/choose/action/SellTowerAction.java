package choose.action;

import game.GameContext;
import player.Player;
import entity.tower.Tower;

public class SellTowerAction extends Action {
    /**
     * Les attributs 
     * le tower a vendu
     */
    private Tower tower;

    /**
     * Constructeur 
     * @param tower le tower voulu vendre
     */
    public SellTowerAction(Tower tower) {
        this.tower = tower;
    }

    @Override
    /**
     * Excecute l'action : vendre le tower
     * @param game le context du jeu
     */
    public void execute(GameContext game) {
        Player player = game.getPlayer();
        player.sellTower(tower);
        game.getBoard().removeTower(tower);
    }

    @Override
    /**
     * La description du jeu
     * @return la description
     */
    public String getDescription() {
        return "Vendre " + tower.getName();
    }
}
