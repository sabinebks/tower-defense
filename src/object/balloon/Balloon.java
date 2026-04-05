package object.balloon;

import java.util.List;

import board.Position;

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
     * Constructeur de base pour les ballons.
     * Initialise les attributs en fonction du type de ballon.
     *
     * @param id identifiant unique du ballon
     * @param startLigne position de départ en ligne (Y)
     * @param startColonne position de départ en colonne (X)
     * @param type type du ballon (BASIC, MEDIUM, STRONG)
     */

    public Balloon(int id, double startLigne, double startColonne, BalloonType type) {
        this.id = id;
        this.posColonne = startColonne;
        this.posLigne = startLigne;
        this.type = type;
        this.active = true;
        this.exitTime = -1;
        this.pathIndex = 0;
        this.health = type.health;
        this.speed = type.speed;
    }
    /** Déplace le ballon le long de son chemin.
     * @return true si le ballon est toujours actif après le déplacement, false s'il a atteint la fin du chemin ou est détruit
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

        double Speed = getEffectiveSpeed();
        if (Speed <= 0) return true;

        double remaining = Speed;

        while (remaining > 0 && pathIndex < path.size()) {
            Position current = path.get(pathIndex);

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
            posLigne = cur.getRow() + 0.5;
            posColonne = cur.getCol() + 0.5;

            if (pathIndex + 1 < path.size() && progression > 0) {
                int nextIndex = Math.min(pathIndex + 1, path.size() - 1);
                Position next = path.get(nextIndex);
                posLigne = cur.getRow() + 0.5 + (next.getRow() - cur.getRow()) * progression;
                posColonne = cur.getCol() + 0.5 + (next.getCol() - cur.getCol()) * progression;
            }
        }

        return true;
    }

        /**
         * Calcule la vitesse effective du ballon en tenant compte des effets de gel et de ralentissement.
         *
         * @return la vitesse effective actuelle du ballon
         */
    public double getEffectiveSpeed() {
        if (freezeTimer > 0)
            return 0.0;
        return speed * slowFactor;
    }

    /** Applique des dégâts au ballon et vérifie s'il est détruit.
     *
     * @param damage les points de dégâts à infliger
     * @return true si le ballon est détruit, false sinon
     */
    public boolean hit(int damage) {
        this.health -= damage;

        if (this.health <= 0) {
            this.active = false;
            return true;
        }

        return false;
    }

    public void update() {
        if (freezeTimer > 0) freezeTimer--;
        if (slowTimer > 0) {
            slowTimer--;
            if (slowTimer == 0) slowFactor = 1.0;
        }
    }


    /** Applique un effet de gel au ballon, le rendant immobile pendant une durée donnée.
     *
     * @param duration la durée du gel en tics
     */
    public void freeze(int duration) {
        if (this.freezeTimer <= 0) {
            this.freezeTimer = duration;
        }
    }

    public void slow(double factor, int duration) {
        this.slowFactor = factor;
        this.slowTimer = duration;
    }

    public void remove() { this.active = false; }

    public boolean hasEscaped() { return escaped; }
    public boolean isActive() {
        return active;
    }


        // ===== GETTERS =====

    /** Retourne l'identifiant du ballon.
     * 
     * @return l'identifiant
     */
    public int getId() {
        return id;
    }

    /** Retourne la vitesse de base du ballon (sans effets).
     * 
     * @return la vitesse
     */
    public double getSpeed() {
        return speed;
    }

    /** Retourne les points de vie actuels du ballon.
     * 
     * @return les points de vie
     */
    public int getHealth() {
        return health;
    }

    /**
     * Retourne le type du ballon.
     * 
     * @return le type
     */
    public BalloonType getType() {
        return type;
    }

    public int getExitTime() { return exitTime; }
    public double getColonne() { return posColonne; }
    public double getLigne() { return posLigne; }
    public List<Position> getPath() { return this.path; }

    // ===== SETTTERS =====

    public void setPath(List<Position> path) {
        this.path = path;
        this.pathIndex = 0;
        this.progression = 0.0;
        if (path != null && !path.isEmpty()) {
            Position start = path.get(0);
            this.posLigne = start.getRow() + 0.5;
            this.posColonne = start.getCol() + 0.5;
        }
    }


     public void setStartPosition(double ligne, double colonne) {
            this.posLigne = ligne;
            this.posColonne = colonne;
            this.pathIndex = 0;
            this.progression = 0.0;
    }
}
