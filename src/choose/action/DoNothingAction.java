package choose.action;

import game.GameContext;

public class DoNothingAction extends Action{

    @Override
    public void execute(GameContext game){
    }

    @Override
    public String getDescription(){
        return "Ne rien faire";
    }
}
