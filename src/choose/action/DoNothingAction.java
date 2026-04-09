package choose.action;

import game.GameContext;

public class DoNothingAction extends Action{

    /**
     * Methode de ne rien faire
     */
    @Override
    public void execute(GameContext game){
    }

    /**
     * La description de l'action
     * @return la description
     */
    @Override
    public String getDescription(){
        return "Ne rien faire";
    }
}
