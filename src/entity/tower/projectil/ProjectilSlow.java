package entity.tower.projectil;

import entity.balloon.Balloon;

public class ProjectilSlow extends Projectil {
    /**
     * Constructeur de la classe
     * @param type
     * @param startX
     * @param startY
     * @param target
     */
    public ProjectilSlow(ProjectilType type, double startX, double startY, Balloon target) {
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
        target.slow(0.5, 6);
    }

}
