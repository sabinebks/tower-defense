package object.tower.projectil;

import object.balloon.Balloon;

import java.util.List;

public class ProjectilBombe extends Projectil {
    private List<Balloon> balloons;
    private double explosionRadius ;

    public ProjectilBombe(ProjectilType type, double startX, double startY, Balloon target, double radius, List<Balloon> currentBalloons) throws NotMatchException {
        super(type, startX, startY, target);
        this.explosionRadius = radius;
        this.balloons = currentBalloons;
        if (type != ProjectilType.BOMBE && type != ProjectilType.EXTRA_BOMBE){
            throw new NotMatchException("Pas possible avec ce type de projectil dans cette classe");
        }
    }

    @Override
    protected void triggerImpact() {
        target.hit(type.getDegats());

        for(Balloon b : balloons) {
            if(b != target) {
                double distance = Math.sqrt(Math.pow(b.getColonne() - this.x, 2) + Math.pow(b.getLigne() - this.y, 2));

                if (distance <= explosionRadius){
                    b.hit(type.getDegats());
                }
            }
        }
    }

}
