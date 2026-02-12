package object;
import java.util.*;
import object.balloon.*;;

public class Round {
    private int numManche;
    private int nbrBalloons;
    private int solde=0;
   /*  pour stocker les ballons de la manche 
    remarque pour ajouter dans notre liste il va falloir 
    faire un random et selon le niveau de la manche on va 
    ajouter des ballons de different niveau ( de niveau plus eleve ) */
<<<<<<< HEAD

    private Set<Balloon> baloons = new HashSet<>();
=======
    private Set <Balloon> balloons= new HashSet<>();
>>>>>>> 0939c0b0cf11555ad9583ee7444d2a19decc0417

   /* constructeur de la classe Round */
    public Round(int numManche, int nbrBalloons) {
        this.numManche = numManche;
        this.nbrBalloons = nbrBalloons;
        this.baloons = new HashSet<Balloon>();
    }
    /* fonction qui retourne le numero de la manche */
    public int getNumManche() {
        return numManche;
    }

    public void addBaloon(Balloon b) {
        baloons.add(b);
    }

    /* fonction qui retourne le nombre de ballons dans la manche */

    public int getNbrBalloons() {
        return nbrBalloons;
    }

     /* fonction qui decremente le nombre de ballons quand un ballon est poppee */

    public void popBalloon(int idBal) {
        if (nbrBalloons > 0) {
            nbrBalloons--;
        }
    }
        /* fonction qui retourne le solde du joueur */
    public int getSolde() {
        return  this.solde;
    }
        /* procedure qui met a jour le solde du joueur */
    public void setSolde(int solde) {
        this.solde += solde;
    }

}
