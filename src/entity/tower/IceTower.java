package entity.tower;

import board.Position;
import entity.balloon.Balloon;
import entity.tower.evolution.TypeEvolution;
import entity.tower.projectil.NotMatchException;
import entity.tower.projectil.Projectil;
import entity.tower.projectil.ProjectilFreeze;
import entity.tower.projectil.ProjectilType;

import java.util.List;

/**
 * ice tower
 */
public class IceTower extends Tower {

    /**
     * constructor
     * @param pos tower position
     */
    public IceTower(Position pos) {
        super(pos, 400, 100, 15, false, EffectType.FREEZE, ProjectilType.NO_DAMAGE);
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

        return new ProjectilFreeze(this.type, startX, startY, target);
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
        return "I";
    }

    @Override
    /**
     * get name
     * @return name
     */
    public String getName() {
        return "IceTower";
    }
}