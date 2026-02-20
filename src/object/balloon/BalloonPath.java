package object.balloon;

import board.Cell;
import board.Position;
import java.util.List;

/**
 * Ballon suivant un chemin prédéfini (liste de cellules).
 * Utilisé pour DefinedBoard.
 */
public class BalloonPath extends Balloon {
    /** Chemin à suivre (liste de cellules) */
    private List<Cell> path;
    
    /** Position actuelle dans le chemin */
    private int pathIndex;

    /**
     * Crée un ballon suivant un chemin.
     * 
     * @param id identifiant unique
     * @param startCell cellule de départ
     * @param speed vitesse du ballon
     * @param type type du ballon
     * @param path chemin à suivre
     */
    public BalloonPath(int id, Cell startCell, BalloonType type, List<Cell> path) {
        // Appeler le constructeur parent
        super(id, 
              startCell.getPosition().getRow() + 0.5,  // Centre de la cellule
              startCell.getPosition().getCol() + 0.5, 
              type);
        
        this.path = path;
        if (path == null || path.isEmpty()) {
            this.active = false;
            this.pathIndex = 0;
        }

    }

    /**
     * Déplace le ballon le long du chemin.
     * Override de la méthode parent.
     * ALGORITHME :
     * 1. Vérifier si actif et pas à la fin
     * 2. Obtenir la cellule cible (prochaine case)
     * 3. Calculer la distance jusqu'au centre de cette case
     * 4. Si on peut l'atteindre en 1 tic :
     *    - Y aller directement
     *    - Passer à la case suivante
     * 5. Sinon :
     *    - Avancer de "speed" unités vers la cible
     * @param height la hauteur du board
     * @param width la largeur du board
     * @return true si le ballon continue, false s'il est arrivé
     */
    @Override
    public boolean move(int height, int width) {
        if (!active) {
            return false;
        }
        if (!active || freezeTimer > 0) return active;

        if (pathIndex >= path.size()) {
            active = false;
            return false;
        }

        Cell targetCell = path.get(pathIndex);
        double currentSpeed = getEffectiveSpeed();
        if (currentSpeed <= 0) return active;

        Position targetPos = targetCell.getPosition();

        double targetLigne = targetPos.getRow() + 0.5;
        double targetColonne = targetPos.getCol() + 0.5;

        double diffCol = targetColonne - posColonne;
        double diffRow = targetLigne - posLigne;

        double distance = Math.sqrt(diffCol * diffCol + diffRow * diffRow);

        if (distance == 0) {
            pathIndex += 1;
            if (pathIndex >= path.size()) active = false;
            return active;
        }

        if (distance <= currentSpeed) {
            posColonne = targetColonne;
            posLigne = targetLigne;
            pathIndex += 1;

            if (pathIndex >= path.size()) {
                active = false;
            }
        } 
        else {
            posColonne += (diffCol / distance) * currentSpeed;
            posLigne += (diffRow / distance) * currentSpeed;
        }
        
        if (posColonne < 0 || posColonne >= width || posLigne < 0 || posLigne >= height) {
            active = false;
        }  

        return active;
    }

    /**
     * Retourne le chemin suivi par ce ballon.
     * 
     * @return la liste de cellules du chemin
     */
    public List<Cell> getPath() {
        return path;
    }

    /**
     * Retourne la position actuelle dans le chemin.
     * 
     * @return l'index de la cellule actuelle
     */
    public int getPathIndex() {
        return pathIndex;
    }
}