package game.action;

import game.Action;
import object.tower.Tower;

public class Downgrade extends Action{

    /**
     * Constructeur de Downgrade
     * @param t le tower a downgrade
     */
    public Downgrade(Tower t) {
        super(t);
    }
}
