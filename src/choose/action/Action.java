package choose.action;

/**
 * Classe abstract des actions 
 */
public abstract class Action {
    
    /**
     * Attributs
     * un int pour gerer le cout total d'une action
     */
    protected int c;
    protected Tower t;
    /**
     * Constructeur de la classes
     */
    public Action(Tower t) {
        this.c = 0;
        this.t = t;
    }

    /**
     * Methode rendre le nombre de credit de l'action
     * @return le nombre de credit
     */
    public int getCout() {
        return this.c;
    }

    /**
     * Methode pour modifier le credit total d'un tower, les upgrades
     * @param c un nouveau prix
    */
    public void setCout(int c) {
        this.c = c;
    }
    /**
     * Methode retourner le tower de l'action
     * @return le tower de l'action
     */
    public Tower getTower() {
        return this.t;
    }

    /**
     * Methode pour acheter une tour
     * Verifie si le joueur a assez de credit pour acheter la tour
     * Si oui, le credit du joueur va diminuer du cout de la tour
     * @param t la tour a acheter
     * @return true si l'achat est reussi, false sinon
     */
    public int calculInvestissementTotal(Tower t) {
        int total = t.getCout();
        if (t.getEvolutions() != null) {
            for (int c : t.getEvolutions().getEvolutionsPossedees().values()) {
                total += c;
            }
        }
        return total;
    }

    /**Methode abstract gerer l'action */
    public abstract void doAction();
}
