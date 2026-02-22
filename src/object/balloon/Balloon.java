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
    protected double posColonne; //X
    protected double posLigne; //Y
    
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
     * @param startColonne position X de départ
     * @param startLigne position Y de départ
     * @param type type du ballon
     */
    public Balloon(int id, double startLigne, double startColonne, BalloonType type, int speed) {
        this.id = id;
        this.posColonne = startColonne;
        this.posLigne = startLigne;
        this.type = type;
        this.active = true;
        this.exitTime = -1;
        this.speed = speed;
        
        // Définir la santé selon le type
        if (type == BalloonType.BASIC) {
            this.health = 1;
            this.speed = 3;
        } else if (type == BalloonType.MEDIUM) {
            this.health = 2;
            this.speed = 2;
        } else if (type == BalloonType.STRONG) {
            this.health = 4;
            this.speed = 1;
        }
    }

    /**
     * Déplace le ballon d'un tic.
     * Cette méthode sera override dans les sous-classes.
     * 
     * @return true si le ballon continue, false s'il est arrivé
     */
    public boolean move(int height, int move) {
        // À override dans les sous-classes
        return active;
    }

    /**
     * Touche le ballon (diminue sa santé).
     * 
     * @param damage dégâts infligés
     * @return true si le ballon est détruit, false sinon
     */
    public boolean hit(int damage) {
        System.out.println("HIT " + type + " HP =" + health);
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
        return new Position((int)posLigne, (int)posColonne);
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
    public double getColonne() {
        return posColonne;
    }

    /**
     * Retourne la position Y précise.
     * @return posY
     */
    public double getLigne() {
        return posLigne;
    }

    public void updateState(){
        if(freezeTimer > 0){
            freezeTimer--;
        }
        if(slowTimer > 0){
            slowTimer--;
        }
        if (slowTimer == 0) {
            slowFactor = 1.0;
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

    public double getEffectiveSpeed() {
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