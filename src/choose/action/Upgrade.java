package choose.action;

import game.GameContext;
import entity.tower.Tower;
import entity.tower.evolution.TypeEvolution;

public class Upgrade extends Action {
    /**
     * Les attributs
     * Un tower a evolue
     * Le type d'evoluer
     */
    private Tower tower;
    private TypeEvolution typeEvo;

    /**
     * Constructeur
     * @param t le tower a evoluer
     * @param typeEvo le type d'evolution
     */
    public Upgrade(Tower t, TypeEvolution typeEvo) {
        this.tower = t;
        this.typeEvo = typeEvo;
    }

    @Override
    /**
     * Execute l'action : appliquer l'evolution sur le tower et diminue un nombre de credit du joueur
     * @param game le contexte du jeu
     */
    public void execute(GameContext game) {
        if (tower != null) {
            int cost = tower.appliquerEvolution(this.typeEvo);
            game.getPlayer().removeCredit(cost);
        }
    }

    @Override
    /**
     * La description de l'action
     * @return la description
     */
    public String getDescription() {
        return  this.typeEvo.name() + " (" + this.tower.getCoutEvolution(this.typeEvo) + ")";
    }
}