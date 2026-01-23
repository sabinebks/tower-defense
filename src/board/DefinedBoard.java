package board;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Plateau avec un chemin généré aléatoirement.
 * Ici je fais simple : le chemin peut aller haut, bas, gauche, droite
 * mais chaque cellule est utilisée une seule fois pour éviter les boucles.
 * C’est pas parfait mais ça marche pour le livrable 1.
 */
public class DefinedBoard extends Board {

    /**
     * L'attribut de DefinedBoard
     * Une liste des cellules chemins
     */
    private List<Cell> chemins;

    // constructeur du plateau
    public DefinedBoard(int height, int width) {
        super(height, width); // appel du constructeur de Board pour la taille
    }

    /**
     * Méthode pour initialiser le plateau.
     * Ici je crée juste la grille de cellules et après je lance la création du chemin
     */
    @Override
    protected void init() {
        // première étape : créer la grille
        chemins = new ArrayList<>();
        grid = new Cell[height][width]; // tableau 2D
        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                // chaque cellule a sa position
                grid[r][c] = new Cell(new Position(r, c));
            }
        }

        // maintenant qu’on a la grille, on calcule le chemin
        calculerPath();
    }

    /**
     * Une methode pour recupperer la liste des cellules chemins
     * @return la liste des chemins
     */
    public List<Cell> getChemins() {
        return this.chemins;
    } 

    /**
     * Retourner le nombre de cellule chemin
     * @return nombre de cellule chemin
     */
    public int getCheminLong() {
        return this.chemins.size();
    }



    /**
     * Génération d’un chemin aléatoire.
     * Le chemin commence sur le bord gauche et avance dans toutes les directions
     * On va essayer a choisir un Cell de debut dans toute les bords ( en haut, en bas,
     * a gauche, a droite )
     */
    public void calculerPath() {
        Random rand = new Random(); // pour choisir les directions aléatoirement

        // point de départ sur le bord gauche
        int row = rand.nextInt(height); // ligne aléatoire
        int col = 0; // bord gauche fixe, on va essayer a creer un chemin commencer aleatoirement dans 4 bords
        
        Cell cellDebut = this.getCell(row, col);
        cellDebut.assignChemin(); // je marque la première cellule comme chemin
        this.chemins.add(cellDebut);
        /* 
        boolean fin = false; // variable pour savoir quand arrêter

        //  boucle pour générer le chemin
        while (!fin) {

            // je fais une liste pour stocker toutes les cellules voisines possibles
            List<int[]> voisins = new ArrayList<>();

            // vérifier chaque direction pour savoir si je peux y aller
            // haut
            if (row > 0 && getCell(row - 1, col).estVide()) {
                voisins.add(new int[]{row - 1, col});
            }
            // bas
            if (row < height - 1 && getCell(row + 1, col).estVide()) {
                voisins.add(new int[]{row + 1, col});
            }
            // gauche
            if (col > 0 && getCell(row, col - 1).estVide()) {
                voisins.add(new int[]{row, col - 1});
            }
            // droite
            if (col < width - 1 && getCell(row, col + 1).estVide()) {
                voisins.add(new int[]{row, col + 1});
            }

            // si je n’ai pas de voisin valide, je sors de la boucle
            if (voisins.isEmpty()) {
                fin = true;
                break;
            }

            // je choisis un voisin au hasard
            int[] next = voisins.get(rand.nextInt(voisins.size()));
            row = next[0];
            col = next[1];

            // marquer cette cellule comme faisant partie du chemin
            Cell chemin = getCell(row, col);
            chemin.assignChemin();
            this.chemins.add(chemin);
            if ( col == width - 1) {
                fin = true;
            }  */

            // chaque cellule visitée une seule fois

            while ( col < width - 1) {
                int nextRowCell = rand.nextInt(height); // Cela va choisir le cell de la prochaine colonne pour generer le chemin
                // le chemin va etre generer verticalement vers nextRowCell
                while ( row != nextRowCell) {
                    row += ( nextRowCell > row) ? 1 : -1; // si la ligne est en haut donc - 1 sinon +1
                    Cell cell = getCell(row, col);
                    cell.assignChemin();
                    chemins.add(cell);
                }

                //apres avoir fini de generer verticalement, on avance vers la droite
                col += 1;
                Cell newCell = getCell(row, col);
                newCell.assignChemin();
                chemins.add(newCell);
            }
        }
}

