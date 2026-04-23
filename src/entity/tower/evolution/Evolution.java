package entity.tower.evolution;

import java.util.ArrayList;
import java.util.HashMap;

public class Evolution {
    /**
     * les attributs
     * typeEvolutions : liste pour sauvgarder les evolution de chaque tour
     * evolutionsPossedees : hashmap<typeEvolution,Integer> pour sauvgarder les evolution acheter pour les vendre apres
     */
    private ArrayList<TypeEvolution> typeEvolutions;
    private HashMap<TypeEvolution, Integer> evolutionsPossedees;

    /**
     * constructeur :
     * cree un arrayList pour les type d'evolution
     * cree un hashmap pour les evolution possédées
     */
    public Evolution() {
        this.typeEvolutions = new ArrayList<>();
        this.evolutionsPossedees = new HashMap<TypeEvolution, Integer>();
    }

    /**
     * methode pour ajouter un typeEvolution
     * @param typeEvolution
     */
    public void addEvolution(TypeEvolution typeEvolution) {
        this.typeEvolutions.add(typeEvolution);
    }

    /**
     * methode pour supprimer un typeEvolution
     * @param typeEvolutions
     */
    public void deleteEvolutions(TypeEvolution typeEvolutions) {
        this.typeEvolutions.remove(typeEvolutions);
    }

    /**
     * getter pour le arrayList typeEvolution
     * @return
     */
    public ArrayList<TypeEvolution> getEvolutions() {
        return typeEvolutions;
    }

    /**
     * methode pour vendre (supprimer) un TypeEvolution dans hashMap evolutionPossedees
     * @param typeEvolution
     */
    public void vendreEvolution(TypeEvolution typeEvolution) {
        this.evolutionsPossedees.remove(typeEvolution);
    }

    /**
     * getter pour l'attribut evolutionPossedees
     * @return
     */
    public HashMap<TypeEvolution, Integer> getEvolutionsPossedees() {
        return evolutionsPossedees;
    }
}
