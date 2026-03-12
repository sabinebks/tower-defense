package object.tower.evolution;

import object.tower.Tower;

import java.util.ArrayList;
import java.util.HashMap;

public class Evolution {

    private ArrayList<TypeEvolution> typeEvolutions;
    private HashMap<TypeEvolution, Integer> evolutionsPossedees;


    public Evolution() {
        this.typeEvolutions = new ArrayList<>();
        this.evolutionsPossedees = new HashMap<TypeEvolution, Integer>();
    }

    public void addEvolution(TypeEvolution typeEvolution) {
        this.typeEvolutions.add(typeEvolution);
    }

    public void deleteEvolutions(TypeEvolution typeEvolutions) {
        this.typeEvolutions.remove(typeEvolutions);
    }

    public ArrayList<TypeEvolution> getEvolutions() {
        return typeEvolutions;
    }

    public void vendreEvolution(TypeEvolution typeEvolution) {
        this.evolutionsPossedees.remove(typeEvolution);
    }

    public HashMap<TypeEvolution, Integer> getEvolutionsPossedees() {
        return evolutionsPossedees;
    }
}
