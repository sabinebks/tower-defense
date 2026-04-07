package player;

import java.util.ArrayList;
import java.util.List;

import board.*;
import entity.tower.*;

public class Shop {
    private Board board;

    /**
     * constructeur 
     * @param board
     */
    public Shop(Board board) {
        this.board = board;
    }

    /**
     * retourne la liste des tours qu'on peut acheter
     * @return liste des tours disponibles
     */
    public List<Tower> getAvailableTowers() {
        List<Tower> towers = new ArrayList<>();

        towers.add(new DartMonkey(null)); // pos null parce qu'elles ne sont pas enocre placees
        towers.add(new BombTower(null));
        towers.add(new IceTower(null));
        towers.add(new SlowTower(null));
        towers.add(new TackShooter(null));
        towers.add(new EliteSniper(null));
        towers.add(new Gorilla(null));

        return towers;

    }

    /**
     * retourne une position libre ou null sinon
     * @return une pos libre ou null sinon
     */
    public Position findAvailablePosition() {
        for (int i = 0; i < board.getHeight(); i++) {
            for (int j = 0; j < board.getWidth(); j++) {
                if (board.isCellAvailable(i, j)) {
                    return new Position(i, j);
                }
            }
        }
        return null;
    }
}

