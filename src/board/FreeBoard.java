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
import java.util.List;
import java.util.Random;

public class FreeBoard extends Board{

    public FreeBoard( int height, int width ) {
        super(height, width);
    }

    @Override
    public void init() {
        grid = new Cell[height][width]; 
        for (int i = 0; i < height; i++) {
            for (int j = 0;j  < width; j++) {
                grid[i][j] = new Cell(new Position(i, j));
            }
        }
    }

    @Override
    public void calculerPath() {

        Random rand = new Random();

        int row, col;

        // ───── Choisir une cellule aléatoire de la bordure ─────
        int side = rand.nextInt(4);

        switch (side) {
            case 0: // haut
                row = 0;
                col = rand.nextInt(width);
            break;
            case 1: // bas
                row = height - 1;
                col = rand.nextInt(width);
            break;
            case 2: // gauche
                row = rand.nextInt(height);
                col = 0;
            break;
            default: // droite
                row = rand.nextInt(height);
                col = width - 1;
    }

    // ───── Déterminer la direction ─────
    int dRow = 0;
    int dCol = 0;

    // COINS
    if (row == 0 && col == 0) {
        if (rand.nextInt(2) == 0) { dRow = 1; dCol = 0; }   // ↓
        else { dRow = 0; dCol = 1; }                       // →
    }
    else if (row == 0 && col == width - 1) {
        if (rand.nextInt(2) == 0) { dRow = 1; dCol = 0; }  // ↓
        else { dRow = 0; dCol = -1; }                      // ←
    }
    else if (row == height - 1 && col == 0) {
        if (rand.nextInt(2) == 0) { dRow = -1; dCol = 0; } // ↑
        else { dRow = 0; dCol = 1; }                       // →
    }
    else if (row == height - 1 && col == width - 1) {
        if (rand.nextInt(2) == 0) { dRow = -1; dCol = 0; } // ↑
        else { dRow = 0; dCol = -1; }                      // ←
    }

    // BORDURES (hors coins)
    else if (row == 0) { dRow = 1; dCol = 0; }             // ↓
    else if (row == height - 1) { dRow = -1; dCol = 0; }  // ↑
    else if (col == 0) { dRow = 0; dCol = 1; }             // →
    else if (col == width - 1) { dRow = 0; dCol = -1; }    // ←

    // ───── Tracer la trajectoire ─────
    int r = row;
    int c = col;

    while (r >= 0 && r < height && c >= 0 && c < width) {
        grid[r][c].assignChemin();
        r += dRow;
        c += dCol;
    }
    }

}