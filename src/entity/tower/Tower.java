package entity.tower;
import board.Position;
import entity.balloon.*;
import entity.tower.evolution.Evolution;
import entity.tower.evolution.TypeEvolution;
import entity.tower.projectil.*;

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
            System.out.println("Tower a " + position + " tire");
            try {
                Projectil p = this.attack(balloons);
                if (p != null) {
                    activeProjectiles.add(p);
                }
            }catch(NotMatchException e){
                e.getMessage();
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
    protected abstract Projectil attack(List<Balloon> balloons) throws NotMatchException;


    /**
     * méthode pour calculer la distance entre la tour et le ballon b
     * @param b le ballon b
     * @return la distance
     */
    public double distanceTo(Balloon b) {
    double towerRow = position.getRow() + 0.5;
    double towerCol = position.getCol() + 0.5;

    double balloonRow = b.getLigne() ;
    double balloonCol = b.getColonne() ;

    double dRow = towerRow - balloonRow;
    double dCol = towerCol - balloonCol;

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
            if (b != null && b.isActive()) {
                double dist = distanceTo(b);
                System.out.println("Distance tour->ballon: " + dist + " portée: " + porte);
                if (porte == -1 || dist <= porte) {
                    if (effectType == EffectType.FREEZE && b.getEffectiveSpeed() == 0) continue;
                    return b;
                }
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
    public int getCost(){
        return cout;
    }

    /**
     * methode abstraite pour evoluer une tour
     * chaque tout a ses evolution
     * @param typeEvo type d'evolution qu'on veut appliquer
     * @return retourner son cout
     */
    public abstract int appliquerEvolution(TypeEvolution typeEvo);


    public int SellEvolution(TypeEvolution evo){
        int cost = this.evolutions.getEvolutionsPossedees().get(evo);
        this.evolutions.getEvolutionsPossedees().remove(evo);
        return cost / 2;
    }

    /**
     * retourne le symbole de la tour pour l'affichage
     * @return le symbole de la tour
     */
    public abstract String getSymbol();
    public abstract String getName();
      // setter pour la position
    public void setPosition(Position position){
        this.position = position;
    }

    public int getSellPrice(){
        return this.cout / 2;
    }
   //getter 
    public abstract int  getCoutEvolution(TypeEvolution evolution);
  public double getrow() {
        return position.getRow();
    }

    public double getcol() {
        return position.getCol();
   }
}