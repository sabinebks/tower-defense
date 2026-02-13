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
              startCell.getPosition().getCol() + 0.5,  // Centre de la cellule
              startCell.getPosition().getRow() + 0.5, 
              type);
        
        this.path = path;
        this.pathIndex = 0;
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
     * 
     * @return true si le ballon continue, false s'il est arrivé
     */
    @Override
    public boolean move() {
        if (!active || freezeTimer > 0) return active;

        double currentSpeed = getEffectiveSpeed();
        if (currentSpeed <= 0) return active;

        // On commence par viser la case d'après celle où on est
        if (pathIndex < path.size()) {
            Cell targetCell = path.get(pathIndex);
            Position targetPos = targetCell.getPosition();

            double targetX = targetPos.getCol() + 0.5;
            double targetY = targetPos.getRow() + 0.5;

            double dx = targetX - posColonne;
            double dy = targetY - posLigne;
            double distance = Math.sqrt(dx * dx + dy * dy);

            if (distance <= currentSpeed) {
                posColonne = targetX;
                posLigne = targetY;
                pathIndex++; // On a atteint cette case, on visera la suivante au prochain tic
            } else {
                // Avancement fluide
                posColonne += (dx / distance) * currentSpeed;
                posLigne += (dy / distance) * currentSpeed;
            }
        } else {
            // Sortie du plateau après la dernière case
            posColonne += currentSpeed;
            if (posLigne > 25) active = false; // Ajuste selon la taille de ton board
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