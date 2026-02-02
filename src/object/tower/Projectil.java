package object.tower;

/**
 * enum pour les projectil
 */
public enum Projectil {

    FLECHETTE(1),
    FLECHETTE_POINTUE(2),
    FLECHETTE_TRES_POINTUE(4),
    AIGUILLE(1),
    BOMBE(2),
    EXTRA_BOMBE(3);

    private final int degats;

    // constructeur
    Projectil(int degats) {
        this.degats = degats;
    }

    // getter
    public int getDegats() {
        return degats;
    }
}
