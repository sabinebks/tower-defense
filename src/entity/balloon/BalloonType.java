package entity.balloon;

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
    BASIC(1, 0.5, 10),
    MEDIUM(2, 0.25, 25),
    STRONG(4, 0.125, 50);

    public final int health;
    public final double speed;
    public final int reward; // crédits gagnés quand ce ballon est détruit

    BalloonType(int health, double speed, int reward) {
        this.health = health;
        this.speed = speed;
        this.reward = reward;
    }
}
