package object.tower.evolution;

import object.tower.Tower;

import java.util.ArrayList;

public class Evolution {

    private ArrayList<TypeEvolution> typeEvolutions;
    private ArrayList<TypeEvolution> evolutionsPossedees;


    public Evolution() {
        this.typeEvolutions = new ArrayList<>();
        this.evolutionsPossedees = new ArrayList<>();
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

    public ArrayList<TypeEvolution> getEvolutionsPossedees() {
        return evolutionsPossedees;
    }
}
