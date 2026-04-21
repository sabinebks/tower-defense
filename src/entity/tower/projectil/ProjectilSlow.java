package entity.tower.projectil;

import entity.balloon.Balloon;

/**
 * slow projectile
 */
public class ProjectilSlow extends Projectil {

    /**
     * constructor
     * @param type projectile type
     * @param startX start x
     * @param startY start y
     * @param target target balloon
     */
    public ProjectilSlow(ProjectilType type, double startX, double startY, Balloon target) {
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
        target.slow(0.5, 6);
    }
}