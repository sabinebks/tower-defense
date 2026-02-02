package object.tower;

import board.Position;
import object.balloon.*;

import java.util.List;

/**
 * La classe interface des towers ( mieux gerer leurs actions)
 */
public abstract class Tower {

    /**
     * les attributs de la classe abstraite Tower
     * position : pour sa position
     * projectil : pour le type de projectil qui lance
     * cout : son cout pour l'acheter
     * porte :
     */
    protected Position position;
    protected Projectil projectil;
    protected int cout;
    protected int porte;
    protected int cadence;
    protected boolean evolution;
    protected EffectType effectType;

    public Tower(Position position, int cout, int porte, int cadence, boolean evolution, EffectType effectType) {
        this.position = position;
        this.cout = cout;
        this.porte = porte;
        this.cadence = cadence;
        this.evolution = evolution;
        this.effectType = effectType;
    }

    public Position getPosition() {
        return position;
    }

    public int getCout() {
        return cout;
    }

    public int getPorte() {
        return porte;
    }

    public int getCadence() {
        return cadence;
    }

    public boolean isEvolution() {
        return evolution;
    }
    public EffectType getEffectType() {
        return effectType;
    }

    public abstract void shoot(List<Balloon> balloons);
}
