package choose.action;

import java.util.ArrayList;

import game.Action;
import object.tower.Tower;
import object.tower.evolution.TypeEvolution;

public class Upgrade extends Action {

    /**
     * Les attributs de la classe
     * Le type d'evolution que le joueur veut appliquer
     */
    private TypeEvolution typeEvo;
    /**
     * Constructeur de Upgrade
     * @param t le tower a upgrade
     * @param typeEvo le type d'evo voulu
     */
    public Upgrade(Tower t, TypeEvolution typeEvo)  {
        super(t);
        this.typeEvo = typeEvo;
    }

    /**
     * Methode gerer l'action du joueur, ici faire un upgrade
     * pour un tower
     */
    @Override
    public void doAction() {
        if (this.t == null || !this.t.getEvolution() || this.t.getEvolutions() == null) {
            return;
        }
        if (this.typeEvo == null) {
            ArrayList<TypeEvolution> evo = this.t.getEvolutions().getEvolutions();
            if (evo == null || evo.isEmpty()) {
                return;
            }
            this.typeEvo = evo.get(0);
        }
        int cout = this.t.appliquerEvolution(this.typeEvo);
        this.setCout(cout);
    }
}

