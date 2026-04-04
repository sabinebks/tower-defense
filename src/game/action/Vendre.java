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

    /**
     * Methode gerer la vente d'un tower
     * On utilise la methode calcul pour faciliter la somme total
     * des credits qui va etre rembouser au joueur
     */
    @Override
    public void doAction() {
        if (this.t == null) {
            return;
        }
        int n = 0 - (calculInvestissementTotal(this.t) / 2);
        this.setCout(n);
    }
}
