package object.tower;
import board.Position;
import object.balloon.*;
import object.tower.evolution.Evolution;
import object.tower.evolution.TypeEvolution;
import object.tower.projectil.*;

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
     * type pour le type de projectil
     *
     *
     */
    protected Position position;
    protected ProjectilType type;
    protected int cout;
    protected int porte;
    protected int cadence;
    protected boolean evolution;
    protected EffectType effectType;
    protected Evolution evolutions;
    protected int degatsSup;

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
    public Tower(Position position, int cout, int porte, int cadence, boolean evolution, EffectType effectType, ProjectilType type) {
        this.position = position;
        this.cout = cout;
        this.porte = porte;
        this.cadence = cadence;
        this.evolution = evolution;
        this.effectType = effectType;
        this.type = type;
        this.degatsSup = 0;
        if(evolution) {
            this.evolutions = new Evolution();
        }
    }
    /**
     * Methode pour mettre a jour apres un shot
     * @param balloons liste des ballons
     * @param activeProjectiles liste des projectils
     * @throws NotMatchException
     */
    public void update(List<Balloon> balloons, List<Projectil> activeProjectiles) throws NotMatchException {
        ticksSinceLastShot++;
        // On vérifie si la tour peut tirer selon sa cadence
        if (ticksSinceLastShot >= cadence) { // Conversion simplifiée ms -> tics
            System.out.println("Tower a " + position + " tirer");
            Projectil p = this.attack(balloons);
            if (p != null) {
                activeProjectiles.add(p);
            }
            ticksSinceLastShot = 0;
        }
    }

    /**
     * méthode abstraite pour l'attaque
     * Au lieu de lancer des attaques differents dans des differents classes de towers, on va faire une seule
     * methode d'attaque ici, puisque tous les towers faire une meme type d'attaque?
     * @param balloons les ballon qui se retrouve a la grille
     * @return bein eu ou pas
     */
    protected Projectil attack(List<Balloon> balloons) throws NotMatchException {
        Balloon target = findTarget(balloons);
        if (target == null) return null;
        double startX = position.getCol() + 0.5;
        double startY = position.getRow() + 0.5;
        if (this.effectType == EffectType.IMMEDIATE && this.porte == -1) {
            target.hit(this.type.getDegats());
            return null;
        }

        if (effectType == EffectType.SLOW || effectType == EffectType.FREEZE) {
            return new ProjectilEffet(this.type, startX, startY, target, effectType);
        }

        if (this.effectType == EffectType.AREA) {
            return new ProjectilBombe(
                    this.type,
                    startX,
                    startY,
                    target,
                    3,
                    balloons
            );
        }

        return new ProjectilSimple(
                this.type,
                startX,
                startY,
                target
        );
    }

    
    /**
     * méthode pour calculer la distance entre la tour et le ballon b
     * @param b le ballon b
     * @return la distance
     */
    public double distanceTo(Balloon b) {
        double towerRow = position.getRow() + 0.5;
        double towerCol = position.getCol() + 0.5;

        double dRow = towerRow - b.getLigne();
        double dCol = towerCol - b.getColonne();
        return Math.sqrt(dRow * dRow + dCol * dCol);
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
            if (b != null && b.isActive() && (porte == -1 || distanceTo(b) <= porte)) {

                if(this.effectType == EffectType.FREEZE && b.getEffectiveSpeed() == 0){
                    continue;
                }
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

    /**
     * getter pour l'attribut evolution
     * @return
     */
    public boolean getEvolution() {
        return evolution;
    }

    /**
     * getter pour l'attribut evolutions
     * @return
     */
    public Evolution getEvolutions() {
        return evolutions;
    }

    /**
     * getter pour l'attribut count
     * @return
     */
    public int getCout(){
        return cout;
    }

    /**
     * methode abstraite pour evoluer une tour
     * chaque tout a ses evolution
     * @param typeEvo type d'evolution qu'on veut appliquer
     * @return retourner son cout
     */
    public abstract int appliquerEvolution(TypeEvolution typeEvo);

}