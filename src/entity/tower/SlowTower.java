package entity.tower;

import board.Position;
import entity.balloon.Balloon;
import entity.tower.evolution.TypeEvolution;
import entity.tower.projectil.NotMatchException;
import entity.tower.projectil.Projectil;
import entity.tower.projectil.ProjectilSlow;
import entity.tower.projectil.ProjectilType;

import java.util.List;

/**
 * slow tower
 */
public class SlowTower extends Tower {

    /**
     * constructor
     * @param pos tower position
     */
    public SlowTower(Position pos) {
        super(pos, 500, 100, 8, false, EffectType.SLOW, ProjectilType.NO_DAMAGE);
    }

    @Override
    /**
     * apply evolution
     * @param typeEvo evolution type
     * @return cost
     */
    public int appliquerEvolution(TypeEvolution typeEvo) {
        return 0;
    }

    @Override
    /**
     * attack method
     * @param balloons list of balloons
     * @return projectile
     * @throws NotMatchException wrong projectile
     */
    public Projectil attack(List<Balloon> balloons) throws NotMatchException {

        Balloon target = findTarget(balloons);
        if (target == null) return null;

        double startX = position.getCol() + 0.5;
        double startY = position.getRow() + 0.5;

        return new ProjectilSlow(this.type, startX, startY, target);
    }

    @Override
    /**
     * get evolution cost
     * @param typeEvo evolution type
     * @return cost
     */
    public int getCoutEvolution(TypeEvolution typeEvo) {
        return 0;
    }

    @Override
    /**
     * get symbol
     * @return symbol
     */
    public String getSymbol() {
        return "S";
    }

    @Override
    /**
     * get name
     * @return name
     */
    public String getName() {
        return "SlowTower";
    }
}