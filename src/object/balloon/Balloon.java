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

   public BalloonAmeliore(int id, double startLigne, double startColonne, BalloonType type) {
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

  public void setPath(List<Position> path) {
    this.path = path;
    this.pathIndex = 0;
}
    /**
     * Déplace le ballon d'un tic.
     * Cette méthode sera override dans les sous-classes.
     * 
     * @return true si le ballon continue, false s'il est arrivé
     */

}
