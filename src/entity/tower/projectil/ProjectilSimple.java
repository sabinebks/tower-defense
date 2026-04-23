package entity.tower.projectil;

import entity.balloon.Balloon;

public class ProjectilSimple extends Projectil {

    /**
     * Constructeur
     * @param type
     * @param startX
     * @param startY
     * @param target
     * @throws NotMatchException
     */
    public ProjectilSimple(ProjectilType type, double startX, double startY, Balloon target) throws NotMatchException {
        super(type, startX, startY, target);
        if (type == ProjectilType.BOMBE || type == ProjectilType.EXTRA_BOMBE){
            throw new NotMatchException("Pas possible avec ce type de projectil dans cette classe");
        }
    }
    /**
     * La methode active l'impact du projectil
     */
    @Override
    protected void triggerImpact(){
        if (target != null && target.isActive()) {
            target.hit(type.getDegats());
        }
    }
}
