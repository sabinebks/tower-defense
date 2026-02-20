package object.balloon;

import board.Direction;
import board.Position;

/**
 * Ballon se déplaçant en ligne droite.
 * Utilisé pour FreeBoard.
 */
public class BalloonStraight extends Balloon {
    /** Direction du mouvement */
    private Direction direction;
    
    /** Position finale (destination) */
    private double endCol;
    private double endRow;
    
    /** Positions de départ et d'arrivée (pour affichage) */
    private Position startPos;
    private Position endPos;

    /**
     * Crée un ballon rectiligne.
     * 
     * @param id identifiant unique
     * @param start position de départ
     * @param end position d'arrivée
     * @param speed vitesse du ballon
     * @param type type du ballon
     */
    public BalloonStraight(int id, Position start, Position end, BalloonType type) {
        // Appeler le constructeur parent
        super(id, 
              start.getRow() + 0.5,  // Centre de la cellule de départ
              start.getCol() + 0.5, 
              type);
        
        this.endCol = end.getCol() + 0.5;
        this.endRow = end.getRow() + 0.5;
        
        // Position finale (centre de la cellule d'arrivée)
        this.endCol = end.getCol() + 0.5;
        this.endRow = end.getRow() + 0.5;
        
        // Déterminer la direction du mouvement
        if (end.getRow() > start.getRow()) {
            direction = Direction.DOWN;
        } else if (end.getRow() < start.getRow()) {
            direction = Direction.UP;
        } else if (end.getCol() > start.getCol()) {
            direction = Direction.RIGHT;
        } else {
            direction = Direction.LEFT;
        }
    }

    /**
     * Déplace le ballon en ligne droite.
     * Override de la méthode parent.
     * 
     * ALGORITHME :
     * 1. Vérifier si actif
     * 2. Avancer selon la direction :
     *    - UP : posY diminue
     *    - DOWN : posY augmente
     *    - LEFT : posX diminue
     *    - RIGHT : posX augmente
     * 3. Vérifier si on a atteint la destination
     * @param height l'hauteur
     * @param width largeur
     * @return true si le ballon continue, false s'il est arrivé
     */
    @Override
    public boolean move(int height, int width) {
        if (!active || freezeTimer > 0) return active;

        double currentSpeed = getEffectiveSpeed();
        if (currentSpeed <= 0) return active;

        if (direction == Direction.UP) {
            posLigne -= currentSpeed;
            if (posLigne <= endRow) { posLigne = endRow; active = false; }
        } else if (direction == Direction.DOWN) {
            posLigne += currentSpeed;
            if (posLigne >= endRow) { posLigne = endRow; active = false; }
        } else if (direction == Direction.LEFT) {
            posColonne -= currentSpeed;
            if (posColonne <= endCol) { posColonne = endCol; active = false; }
        } else if (direction == Direction.RIGHT) {
            posColonne += currentSpeed;
            if (posColonne >= endCol) { posColonne = endCol; active = false; }
        }

        return active;
    }

    /**
     * Retourne la direction du mouvement.
     * 
     * @return la direction
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * Retourne la position de départ.
     * 
     * @return position de départ
     */
    public Position getStartPos() {
        return startPos;
    }

    /**
     * Retourne la position d'arrivée.
     * 
     * @return position d'arrivée
     */
    public Position getEndPos() {
        return endPos;
    }

    public void takeDamage(int degats) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'takeDamage'");
    }
}