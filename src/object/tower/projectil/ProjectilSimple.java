package object.tower.projectil;

import object.balloon.Balloon;

public class ProjectilSimple extends Projectil {

    public ProjectilSimple(ProjectilType type, double startX, double startY, Balloon target) throws NotMatchException {
        super(type, startX, startY, target);
        if (type == ProjectilType.BOMBE || type == ProjectilType.EXTRA_BOMBE){
            throw new NotMatchException("Pas possible avec ce type de projectil dans cette classe");
        }
    }

    @Override
    protected void triggerImpact(){
        target.hit(type.getDegats());
    }
}
