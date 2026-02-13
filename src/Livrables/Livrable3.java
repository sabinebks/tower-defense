package Livrables;
import board.typeOfBoard.*;
import game.*;

public class Livrable3 {
    public static void main(String[] args) {
        // nitialisation du plateau (juste un exemple 10*10)
        DefinedBoard board = new DefinedBoard(10, 10);

        // initialisation du jeu
        Game game = new Game(board);
        game.play();

    
    }
}