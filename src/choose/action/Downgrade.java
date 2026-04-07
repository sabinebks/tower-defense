package choose.action;

import java.util.ArrayList;
import java.util.HashMap;

import entity.tower.Tower;
import entity.tower.evolution.TypeEvolution;
import game.Action;

public class Downgrade extends Action{


    /**
     * Les attributs de la classe
     * @param typeEvo la type d'evolution a vendre
     */
    private TypeEvolution typeEvo;

    /**
     * Constructeur de Downgrade
     * @param t le tower a downgrade
     * @param typeEvo le type d'evolution a vendre
     */
    public Downgrade(Tower t, TypeEvolution typeEvo) {
        super(t);
        this.typeEvo = typeEvo;
    }

    /**
     * Methode gerer la vendre d'une type evolution
     */
    @Override
    public void doAction() {
        if (this.t == null || !this.t.getEvolution() || this.t.getEvolutions() == null) {
            return;
        }
        HashMap<TypeEvolution, Integer> p = this.t.getEvolutions().getEvolutionsPossedees();
        if (p == null || p.isEmpty()) {
            return;
        }
        if (this.typeEvo == null) {
            ArrayList<TypeEvolution> l = new ArrayList<>(p.keySet());
            this.typeEvo = l.get(0);
        }
        double n = 0 - (p.get(this.typeEvo) * 0.75);
        this.t.getEvolutions().vendreEvolution(this.typeEvo);
        this.t.getEvolutions().addEvolution(this.typeEvo);
        this.setCout((int)n);
    }
}
