package choose.action;

import board.Board;
import board.Position;
import game.GameContext;
import player.Player;
import entity.tower.Tower;
import player.Shop;

public class BuyTowerAction extends Action{
    private Tower tower;
    private Position position;

    public BuyTowerAction(Tower t, Position p){
        this.tower = t;
        this.position = p;
    }

    @Override
    public void execute(GameContext game){

        Player player = game.getPlayer();
        if(player.getCredit() >= tower.getCost()){
            player.buyTower(this.tower);
            game.getBoard().placeTower(this.tower, this.position);
        }
    }

    @Override
    public String getDescription(){
        return "Acheter " + tower.getName() + " (" + tower.getCost() + "crédits)";

    }
}
