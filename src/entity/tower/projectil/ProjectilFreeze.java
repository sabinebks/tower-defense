package entity.tower.projectil;

import entity.balloon.Balloon;

/**
 * freeze projectile
 */
public class ProjectilFreeze extends Projectil {

    /**
     * constructor
     * @param type projectile type
     * @param startX start x
     * @param startY start y
     * @param target target balloon
     */
    public ProjectilFreeze(ProjectilType type, double startX, double startY, Balloon target) {
        super(type, startX, startY, target);
    }

    @Override
    /**
     * trigger impact
     */
    protected void triggerImpact() {
        if (target == null || !target.isActive()) {
            return;
        }

        target.hit(type.getDegats());
        target.freeze(3);
    }
}