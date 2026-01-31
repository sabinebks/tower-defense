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
    public BalloonPath(int id, Cell startCell, double speed, BalloonType type, List<Cell> path) {
        // Appeler le constructeur parent
        super(id, 
              startCell.getPosition().getCol() + 0.5,  // Centre de la cellule
              startCell.getPosition().getRow() + 0.5, 
              speed, 
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
        // Vérifier si le ballon peut encore bouger
        if (!active) {
            return false;  // Le ballon est déjà sorti ou détruit
        }
        
        // suivre le chemin (si pas encore fini) 
        if (pathIndex < path.size()) {
            // Prendre la prochaine case à atteindre
            Cell targetCell = path.get(pathIndex);
            Position targetPos = targetCell.getPosition();
            
            // Coordonnées du CENTRE de la case cible
            // +0.5 pour viser le milieu de la case, pas le coin
            double targetX = targetPos.getCol() + 0.5;
            double targetY = targetPos.getRow() + 0.5;
            
            // Calculer la distance entre le ballon et la case cible
            double dx = targetX - posX;  // Différence horizontale
            double dy = targetY - posY;  // Différence verticale
            double distance = Math.sqrt(dx * dx + dy * dy);  // Distance réelle (Pythagore)
            
            // cas1 : On peut atteindre la case en un seul déplacement
            if (distance <= speed) {
                // On se téléporte exactement au centre de la case
                posX = targetX;
                posY = targetY;
                pathIndex++;  // On passe à la case suivante du chemin
            } 
            // cas2: La case est trop loin pour un seul déplacement
            else {
                // ratio = proportion de la distance à parcourir ce tour-ci
                double ratio = speed / distance;
                posX += dx * ratio;  // Avancer horizontalement
                posY += dy * ratio;  // Avancer verticalement
            }
        }
        
        // ===== ÉTAPE 2 : SORTIR DU PLATEAU (après la dernière case) =====
        if (pathIndex >= path.size()) {
            // Le chemin est terminé, mais le ballon doit continuer 
            // pour vraiment sortir du plateau
            
            posX += speed;
            
            // Quand le ballon est assez loin (hors écran), on le désactive
            if (posX > 20) {  // 20 cases à droite = largement sorti
                active = false;  // Le ballon a vraiment fini son parcours
            }
        }
        
        // Retourne vrai si le ballon est encore actif
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