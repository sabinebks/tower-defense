package entity.balloon;

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

    public boolean move(){
        if (freezeTimer > 0 || path == null || pathIndex >= path.size()) {
            return active;
        }

        Position target = path.get(pathIndex);
        double targetLigne = target.getRow() + 0.5;
        double targetColonne = target.getCol() + 0.5;

        double effectiveSpeed = getEffectiveSpeed();
        double deltaLigne = targetLigne - posLigne;
        double deltaColonne = targetColonne - posColonne;
        double distanceToTarget = Math.sqrt(deltaLigne * deltaLigne + deltaColonne * deltaColonne);

        if (distanceToTarget <= effectiveSpeed) {
            posLigne = targetLigne;
            posColonne = targetColonne;
            pathIndex++;
            progression = 0.0;

            if (pathIndex >= path.size()) {
                active = false;
                escaped = true;
                exitTime = 0; // Peut être mis à jour par le gestionnaire de jeu
            }
        } else {
            progression += effectiveSpeed / distanceToTarget;
            posLigne += deltaLigne * (effectiveSpeed / distanceToTarget);
            posColonne += deltaColonne * (effectiveSpeed / distanceToTarget);
        }

        return active;
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
        if (this.slowTimer <= 0) {
            this.slowFactor = factor;
            this.slowTimer = duration;
        }
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
    public double getColonne() { return posColonne;     }
    public double getLigne() { return posLigne; }
    public List<Position> getPath() { return this.path; }

    // ===== SETTTERS =====

   public void setPath(List<Position> path) {
        this.path = path;
        if (path != null && !path.isEmpty()) {
            Position start = path.get(0);
            this.posLigne = start.getRow() + 0.5;
            this.posColonne = start.getCol() + 0.5;
            this.pathIndex = 0;
            this.progression = 0.0;
        }

     public void setStartPosition(double ligne, double colonne) {
            this.posLigne = ligne;
            this.posColonne = colonne;
            this.pathIndex = 0;
            this.progression = 0.0;
    }
}
