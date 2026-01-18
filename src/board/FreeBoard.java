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
}
