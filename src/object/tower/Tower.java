package object.tower;

import board.Position;
import object.balloon.BalloonStraight;
import java.util.List;

public abstract class Tower {
    protected Position position;
    protected Projectil projectil;
    protected int cout;
    protected int porte;
    protected int cadence;
    protected boolean evolution;
    protected EffectType effectType;

    protected int ticksSinceLastShot = 0;
    public Tower(Position position, int cout, int porte, int cadence, boolean evolution, EffectType effectType) {
        this.position = position;
        this.cout = cout;
        this.porte = porte;
        this.cadence = cadence;
        this.evolution = evolution;
        this.effectType = effectType;
    }
    public void update(List<BalloonStraight> balloons) {
        ticksSinceLastShot++;
        // On vérifie si la tour peut tirer selon sa cadence
        if (ticksSinceLastShot >= (cadence / 100)) { // Conversion simplifiée ms -> tics
            if (attack(balloons)) {
                ticksSinceLastShot = 0;
            }
        }
    }
    protected abstract boolean attack(List<BalloonStraight> balloons);
    protected double distanceTo(BalloonStraight b) {
        Position p = b.getCurrentPosition();
        return Math.sqrt(Math.pow(position.getRow() - p.getRow(), 2) +
                Math.pow(position.getCol() - p.getCol(), 2));
    }
    protected BalloonStraight findTarget(List<BalloonStraight> balloons) {
        for (BalloonStraight b : balloons) {
            // On vérifie si le ballon est actif et à portée
            if (b.isActive() && (porte == -1 || distanceTo(b) <= porte)) {
                return b;
            }
        }
        return null;
    }
    public Position getPosition() { return position; }
}