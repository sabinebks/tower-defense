package entity.tower.projectil;

import java.util.List;

import entity.balloon.Balloon;

public class ProjectilBombe extends Projectil {
    /**
     * Les attributs de la classes
     * Une liste des ballons dans le radius
     * Le radius du bomb
     */
    private List<Balloon> balloons;
    private double explosionRadius ;
    /**
     * Constructeur
     * @param type
     * @param startX
     * @param startY
     * @param target
     * @param radius
     * @param currentBalloons
     * @throws NotMatchException
     */
    public ProjectilBombe(ProjectilType type, double startX, double startY, Balloon target, double radius, List<Balloon> currentBalloons) throws NotMatchException {
        super(type, startX, startY, target);
        this.explosionRadius = radius;
        this.balloons = currentBalloons;
        if (type != ProjectilType.BOMBE && type != ProjectilType.EXTRA_BOMBE){
            throw new NotMatchException("Pas possible avec ce type de projectil dans cette classe");
        }
    }

    /**
     *La methode active l'impact du projectil
     */
    @Override
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
