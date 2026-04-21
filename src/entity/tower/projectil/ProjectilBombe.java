package entity.tower.projectil;

import java.util.List;
import entity.balloon.Balloon;

/**
 * bomb projectile
 */
public class ProjectilBombe extends Projectil {

    /**
     * balloons in range
     * explosion radius
     */
    private List<Balloon> balloons;
    private double explosionRadius;

    /**
     * constructor
     * @param type projectile type
     * @param startX start x
     * @param startY start y
     * @param target target balloon
     * @param radius explosion radius
     * @param currentBalloons list of balloons
     * @throws NotMatchException wrong type
     */
    public ProjectilBombe(ProjectilType type, double startX, double startY, Balloon target, double radius, List<Balloon> currentBalloons) throws NotMatchException {
        super(type, startX, startY, target);

        this.explosionRadius = radius;
        this.balloons = currentBalloons;

        if (type != ProjectilType.BOMBE && type != ProjectilType.EXTRA_BOMBE) {
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

        if (balloons == null) return;

        for (Balloon b : balloons) {
            if (b != null && b.isActive() && b != target) {

                double dx = b.getColonne() - this.x;
                double dy = b.getLigne() - this.y;
                double d = Math.sqrt(dx * dx + dy * dy);

                if (d <= explosionRadius) {
                    b.hit(type.getDegats());
                }
            }
        }
    }
}