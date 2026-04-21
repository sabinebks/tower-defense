package entity.balloon;

import java.util.List;

import board.Position;

/**
 * balloon class
 */
public class Balloon {

    protected int id;
    protected double posColonne;
    protected double posLigne;
    protected double speed;
    protected int health;
    protected BalloonType type;
    protected boolean active;
    protected boolean escaped = false;
    protected int exitTime;
    protected int freezeTimer = 0;
    protected int slowTimer = 0;
    protected double slowFactor = 1.0;
    protected List<Position> path;
    protected int pathIndex;
    private double progression = 0.0;

    /**
     * constructor
     * @param id balloon id
     * @param startLigne start row
     * @param startColonne start column
     * @param type balloon type
     */
    public Balloon(int id, double startLigne, double startColonne, BalloonType type) {
        this.id = id;
        this.posColonne = startColonne + 0.5;
        this.posLigne = startLigne + 0.5;
        this.type = type;
        this.active = true;
        this.exitTime = -1;
        this.pathIndex = 0;
        this.health = type.health;
        this.speed = type.speed;
    }

    /**
     * move balloon
     * @return true if still active
     */
    public boolean move() {
        if (!active) return false;
        if (path == null || path.isEmpty()) return false;

        if (pathIndex >= path.size()) {
            active = false;
            escaped = true;
            return false;
        }

        if (freezeTimer > 0) return true;

        double speed = getEffectiveSpeed();
        if (speed <= 0) return true;

        double remaining = speed;

        while (remaining > 0 && pathIndex < path.size()) {
            double distToNext = 1.0 - progression;

            if (remaining >= distToNext) {
                remaining -= distToNext;
                progression = 0.0;
                pathIndex++;

                if (pathIndex >= path.size()) {
                    active = false;
                    escaped = true;

                    Position last = path.get(path.size() - 1);
                    posLigne = last.getRow() + 0.5;
                    posColonne = last.getCol() + 0.5;

                    return false;
                }
            } else {
                progression += remaining;
                remaining = 0;
            }
        }

        if (pathIndex < path.size()) {
            Position cur = path.get(pathIndex);

            if (pathIndex + 1 < path.size()) {
                Position next = path.get(pathIndex + 1);

                posLigne = cur.getRow() + 0.5
                        + (next.getRow() - cur.getRow()) * progression;

                posColonne = cur.getCol() + 0.5
                        + (next.getCol() - cur.getCol()) * progression;
            } else {
                posLigne = cur.getRow() + 0.5;
                posColonne = cur.getCol() + 0.5;
            }
        }

        return true;
    }

    /**
     * get effective speed
     * @return speed
     */
    public double getEffectiveSpeed() {
        if (freezeTimer > 0)
            return 0.0;
        return speed * slowFactor;
    }

    /**
     * apply damage
     * @param damage damage value
     * @return true if dead
     */
    public boolean hit(int damage) {
        this.health -= damage;

        if (this.health <= 0) {
            this.active = false;
            return true;
        }

        return false;
    }

    /**
     * update timers
     */
    public void update() {
        if (freezeTimer > 0) freezeTimer--;
        if (slowTimer > 0) {
            slowTimer--;
            if (slowTimer == 0) slowFactor = 1.0;
        }
    }

    /**
     * freeze balloon
     * @param duration duration
     */
    public void freeze(int duration) {
        if (this.freezeTimer <= 0) {
            this.freezeTimer = duration;
        }
    }

    /**
     * slow balloon
     * @param factor slow factor
     * @param duration duration
     */
    public void slow(double factor, int duration) {
        if (this.slowTimer <= 0) {
            this.slowFactor = factor;
            this.slowTimer = duration;
        }
    }

    /**
     * remove balloon
     */
    public void remove() {
        this.active = false;
    }

    /**
     * check escape
     * @return true if escaped
     */
    public boolean hasEscaped() {
        return escaped;
    }

    /**
     * check active
     * @return true if active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * get id
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * get speed
     * @return speed
     */
    public double getSpeed() {
        return speed;
    }

    /**
     * get health
     * @return health
     */
    public int getHealth() {
        return health;
    }

    /**
     * get type
     * @return type
     */
    public BalloonType getType() {
        return type;
    }

    /**
     * get exit time
     * @return exit time
     */
    public int getExitTime() {
        return exitTime;
    }

    /**
     * get column
     * @return column
     */
    public double getColonne() {
        return posColonne;
    }

    /**
     * get row
     * @return row
     */
    public double getLigne() {
        return posLigne;
    }

    /**
     * get path
     * @return path
     */
    public List<Position> getPath() {
        return this.path;
    }

    /**
     * set path
     * @param path path list
     */
    public void setPath(List<Position> path) {
        this.path = path;

        if (path != null && !path.isEmpty()) {
            Position start = path.get(0);
            this.posLigne = start.getRow() + 0.5;
            this.posColonne = start.getCol() + 0.5;
            this.pathIndex = 0;
            this.progression = 0.0;
        }
    }

    /**
     * set start position
     * @param ligne row
     * @param colonne column
     */
    public void setStartPosition(double ligne, double colonne) {
        this.posLigne = ligne + 0.5;
        this.posColonne = colonne + 0.5;
        this.pathIndex = 0;
        this.progression = 0.0;
    }
}