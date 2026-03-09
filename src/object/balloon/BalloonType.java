package object.balloon;

/**
 * Représente les différents types de ballons disponibles dans le jeu.
 *
 * Chaque type de ballon peut avoir des caractéristiques différentes
 * comme par exemple :
 * - une vitesse différente
 * - une résistance différente
 * - un comportement particulier dans le jeu
 *
 * Cette énumération permet d’identifier facilement le type d’un ballon
 * lors de sa création ou pendant son utilisation dans le programme.
 */
public enum BalloonType {
    BASIC,
    MEDIUM,
    STRONG;
}
