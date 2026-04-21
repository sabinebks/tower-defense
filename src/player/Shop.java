package player;

import java.util.ArrayList;
import java.util.List;

import board.Board;
import entity.tower.BombTower;
import entity.tower.DartMonkey;
import entity.tower.EliteSniper;
import entity.tower.Gorilla;
import entity.tower.IceTower;
import entity.tower.SlowTower;
import entity.tower.TackShooter;
import entity.tower.Tower;

/**
 * shop
 */
public class Shop {

    private Board board;

    /**
     * constructor
     * @param board board
     */
    public Shop(Board board) {
        this.board = board;
    }

    /**
     * get available towers
     * @return towers list
     */
    public List<Tower> getAvailableTowers() {
        List<Tower> towers = new ArrayList<>();

        towers.add(new DartMonkey(null));
        towers.add(new BombTower(null));
        towers.add(new IceTower(null));
        towers.add(new SlowTower(null));
        towers.add(new TackShooter(null));
        towers.add(new EliteSniper(null));
        towers.add(new Gorilla(null));

        return towers;
    }

    /**
     * get board
     * @return board
     */
    public Board getBoard() {
        return this.board;
    }
}