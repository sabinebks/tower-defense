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

        this.path = null;
        this.pathIndex = 0;

        if (type == BalloonType.BASIC) {
            this.health = 1;
            this.speed = 0.5;
        } else if (type == BalloonType.MEDIUM) {
            this.health = 2;
            this.speed = 0.25;
        } else if (type == BalloonType.STRONG) {
            this.health = 4;
            this.speed = 0.125;
        }
    }
    // Attributs spécifiques pour les ballons améliorés

    /** Déplace le ballon le long de son chemin.
     *
     * @param height hauteur du plateau (pour les limites)
     * @param width largeur du plateau (pour les limites)
     * @return true si le ballon est toujours actif après le déplacement, false s'il a atteint la fin du chemin ou est détruit
     */

    public boolean move(int height, int width) {
        if (freezeTimer > 0) {
            freezeTimer--;
        }
        if (!active)
            return false;
        if (path == null || path.isEmpty())
            return false;
        if (pathIndex >= path.size()) {
            active = false;
            return false;
        }

        if (freezeTimer > 0)
            return true; // IMPORTANT: reste actif mais ne bouge pas

        Position target = path.get(pathIndex);

        double targetRow = target.getRow() + 0.5;
        double targetCol = target.getCol() + 0.5;

        double dx = targetCol - posColonne;
        double dy = targetRow - posLigne;

        double distance = Math.sqrt(dx * dx + dy * dy);

        double speed = getEffectiveSpeed();
        if (speed <= 0)
            return true;

        if (distance <= speed) {
            posColonne = targetCol;
            posLigne = targetRow;
            pathIndex++;

            if (pathIndex >= path.size()) {
                active = false;
                return false;
            }

        } else {
            posColonne += (dx / distance) * speed;
            posLigne += (dy / distance) * speed;
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

    /** Définit le temps de sortie du ballon.
     *
     * @param time le temps de sortie à définir
     */
    public void setExitTime(int time) {
        this.exitTime = time;
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

    /**
     * Retourne le temps de sortie.
     * 
     * @return le temps de sortie (-1 si pas encore sorti)
     */
    public int getExitTime() {
        return exitTime;
    }

    /**
     * Retourne la position X précise.
     * 
     * @return posX
     */
    public double getColonne() {
        return posColonne;
    }

    /**
     * Retourne la position Y précise.
     * 
     * @return posY
     */
    public double getLigne() {
        return posLigne;
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

    /** Applique un effet de ralentissement au ballon, réduisant sa vitesse pendant une durée donnée.
     *
     * @param factor le facteur de ralentissement (ex: 0.5 pour moitié de la vitesse)
     * @param duration la durée du ralentissement en tics
     */
    public void slow(double factor, int duration) {
        if (slowTimer > 0) {
            slowTimer--;
            if (slowTimer == 0) {
                slowFactor = 1.0;
            }
        }
        this.slowFactor = factor;
        this.slowTimer = duration;
    }

    /** Marque le ballon comme détruit et inactif.
     */
    public void remove() {
        this.active = false;
    }

    // ===== SETTTERS =====

    public void setPath(List<Position> path) {
        this.path = path;
        this.pathIndex = 0;
    }

    public boolean isActive() {
        return active;
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
}
