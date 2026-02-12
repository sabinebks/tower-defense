package object.tower.projectil;

import object.balloon.Balloon;

public class ProjectilSimple extends Projectil {

    public ProjectilSimple(ProjectilType type, double startX, double startY, Balloon target) {
        super(type, startX, startY, target);
    }

    @Override
    protected void triggerImpact(){
        target.hit(type.getDegats());
    }
}
