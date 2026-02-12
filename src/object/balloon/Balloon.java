package object.balloon;

import board.Position;

/**
 * Classe de base pour tous les ballons.
 * Contient les attributs et méthodes communs.
 */
public class Balloon {
    /** Identifiant unique */
    protected int id;
    
    /** Position précise (avec décimales pour mouvement fluide) */
    protected double posX;
    protected double posY;
    
    /** Vitesse (unités par tic) */
    protected double speed;
    
    /** Points de vie */
    protected int health;
    
    /** Type du ballon */
    protected BalloonType type;
    
    /** État du ballon */
    protected boolean active;
    
    /** Temps de sortie (-1 si pas encore sorti) */
    protected int exitTime;

    protected int freezeTimer = 0;
    protected int slowTimer = 0;
    protected double slowFactor = 1.0;

    /**
     * Crée un ballon de base.
     * 
     * @param id identifiant unique
     * @param startX position X de départ
     * @param startY position Y de départ
     * @param speed vitesse du ballon
     * @param type type du ballon
     */
    public Balloon(int id, double startX, double startY, double speed, BalloonType type) {
        this.id = id;
        this.posX = startX;
        this.posY = startY;
        this.speed = speed;
        this.type = type;
        this.active = true;
        this.exitTime = -1;
        
        // Définir la santé selon le type
        if (type == BalloonType.BASIC) {
            this.health = 1;
        } else if (type == BalloonType.MEDIUM) {
            this.health = 2;
        } else if (type == BalloonType.STRONG) {
            this.health = 4;
        }
    }

    /**
     * Déplace le ballon d'un tic.
     * Cette méthode sera override dans les sous-classes.
     * 
     * @return true si le ballon continue, false s'il est arrivé
     */
    public boolean move() {
        // À override dans les sous-classes
        return false;
    }

    /**
     * Touche le ballon (diminue sa santé).
     * 
     * @param damage dégâts infligés
     * @return true si le ballon est détruit, false sinon
     */
    public boolean hit(int damage) {
        health -= damage;
        if (health <= 0) {
            active = false;
            return true;  // Ballon détruit
        }
        return false;  // Ballon encore vivant
    }

    /**
     * Vérifie si le ballon est actif.
     * 
     * @return true si actif, false sinon
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Retourne la position actuelle (case) du ballon.
     * 
     * @return la position de la case où se trouve le ballon
     */
    public Position getCurrentPosition() {
        return new Position((int)posY, (int)posX);
    }

    /**
     * Définit le temps de sortie du ballon.
     * 
     * @param time le temps en tics
     */
    public void setExitTime(int time) {
        this.exitTime = time;
    }

    // ===== GETTERS =====
    
    /**
     * Retourne l'identifiant du ballon.
     * @return l'id
     */
    public int getId() {
        return id;
    }

    /**
     * Retourne la vitesse du ballon.
     * @return la vitesse
     */
    public double getSpeed() {
        return speed;
    }

    /**
     * Retourne les points de vie restants.
     * @return la santé
     */
    public int getHealth() {
        return health;
    }

    /**
     * Retourne le type du ballon.
     * @return le type
     */
    public BalloonType getType() {
        return type;
    }

    /**
     * Retourne le temps de sortie.
     * @return le temps de sortie (-1 si pas encore sorti)
     */
    public int getExitTime() {
        return exitTime;
    }

    /**
     * Retourne la position X précise.
     * @return posX
     */
    public double getPosX() {
        return posX;
    }

    /**
     * Retourne la position Y précise.
     * @return posY
     */
    public double getPosY() {
        return posY;
    }

    public void updateState(){
        if(freezeTimer > 0){
            freezeTimer--;
        }
        if(slowTimer > 0){
            slowTimer--;
        }
    }

    /**
     * pour stopper le ballon
     */
    public void freeze(int duration) {
        this.freezeTimer = duration;
    }

    /**
     * Ralentit le ballon (réduit sa vitesse)
     * @param factor facteur de ralentissement (ex: 0.5 = 50% plus lent)
     */
    public void slow(double factor, int duration) {
        this.slowFactor = factor;
        this.slowTimer = duration;
    }

    public double getSlowFactor() {
        if (freezeTimer > 0) {return 0;}
        if(slowTimer > 0){return this.speed * slowFactor;}
        return this.speed;
    }

    /**
     *
     */
    public void remove(){
        this.active = false;
    }

}