package board;
/**
 * Représente les différentes directions possibles de déplacement
 * sur le plateau du jeu.
 *
 * Cette énumération est utilisée pour indiquer dans quelle direction
 * un objet (par exemple un ballon ou un joueur) doit se déplacer.
 *
 * Les directions correspondent aux quatre mouvements principaux
 * dans une grille :
 * - vers le haut
 * - vers le bas
 * - vers la droite
 * - vers la gauche
 */
public enum Direction {
    UP,
    DOWN,
    RIGHT,
    LEFT;
}
