package choose.action;

import board.Board;
import board.Position;
import game.GameContext;
import player.Player;
import entity.tower.Tower;
import player.Shop;

public class BuyTowerAction extends Action{
    /**
     * Les attributs de buy tower
     * Un tower
     * Une position a placer
     */
    private Tower tower;
    private Position pos;

    /**
     * Le constructeur
     * @param t le tower voulu acheter
     * @param p la pos voulu placer
     */
    public BuyTowerAction(Tower t, Position p){
        this.tower = t;
        this.pos = p;
    }

    @Override
    /**
     * Executer l'action : acheter le tower et le place dans la pos voulu
     * @param game le game context
     */
    public void execute(GameContext game){

        Player player = game.getPlayer();
        if(player.getCredit() >= tower.getCost()){
            player.buyTower(this.tower);
            game.getBoard().placeTower(this.tower, this.pos);
        }
    }

    @Override
    /**
     * La description de l'action
     * @return un string de description
     */
    public String getDescription(){
        return "Acheter " + tower.getName() + " (" + tower.getCost() + "crédits)";

    }
}
