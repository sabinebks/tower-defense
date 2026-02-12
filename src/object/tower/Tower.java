package object.tower;

import board.Position;
import object.balloon.*;
import object.tower.projectil.Projectil;
import object.tower.projectil.ProjectilType;

import java.util.List;

public abstract class Tower {

    /**
     * les attributs :
     * position : pour la position de la tour
     * projectil : pour les projectil qu'elle dois lancer
     * cout : le cout pour l'acheter
     * porte : le rayon max des projectil
     * cadence : vitesse de tire
     * evolution : boolean pour savoir si elle est capable d'evoluer ou pas
     * effectType : IMMEDIATE ou AREA ou FREEZE ..
     *
     *
     *
     */
    protected Position position;
    protected Projectil projectil;
    protected ProjectilType type;
    protected int cout;
    protected int porte;
    protected int cadence;
    protected boolean evolution;
    protected EffectType effectType;

    protected int ticksSinceLastShot = 0;

    /**
     * contructeur
     * @param position position
     * @param cout le cout
     * @param porte le rayon max
     * @param cadence vitesse de tire
     * @param evolution true or false
     * @param effectType type d'effet
     */
    public Tower(Position position, int cout, int porte, int cadence, boolean evolution, EffectType effectType) {
        this.position = position;
        this.cout = cout;
        this.porte = porte;
        this.cadence = cadence;
        this.evolution = evolution;
        this.effectType = effectType;
    }

    public void update(List<Balloon> balloons) {
        ticksSinceLastShot++;
        // On vérifie si la tour peut tirer selon sa cadence
        if (ticksSinceLastShot >= (cadence / 50)) { // Conversion simplifiée ms -> tics
            if (attack(balloons)) {
                ticksSinceLastShot = 0;
            }
        }
    }

    /**
     * méthode abstraite pour l'attaque
     * Au lieu de lancer des attaques differents dans des differents classes de towers, on va faire une seule
     * methode d'attaque ici, puisque tous les towers faire une meme type d'attaque?
     * @param balloons les ballon qui se retrouve a la grille
     * @return bein eu ou pas
     */
    protected boolean attack(List<Balloon> balloons) {

        Balloon target = findTarget(balloons);

        if  ( this.effectType == EffectType.IMMEDIATE ){
            if (target != null) {

            // Le ballon prend les dégâts de la fléchette pointue
            target.hit(projectil.getDegats());

            // La tour a tiré
            return true;
            }
        }
        else if ( this.effectType == EffectType.SLOW) {
            if (target != null && target.isActive()) {

            // Inflige les dégâts
            target.hit(projectil.getDegats());

            // Ralentit le ballon (ex: -60% de vitesse)
            target.slow(0.4);

            // La tour a tiré
            return true;
            }
        }
        else if (this.effectType == EffectType.AREA ) {
            if (target != null) {
            // Position de l'explosion (position du ballon ciblé)
            Position impact = target.getCurrentPosition();
            // On parcourt tous les ballons
            for (Balloon b : balloons) {
                // Calcul de la distance entre le ballon et l'explosion
                double d = Math.sqrt(
                    Math.pow(b.getCurrentPosition().getRow() - impact.getRow(), 2) +
                    Math.pow(b.getCurrentPosition().getCol() - impact.getCol(), 2)
                );
                // Si le ballon est actif et dans la zone d'explosion
                if (b.isActive() && d <= porte) {
                    // Le ballon prend des dégâts
                    b.hit(projectil.getDegats());
                }
            }
            // La tour a tiré
            return true;
            }
        }
        else if ( this.effectType == EffectType.FREEZE) {
            if (target != null  && target.isActive()) {

            // Inflige les dégâts
            target.hit(projectil.getDegats());

            // Gèle le ballon
            target.freeze();

            // La tour a tiré
            return true;
            }
        }
        return false;
    }

    /**
     * méthode pour calculer la distance entre la tour et le ballon b
     * @param b le ballon b
     * @return la distance
     */
    public double distanceTo(Balloon b) {
        Position p = b.getCurrentPosition();
        return Math.sqrt(Math.pow(position.getRow() - p.getRow(), 2) +
                Math.pow(position.getCol() - p.getCol(), 2));
    }

    /**
     * méthode permettant de déterminer le ballon ciblé par le tir
     *
     * @param balloons les ballons dans la grille
     * @return baloon ciblé
     */
    public Balloon findTarget(List<Balloon> balloons) {
        for (Balloon b : balloons) {
            // On vérifie si le ballon est actif et à portée
            if (b.isActive() && (porte == -1 || distanceTo(b) <= porte)) {
                return b;
            }
        }
        return null;
    }

    /**
     * getter pour la position
     * @return
     */
    public Position getPosition() {
        return position;
    }

    public int getPorte() {
    return porte;
}

}