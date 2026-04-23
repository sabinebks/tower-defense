package entity.balloon;

import java.util.List;

import board.Position;

public class Balloon {

    /**
     * id of the balloons
     * Balloons pos column
     * Balloons pos row
     * Balloons speed
     * Balloons health
     * Balloons type
     * Balloons state ( active/not)
     * Balloons escaped or not
     * Balloons exits time
     * A timer for freeze
     * A timer for sloow
     * The slow factor
     * The path of balloons
     * The paths index
     * Balloons progression
     */
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
    this.posColonne = startColonne + 0.5;
    this.posLigne = startLigne + 0.5;
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

    //  Position mise à jour  avec progression
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
/** Met à jour les timers de gel et de ralentissement du ballon. Doit être appelé à chaque tic de jeu.
     */
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
/* Applique un effet de ralentissement au ballon, réduisant sa vitesse pendant une durée donnée.
     *
     * @param factor le facteur de ralentissement
     * @param duration la durée du ralentissement en tics
     */
 public void slow(double factor, int duration) {
        if (this.slowTimer <= 0) {
            this.slowFactor = factor;
            this.slowTimer = duration;
        }
    }
/* Force la destruction du ballon, le retirant du jeu.
     */
    public void remove() { this.active = false; }
/* Indique que le ballon a atteint la fin du chemin et a échappé.
     */
    public boolean hasEscaped() { return escaped; }
    /** Indique si le ballon est actif.
     * 
     * @return true si le ballon est actif, false sinon
     */
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
    /** Retourne le temps d'évasion du ballon, ou -1 s'il n'a pas encore échappé.
     * 
     * @return le temps d'évasion
     */
    public int getExitTime() { return exitTime; }
    /** Retourne la position actuelle en colonne du ballon.
     * 
     * @return la position en colonne
     */
    public double getColonne() { return posColonne;     }
    /** Retourne la position actuelle en ligne du ballon.
     * 
     * @return la position en ligne
     */
    public double getLigne() { return posLigne; }
    /** Retourne le chemin que suit le ballon.
     * 
     * @return la liste de positions du chemin
     */
    public List<Position> getPath() { return this.path; }

    // ===== SETTTERS =====
   /** Définit le chemin que doit suivre le ballon. Réinitialise la position du ballon au début du chemin.
     * 
     * @param path la liste de positions formant le chemin
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
    /** Définit la position de départ du ballon.
     * 
     * @param ligne la ligne de la position de départ
     * @param colonne la colonne de la position de départ
     */
    public void setStartPosition(double ligne, double colonne) {
    this.posLigne = ligne + 0.5;
    this.posColonne = colonne + 0.5;
    this.pathIndex = 0;
    this.progression = 0.0;
}
}
