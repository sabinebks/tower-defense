package game.action;

import game.Action;
import object.tower.Tower;

public class Vendre extends Action{

    /**
     * Constructeur de Vendre
     * @param t le tower a vendre
     */
    public Vendre(Tower t ) {
        super(t);
    }
}
