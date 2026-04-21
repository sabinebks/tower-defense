package entity.tower.projectil;

import entity.balloon.Balloon;

/**
 * abstract projectile
 */
public abstract class Projectil {
    /**
     * position
     * target
     * type
     * speed
     * active state
     */
    protected double x, y;
    protected Balloon target;
    protected ProjectilType type;
    protected double speed = 4.0;
    protected boolean active = true;

    /**
     * constructor
     * @param type projectile type
     * @param startX start x
     * @param startY start y
     * @param target target balloon
     */
    public Projectil(ProjectilType type, double startX, double startY, Balloon target) {
        this.type = type;
        this.x = startX;
        this.y = startY;
        this.target = target;
    }

    /**
     * update each tick
     */
    public void update() {
        if (!active) return;

        if (target == null || !target.isActive()) {
            active = false;
            return;
        }

        triggerImpact();
        active = false;
    }

    /**
     * impact effect
     */
    protected abstract void triggerImpact();

    /**
     * check active
     * @return true if active
     */
    public boolean isActive() {
        return active;
    }
}