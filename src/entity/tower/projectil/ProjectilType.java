package entity.tower.projectil;

/**
 * projectile types
 */
public enum ProjectilType {

    NO_DAMAGE(0),
    FLECHETTE(1),
    FLECHETTE_POINTUE(2),
    FLECHETTE_TRES_POINTUE(4),
    AIGUILLE(1),
    BOMBE(2),
    EXTRA_BOMBE(3);

    private final int degats;

    /**
     * constructor
     * @param degats damage value
     */
    ProjectilType(int degats) {
        this.degats = degats;
    }

    /**
     * get damage
     * @return damage
     */
    public int getDegats() {
        return degats;
    }
}