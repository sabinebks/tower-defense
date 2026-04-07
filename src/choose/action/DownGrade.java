package choose.action;

import game.GameContext;
import entity.tower.Tower;
import entity.tower.evolution.TypeEvolution;

public class DownGrade extends Action{
    private Tower tower;
    private TypeEvolution evo;

    public DownGrade(Tower tower, TypeEvolution evo){
        this.tower = tower;
        this.evo = evo;
    }

    @Override
    public void execute(GameContext game){;
        if(game.getPlayer().getTowers().contains(this.tower)){
            int index = game.getPlayer().getTowers().indexOf(this.tower);
            if(game.getPlayer().getTowers().get(index).getEvolutions().getEvolutionsPossedees().containsKey(evo)) {
                int cost = game.getPlayer().getTowers().get(index).SellEvolution(evo);
                game.getPlayer().addCredit(cost);
            }
        }
    }

    @Override
    public String getDescription(){
        return  this.evo.name() + " (" + this.tower.getCoutEvolution(this.evo) + ")";
    }
}
