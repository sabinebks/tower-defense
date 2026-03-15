package object.tower.projectil;

import object.balloon.Balloon;
import object.tower.EffectType;


public class ProjectilEffet extends Projectil {
    /**
     * Les attributs de la classes
     * Le type d'effet
     */
    private EffectType effet;
    /**
     * Constructeur de la classe
     * @param type
     * @param startX
     * @param startY
     * @param target
     * @param effet
     */
    public ProjectilEffet(ProjectilType type, double startX, double startY, Balloon target, EffectType effet) {
        super(type, startX, startY, target);
        this.effet = effet;
    }
    /**
     * La methode active l'impact du projectil
     */
    @Override  
    protected void triggerImpact() {
        if (target == null || !target.isActive()) {
            return;
        }
        target.hit(type.getDegats());


        if (this.effet == EffectType.SLOW) {
            target.slow(0.5,5);
        }

        else if (this.effet == EffectType.FREEZE) {
            target.freeze(3);
        }
    }
}
