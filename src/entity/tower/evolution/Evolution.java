package entity.tower.evolution;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * evolution class
 */
public class Evolution {

    /**
     * list of evolutions
     * owned evolutions map
     */
    private ArrayList<TypeEvolution> typeEvolutions;
    private HashMap<TypeEvolution, Integer> evolutionsPossedees;

    /**
     * constructor
     */
    public Evolution() {
        this.typeEvolutions = new ArrayList<>();
        this.evolutionsPossedees = new HashMap<TypeEvolution, Integer>();
    }

    /**
     * add evolution type
     * @param typeEvolution evolution type
     */
    public void addEvolution(TypeEvolution typeEvolution) {
        this.typeEvolutions.add(typeEvolution);
    }

    /**
     * delete evolution type
     * @param typeEvolution evolution type
     */
    public void deleteEvolutions(TypeEvolution typeEvolution) {
        this.typeEvolutions.remove(typeEvolution);
    }

    /**
     * get evolutions list
     * @return list of evolutions
     */
    public ArrayList<TypeEvolution> getEvolutions() {
        return typeEvolutions;
    }

    /**
     * sell evolution
     * @param typeEvolution evolution type
     */
    public void vendreEvolution(TypeEvolution typeEvolution) {
        this.evolutionsPossedees.remove(typeEvolution);
    }

    /**
     * get owned evolutions
     * @return map of evolutions
     */
    public HashMap<TypeEvolution, Integer> getEvolutionsPossedees() {
        return evolutionsPossedees;
    }
}