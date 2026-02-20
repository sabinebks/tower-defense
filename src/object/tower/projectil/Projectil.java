package object.tower.projectil;

import object.balloon.Balloon;

public abstract class Projectil {
    /**
     * Les attributs de la classe abstract
     * x, y la position
     * le type du projectile
     * sa vitesse
     * son status
     */
    protected double x, y;
    protected Balloon target;
    protected ProjectilType type;
    protected double speed = 0.5;
    protected boolean active = true;

    /**
     * Constructeur
     * @param type
     * @param startX
     * @param startY
     * @param target
     */
    public Projectil(ProjectilType type, double startX, double startY, Balloon target) {
        this.type = type;
        this.x = startX;
        this.y = startY;
        this.target = target;
    }

    /**
     * cette methode est appelé à chaque tic de l'horloge
     */
    public void update() {
        if (!active) return;
        if (target == null || !target.isActive()) {
            active = false;
            return;
        }

        double diffX = target.getColonne() - this.x;
        double diffY = target.getLigne() - this.y;
        double distance = Math.sqrt(diffX * diffX + diffY * diffY);

        if (distance < 0.2) {
            triggerImpact();
            active = false;
            return;
        }
        

        this.x += (diffX / distance) * speed;
        this.y += (diffY / distance) * speed;
    }

    protected abstract void triggerImpact();

    public boolean isActive() { return active; }
}