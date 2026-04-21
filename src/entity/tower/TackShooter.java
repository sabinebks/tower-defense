package entity.tower;

import board.Position;
import entity.balloon.Balloon;
import entity.tower.evolution.TypeEvolution;
import entity.tower.projectil.NotMatchException;
import entity.tower.projectil.Projectil;
import entity.tower.projectil.ProjectilSimple;
import entity.tower.projectil.ProjectilType;

import java.util.List;

/**
 * tack shooter tower
 */
public class TackShooter extends Tower {

    /**
     * constructor
     * @param pos tower position
     */
    public TackShooter(Position pos) {

        super(pos, 350, 80, 2, true, EffectType.IMMEDIATE, ProjectilType.AIGUILLE);

        this.evolutions.addEvolution(TypeEvolution.PORTEE);
        this.evolutions.addEvolution(TypeEvolution.CANDENCE);
    }

    @Override
    /**
     * apply evolution
     * @param typeEvo evolution type
     * @return cost
     */
    public int appliquerEvolution(TypeEvolution typeEvo) {

        if (!this.evolutions.getEvolutions().contains(typeEvo)) return 0;
        if (this.evolutions.getEvolutionsPossedees().containsKey(typeEvo)) return 0;

        int cout = 0;

        switch (typeEvo) {

            case PORTEE:
                this.porte = (int)(this.porte * 1.2);
                cout = 150;
                this.evolutions.deleteEvolutions(TypeEvolution.PORTEE);
                break;

            case CANDENCE:
                this.cadence = (int)(this.cadence * 1.25);
                cout = 200;
                this.evolutions.deleteEvolutions(TypeEvolution.CANDENCE);
                break;

            default:
                break;
        }

        if (cout > 0) {
            this.evolutions.getEvolutionsPossedees().put(typeEvo, cout);
        }

        return cout;
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

        if (this.porte == -1) {
            target.hit(this.type.getDegats());
            return null;
        }

        return new ProjectilSimple(
                this.type,
                startX,
                startY,
                target
        );
    }

    @Override
    /**
     * get evolution cost
     * @param typeEvo evolution type
     * @return cost
     */
    public int getCoutEvolution(TypeEvolution typeEvo) {
        switch (typeEvo) {
            case PORTEE:
                return 150;
            case CANDENCE:
                return 200;
            default:
                return 0;
        }
    }

    @Override
    /**
     * get symbol
     * @return symbol
     */
    public String getSymbol() {
        return "T";
    }

    @Override
    /**
     * get name
     * @return name
     */
    public String getName() {
        return "TackShooter";
    }
}