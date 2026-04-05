package choose.action;

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

    /**
     * Methode pour prendre la position ou on va acheter + placer le tour
     * @return position du tour
     */
    public Position getPosition() {
        return this.p;
    }

    /**
     * Action de cette classe, ici c'est acheter un tower et le placer dans la position
     * Puisque lors qu'on cree un nouveau tower, on lui a deja donne
     * la position, donc on a pas besoin de faire qqc ici
     */
    @Override
    public void doAction() {
        if ( this.getTower() == null || this.p == null) {
            return;
        }
    }
}