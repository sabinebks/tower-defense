package object.tower.projectil;

import object.balloon.Balloon;
import object.tower.EffectType;


public class ProjectilEffet extends Projectil {

    private EffectType effet;

    public ProjectilEffet(ProjectilType type, double startX, double startY, Balloon target, EffectType effet) {
        super(type, startX, startY, target);
        this.effet = effet;
    }

    @Override
    protected void triggerImpact() {

        target.hit(type.getDegats());


        if (this.effet == EffectType.SLOW) {
            target.slow(0.5,100);
        }

        else if (this.effet == EffectType.FREEZE) {
            target.freeze(60);
        }
        this.active = false;
    }

}
