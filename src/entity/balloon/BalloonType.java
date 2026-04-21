package entity.balloon;

/**
 * balloon type
 */
public enum BalloonType {

    BASIC(1, 0.5, 10),
    MEDIUM(2, 0.25, 25),
    STRONG(4, 0.125, 50);

    public final int health;
    public final double speed;
    public final int reward;

    /**
     * constructor
     * @param health health value
     * @param speed speed value
     * @param reward reward value
     */
    BalloonType(int health, double speed, int reward) {
        this.health = health;
        this.speed = speed;
        this.reward = reward;
    }
}