package game.action;

import game.Action;
import object.tower.Tower;

public class Upgrade extends Action {

    /**
     * Constructeur de Upgrade
     * @param t le tower a upgrade
     */
    public Upgrade(Tower t) {
        super(t);
    }
}
