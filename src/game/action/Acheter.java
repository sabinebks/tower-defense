package game.action;

import game.*;
import object.tower.*;
import board.*;

public class Acheter extends Action {

    /**
     * Les attributs de Acheter
     * Une position a placer le tower
     */
    private Position p;
    /**
     * Constructeur de Acheter
     * @param t le tower veuiller a acheter
     * @param p la position a placer le tower
     */
    public Acheter(Tower t, Position p) {
        super(t);
        this.p = p;
    }

    public void doAction() {

    }
}
