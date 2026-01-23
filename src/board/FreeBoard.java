/*
pseudo code 
pour chaque ballon B:
    si B.direction == RIGHT:
        B.col++
    si B.direction == LEFT:
        B.col--
    si B.direction == DOWN:
        B.row++
    si B.direction == UP:
        B.row--

    // vérifier bord du plateau
    si B.col < 0: B.col = 0; inverser direction à RIGHT
    si B.col >= largeur: B.col = largeur-1; inverser direction à LEFT
    si B.row < 0: B.row = 0; inverser direction à DOWN
    si B.row >= hauteur: B.row = hauteur-1; inverser direction à UP

    // ajouter le ballon sur la cellule
    Cell[cell.row][cell.col].ajouterBallon(B)
 */

package board;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class FreeBoard extends Board {

    /**
     * Constructeur de FreeBoard
     * 
     * @param height
     * @param width
     */
    public FreeBoard(int height, int width) {
        super(height, width);
    }

    /**
     * Methode init pour initialiser le board
     */
    @Override
    public void init() {
        grid = new Cell[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                grid[i][j] = new Cell(new Position(i, j));
            }
        }
    }

    // cree une map qui enregistre la map chemins , ainsi que le numero
    // du chemin
    private Map<Integer, Position> numChemins = new HashMap<>();
    private int cmp = 0;

    // cree une map ou on enregiste les premieres et les dernieres
    // cellules du chemin cree par calculerPath
    private Map<Position, Position> chemins = new HashMap<>();

    /**
     * Methode calculerPath pour creer le chemin ou les ballons marche
     * On a du coup 4 directions pour le chemin, chaque chemin va etre creer
     * aleatoirement vers le bord oppose
     */
    @Override

    public void calculerPath() {

        Random rand = new Random();

        // cree toutes les cellules de depart possible (tous les bords)
        // pris en compte les cellule qui peuvent etre rajouter deux fois
        // le rand ne sera pas biaise
        List<Position> listPositions = new ArrayList<>();
        // sauvegarder la liste de la premiere ligne et la derniere
        for (int j = 0; j < width; j++) {
            listPositions.add(new Position(0, j));
            listPositions.add(new Position(height - 1, j));
        }
        // sauvegarder la liste de la premiere colonne et la derniere
        for (int i = 1; i < height - 1; i++) {
            listPositions.add(new Position(i, 0));
            listPositions.add(new Position(i, width - 1));
        }

        // Choix random du point de depart
        Position pRandom = listPositions.get(rand.nextInt(listPositions.size()));

        // sauvegarder la la cellule de depart et toutes sa ligne ou sa colonne
        // comme chemin

        Direction dir = getDirection(pRandom, getFinChemin(pRandom));

        switch (dir) {
            case RIGHT: {
                int row = pRandom.getRow();
                for (int c = pRandom.getCol(); c <= getFinChemin(pRandom).getCol(); c++) {
                    getCell(row, c).assignChemin();
                }
                break;
            }
            case LEFT: {
                int row = pRandom.getRow();
                for (int c = pRandom.getCol(); c >= getFinChemin(pRandom).getCol(); c--) {
                    getCell(row, c).assignChemin();
                }
                break;
            }
            case DOWN: {
                int col = pRandom.getCol();
                for (int r = pRandom.getRow(); r <= getFinChemin(pRandom).getRow(); r++) {
                    getCell(r, col).assignChemin();
                }
                break;
            }
            case UP: {
                int col = pRandom.getCol();
                for (int r = pRandom.getRow(); r >= getFinChemin(pRandom).getRow(); r--) {
                    getCell(r, col).assignChemin();
                }
                break;
            }
            default:
                throw new IllegalArgumentException("Positions non alignées !");
        }

        // enlever les positions deja chemin de la liste listPositions (peuvent pas etre
        // choisi
        // comme chemin ou comme voisin)
        rmPositions(pRandom, getFinChemin(pRandom), listPositions, width, height);
        chemins.put(pRandom, getFinChemin(pRandom));
        numChemins.put(cmp, pRandom);
        cmp++;
        // boolean pour s'arreter quand ya plus de celulles non bloque
        boolean fin = false;

        while (!fin) {
            // si la liste est vide sortir de la boucle fin de creation de chemins
            if (listPositions.size() == 0) {
                fin = true;
                break;
            } else {
                pRandom = listPositions.get(rand.nextInt(listPositions.size()));

                // on sauvegarde les cases chemins
                dir = getDirection(pRandom, getFinChemin(pRandom));

                switch (dir) {
                    case RIGHT: {
                        int row = pRandom.getRow();
                        for (int c = pRandom.getCol(); c <= getFinChemin(pRandom).getCol(); c++) {
                            getCell(row, c).assignChemin();
                        }
                        break;
                    }
                    case LEFT: {
                        int row = pRandom.getRow();
                        for (int c = pRandom.getCol(); c >= getFinChemin(pRandom).getCol(); c--) {
                            getCell(row, c).assignChemin();
                        }
                        break;
                    }
                    case DOWN: {
                        int col = pRandom.getCol();
                        for (int r = pRandom.getRow(); r <= getFinChemin(pRandom).getRow(); r++) {
                            getCell(r, col).assignChemin();
                        }
                        break;
                    }
                    case UP: {
                        int col = pRandom.getCol();
                        for (int r = pRandom.getRow(); r >= getFinChemin(pRandom).getRow(); r--) {
                            getCell(r, col).assignChemin();
                        }
                        break;
                    }
                    default:
                        throw new IllegalArgumentException("Positions non alignées !");
                }
            }
            // enlever la premiere et la derniere position du chemin de listPositions
            rmPositions(pRandom, getFinChemin(pRandom), listPositions, width, height);
            // enregistrer la premiere et la derniere postions dans chemins
            chemins.put(pRandom, getFinChemin(pRandom));
            // enregistrer le numero de ce chemin
            numChemins.put(cmp, pRandom);
            cmp++;

        }

    }

     /* selon le numero inserer on va retourner le debut et la fin du chemin
     on a utiliser un tableau de position car c'est plus facile  */
 public Position[] retourneLesChemins(int num) {
             //on verifie si num nest pas negatif ou depasse le compteur
    if (num >= 0 && num < cmp) {
        Position debut = numChemins.get(num);
        Position fin = chemins.get(debut);
             //on retourne le couple debut, fin de positions 
        return new Position[]{debut, fin};
    } else {
        throw new IllegalArgumentException("numero invalide");
    }
}

    public int retourneNbrChemins() {
        return cmp;
    }

    public Map<Position, Position> getCheminsMap() {
        return chemins;
    }

}
