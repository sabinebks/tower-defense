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
    public void calculerPath(){
        
        Random rand = new Random();
        int row = rand.nextInt(width);
        int col = rand.nextInt(height);
        
        if(row == 0 && col == 0 || row == 0 && col == width - 1 || row == height -1 && col == 0 || row == height - 1 && col == width -1 ){
            int d = rand.nextInt(2);
            if( row == 0 && col == 0){
                if( d == 0){
                    for(int j = 0; j < width; j++){
                        Cell cell = new Cell(new Position(0, j));
                        cell.assignChemin();
                        grid[0][j] = cell;
                    }
                    }else{
                    for(int i = 0; i < height; i++){
                        Cell cell = new Cell(new Position(i, 0));
                        cell.assignChemin();
                        grid[i][0] = cell;
                    }
                }
            } else if( row == 0 && col == width - 1 ){
                if( d == 0){
                    for(int j = width - 1; j >= 0; j--){
                        Cell cell = new Cell(new Position(0, j));
                        cell.assignChemin();
                        grid[0][j] = cell;
                    }
                    }else{
                    for(int i = 0; i < height; i++){
                        Cell cell = new Cell(new Position(i, 0));
                        cell.assignChemin();
                        grid[i][0] = cell;
                    }
                }
            }
        }

    }
}