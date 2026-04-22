package entity.tower;

import board.Position;
import entity.balloon.*;
import entity.tower.evolution.Evolution;
import entity.tower.evolution.TypeEvolution;
import entity.tower.projectil.*;

import java.util.List;

/**
 * tower base class
 */
public abstract class Tower {
    /**
     * Position of the tower
     * Type of projectile of the tower
     * Price of the tower
     * Porte of the tower
     * Cadence of the tower
     * Verify if tower can be evolue
     * Effect type of the tower
     * Evolution available of the tower
     * Degats increase after upgrade
     * Tick since the last shot from tower
     */
    protected Position position;
    protected ProjectilType type;
    protected int cout;
    protected int porte;
    protected int cadence;
    protected boolean evolution;
    protected EffectType effectType;
    protected Evolution evolutions;
    protected int degatsSup;
    protected int ticksSinceLastShot = 0;

    /**
     * constructor
     * @param position position
     * @param cout cost
     * @param porte range
     * @param cadence fire rate
     * @param evolution can evolve
     * @param effectType effect type
     * @param type projectile type
     */
    public Tower(Position position, int cout, int porte, int cadence, boolean evolution, EffectType effectType, ProjectilType type) {
        this.position = position;
        this.cout = cout;
        this.porte = porte;
        this.cadence = cadence;
        this.evolution = evolution;
        this.effectType = effectType;
        this.type = type;
        this.degatsSup = 0;

        if (evolution) {
            this.evolutions = new Evolution();
        }
    }

    /**
     * update tower
     * @param balloons list of balloons
     * @param activeProjectiles list of projectiles
     * @throws NotMatchException error
     */
    public void update(List<Balloon> balloons, List<Projectil> activeProjectiles) throws NotMatchException {

        ticksSinceLastShot++;

        if (ticksSinceLastShot >= cadence) {

            System.out.println("tower at " + position + " shoots");

            try {
                Projectil p = this.attack(balloons);
                if (p != null) {
                    activeProjectiles.add(p);
                }
            } catch (NotMatchException e) {
                e.getMessage();
            }

            ticksSinceLastShot = 0;
        }
    }

    /**
     * attack method
     * @param balloons list of balloons
     * @return projectile
     * @throws NotMatchException error
     */
    protected abstract Projectil attack(List<Balloon> balloons) throws NotMatchException;

    /**
     * distance to balloon
     * @param b balloon
     * @return distance
     */
    public double distanceTo(Balloon b) {

        double towerRow = position.getRow() + 0.5;
        double towerCol = position.getCol() + 0.5;

        double balloonRow = b.getLigne();
        double balloonCol = b.getColonne();

        double dRow = towerRow - balloonRow;
        double dCol = towerCol - balloonCol;

        return Math.sqrt(dRow * dRow + dCol * dCol);
    }

    /**
     * find target
     * @param balloons list of balloons
     * @return balloon target
     */
    public Balloon findTarget(List<Balloon> balloons) {

        for (Balloon b : balloons) {

            if (b != null && b.isActive()) {

                double dist = distanceTo(b);
                System.out.println("distance tower->balloon: " + dist + " range: " + porte);

                if (porte == -1 || dist <= porte) {

                    if (effectType == EffectType.FREEZE && b.getEffectiveSpeed() == 0) continue;

                    return b;
                }
            }
        }

        return null;
    }

    /**
     * get position
     * @return position
     */
    public Position getPosition() {
        return position;
    }

    /**
     * get range
     * @return range
     */
    public int getPorte() {
        return porte;
    }

    /**
     * can evolve
     * @return boolean
     */
    public boolean getEvolution() {
        return evolution;
    }

    /**
     * get evolutions
     * @return evolution object
     */
    public Evolution getEvolutions() {
        return evolutions;
    }

    /**
     * get cost
     * @return cost
     */
    public int getCost() {
        return cout;
    }

    /**
     * apply evolution
     * @param typeEvo evolution type
     * @return cost
     */
    public abstract int appliquerEvolution(TypeEvolution typeEvo);

    /**
     * sell evolution
     * @param evo evolution type
     * @return refund
     */
    public int SellEvolution(TypeEvolution evo) {
        int cost = this.evolutions.getEvolutionsPossedees().get(evo);
        this.evolutions.getEvolutionsPossedees().remove(evo);
        return cost / 2;
    }

    /**
     * get symbol
     * @return symbol
     */
    public abstract String getSymbol();

    /**
     * get name
     * @return name
     */
    public abstract String getName();

    /**
     * set position
     * @param position position
     */
    public void setPosition(Position position) {
        this.position = position;
    }

    /**
     * get sell price
     * @return price
     */
    public int getSellPrice() {
        return this.cout / 2;
    }

    /**
     * get evolution cost
     * @param evolution evolution type
     * @return cost
     */
    public abstract int getCoutEvolution(TypeEvolution evolution);

    /**
     * return tower row
     * @return tower row
     */
    public double getrow() {
        return position.getRow();
    }

    /**
     * Return towers column
     * @return towers column
     */
    public double getcol() {
        return position.getCol();
    }
}