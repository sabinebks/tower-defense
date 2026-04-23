package entity.tower.projectil;

import entity.balloon.Balloon;


public class ProjectilFreeze extends Projectil{

    /**
     * Constructeur de la classe
     * @param type
     * @param startX
     * @param startY
     * @param target
     */
    public ProjectilFreeze(ProjectilType type, double startX, double startY, Balloon target) {
        super(type, startX, startY, target);
    }
    /**
     * La methode active l'impact du projectil
     */
    @Override
    protected void triggerImpact() {
        if (target == null || !target.isActive()) {
            return;
        }
        target.hit(type.getDegats());

        target.freeze(3);
    }
}
