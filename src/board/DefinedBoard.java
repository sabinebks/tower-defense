package board;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Plateau avec un chemin genere aleatoirment
 * Le chemin peut aller haut, bas, gauche, droite
 * Chaque cellule est utilise une seule fois pour eviter les boucles
 * Ce n’est pas parfait mais suffisant pour le livrable 1
 */
public class DefinedBoard extends Board {

    /**
     * Liste de cellules pour sauvegarder notre chemin
     */
    private List<Cell> chemins;

    /**
     * derniere cell du chemin (pour la sauvegarder)
     */
    private Position cheminAv;

    /**
     * declaration de la cellule de depart 
     */
    private Position pointDeDepart;

    /**
     * Constructeur du plateau 
     *
     * @param height Hauteur du plateau haut en bas
     * @param width  Largeur du plateau gache a droite 
     */
    public DefinedBoard(int height, int width) {
        super(height, width);
    }

    /**
     * Initialiser le plateau
     * Crée la grille de cellules et lance la génération du chemin.
     */
    @Override
    protected void init() {
        chemins = new ArrayList<>();
        grid = new Cell[height][width]; // tableau 2D des cellules
        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                grid[r][c] = new Cell(new Position(r, c));
            }
        }
        // Génération du chemin après création de la grille
        calculerPath();
    }

    /**
     * Retourne la liste des cellules du chemin 
     *
     * @return Liste des cellules du chemin
     */
    public List<Cell> getChemins() {
        return chemins;
    }

    /**
     * Retourne le nombre de cellules  chemin
     *
     * @return Longueur du chemin
     */
    public int getCheminLong() {
        return chemins.size();
    }
    /**
     * Retourner si une position est une borde ou non
     * @return true si bord, false sinon
     */
    public boolean isBord( Position p) {
        return p.getRow() == 0 || p.getRow() == this.height - 1 || p.getCol() == 0 || p.getCol() == this.width - 1;
    }




    /**
     * genere un chemin aleatoire , peut commencer de nymporte quel bord 
     * fini toujour dans une cellule de bord 
     * regler le probleme du debut qui se fait que dune seule direction
     */
    @Override
    public void calculerPath() {
        Random rand = new Random();

        /*Ici c'est la taille minimum pour le chemin
        *Cela nous aide a eviter les chemins trop courts
        */
        int minLen = this.width + this.height ;

        // cree toutes les cellules de depart possible  (tous les bords)
        //pris en compte les cellule qui peuvent etre rajouter deux fois 
        //le rand ne sera pas biaise
        List<Position> listPositions = new ArrayList<>();
        //sauvegarder la liste de la premiere ligne et la derniere 
        for (int j = 0; j < width; j++) {
            listPositions.add(new Position(0, j));
            listPositions.add(new Position(height - 1, j));
        }
        //sauvegarder la liste de la premiere colonne et la derniere 
        for (int i = 1; i < height - 1; i++) {
            listPositions.add(new Position(i, 0));
            listPositions.add(new Position(i, width - 1));
        }

        // Choix random du point de depart
        Position pRandom = listPositions.get(rand.nextInt(listPositions.size()));
        pointDeDepart = pRandom;
        cheminAv = pRandom;

        // sauvegarder la la cellule de depart comme chemin
        getCell(pRandom).assignChemin();
        chemins.add(getCell(pRandom));


        // cree une liste de position qui vont etre bloque (peuvent pas etre choisi 
       // comme chemin ou comme voisin)
        List<Position> bloque = new ArrayList<>();
        boolean fin = false;

        while (!fin) {
            // Liste des voisins valides , nest pas bloque et 
            //peut etre en haut , bas ,a gauche ou a droite de la cellule 
            // n'est pas deja un chemin et ne depasse pas les bords 
            List<Position> mesChoix = new ArrayList<>();

            // voisin gauche
            if (cheminAv.getCol() != 0) {
                Position voisin = new Position(cheminAv.getRow(), cheminAv.getCol() - 1);
                if (getCell(voisin).estVide() && !bloque.contains(voisin)) {
                    mesChoix.add(voisin);
                }
            }
            // voisin droit
            if (cheminAv.getCol() != width - 1) {
                Position voisin = new Position(cheminAv.getRow(), cheminAv.getCol() + 1);
                if (getCell(voisin).estVide() && !bloque.contains(voisin)) {
                    mesChoix.add(voisin);
                }
            }
            // voisin haut
            if (cheminAv.getRow() != 0) {
                Position voisin = new Position(cheminAv.getRow() - 1, cheminAv.getCol());
                if (getCell(voisin).estVide() && !bloque.contains(voisin)) {
                    mesChoix.add(voisin);
                }
            }
            // voisin bas
            if (cheminAv.getRow() != height - 1) {
                Position voisin = new Position(cheminAv.getRow() + 1, cheminAv.getCol());
                if (getCell(voisin).estVide() && !bloque.contains(voisin)) {
                    mesChoix.add(voisin);
                }
            }

            // Pas de voisin valide donc fin du chemin
            if (mesChoix.isEmpty()){
                fin = true;
                break;
            } 
            /**
             * Cela nous aide a eviter les chemins de fini apres une seule
             * generation ( Depart puis Fini tout de suite )
             */
            if( chemins.size() < minLen) {
                List<Position> filtre = new ArrayList<>();
                for ( Position p : mesChoix) {
                    if ( !(isBord(p) && !p.equals(pointDeDepart))) {
                        filtre.add(p);
                    }
                }
                if (!filtre.isEmpty()) {
                    mesChoix = filtre;
                }
            }


            // Choix random du chemin qui sera un des voisin  
            Position randi = mesChoix.get(rand.nextInt(mesChoix.size()));
            getCell(randi).assignChemin();
            chemins.add(getCell(randi));

            // Ajouter la ligne ou colonne de cheminAv(la derniere 
            // cellule designee du chemin ) liste bloque
            if (cheminAv.getCol() != randi.getCol()) {
                for (int rr = 0; rr < height; rr++) {
                    bloque.add(new Position(rr, cheminAv.getCol()));
                }
            } else if (cheminAv.getRow() != randi.getRow()) {
                for (int cc = 0; cc < width; cc++) {
                    bloque.add(new Position(cheminAv.getRow(), cc));
                }
            }


            //assigne la celulle quon a trouver de maniere random 
            //comme nouveau chemin precedent pour refaire la boucle 
            cheminAv = randi;

            // Arrêt si on atteint le bord (different du point de depart)
            if (chemins.size() >= minLen && isBord(randi) && !randi.equals(pointDeDepart)) {
                fin = true;
            }
        }
    }
}
