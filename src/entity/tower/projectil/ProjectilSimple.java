package entity.tower.projectil;

import entity.balloon.Balloon;

/**
 * simple projectile
 */
public class ProjectilSimple extends Projectil {

    /**
     * constructor
     * @param type projectile type
     * @param startX start x
     * @param startY start y
     * @param target target balloon
     * @throws NotMatchException wrong type
     */
    public ProjectilSimple(ProjectilType type, double startX, double startY, Balloon target) throws NotMatchException {
        super(type, startX, startY, target);

        if (type == ProjectilType.BOMBE || type == ProjectilType.EXTRA_BOMBE) {
            throw new NotMatchException("not allowed projectile type");
        }
    }

    @Override
    /**
     * trigger impact
     */
    protected void triggerImpact() {
        if (target != null && target.isActive()) {
            target.hit(type.getDegats());
        }
    }
}