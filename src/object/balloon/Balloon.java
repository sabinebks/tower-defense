package object.balloon;


import java.util.List;

import board.Position;

public class Balloon {
 

/**
 * Classe de base pour tous les ballons.
 * Contient les attributs et méthodes communs.
 */

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
    //Attributs spécifiques pour les ballons améliorés
    /** Chemin que le ballon doit suivre */
    protected List<Position> path;
    /** Index de la position actuelle dans le chemin */
    protected int pathIndex;
    /**
     * Crée un ballon de base.
     * 
     * @param id identifiant unique
     * @param startColonne position X de départ
     * @param startLigne position Y de départ
     * @param type type du ballon
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

/**
* Déplace le ballon d'un tic.
* Cette méthode sera override dans les sous-classes.
* 
* @return true si le ballon continue, false s'il est arrivé
*/
   
public boolean move(int height, int width) {
     
    updateState();
    if (!active || freezeTimer > 0) return active;

    if (path == null || path.isEmpty()) return active;

    if (pathIndex >= path.size()) {
        active = false;
        return false;
    }

    Position target = path.get(pathIndex);

    double targetRow = target.getRow() + 0.5;
    double targetCol = target.getCol() + 0.5;

    double dx = targetCol - posColonne;
    double dy = targetRow - posLigne;

    double distance = Math.sqrt(dx * dx + dy * dy);

    double currentSpeed = getEffectiveSpeed();
    if (currentSpeed <= 0) return active;

    if (distance <= currentSpeed) {
        posColonne = targetCol;
        posLigne = targetRow;
        pathIndex++;

        if (pathIndex >= path.size()) {
            active = false;
        }
        } else {
            posColonne += (dx / distance) * currentSpeed;
            posLigne += (dy / distance) * currentSpeed;
        }

        return active;
    }



    /**
     * methode pour savoir si le ballon a ete gelé ou ralenti 
     * et gerer ça dans move et actualiser le ballon a chaque fois
     */
    public void updateState(){
        if(freezeTimer > 0){
            freezeTimer--;
        }
        if(slowTimer > 0){
            slowTimer--;
        }
        if (slowTimer <= 0) {
            slowFactor = 1.0;
        }
    }

     public double getEffectiveSpeed() {
        if (freezeTimer > 0) {return 0;}
        if(slowTimer > 0){return this.speed * slowFactor;}
        return this.speed;
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

   

    /**
     * pour stopper le ballon
     */
    public void freeze(int duration) {
        if (this.freezeTimer<=0) {
            this.freezeTimer = duration;
        }
    }

    /**
     * Ralentit le ballon (réduit sa vitesse)
     * @param factor facteur de ralentissement (ex: 0.5 = 50% plus lent)
     */
    public void slow(double factor, int duration) {
        this.slowFactor = factor;
        this.slowTimer = duration;
    }


    /**
     *
     */
    public void remove(){
        this.active = false;
    }

    // ===== SETTTERS =====
    
    public void setPath(List<Position> path) {
        this.path = path;
        this.pathIndex = 0;
    }

}
