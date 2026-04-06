package choose.action;

import object.tower.Tower;

public class TowerAction {
    protected Tower tower;

    /**
     * Constructeur.
     * @param tower la tour concernée
     */
    public TowerAction(Tower tower) {
        this.tower = tower;
    }

    public Tower getTower() {
        return tower;
    }
    
}
