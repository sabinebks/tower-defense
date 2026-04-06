package player;

import java.util.ArrayList;
import java.util.List;

import object.tower.*;
import board.Board;

public class Shop {
    private Board board;

    public Shop(Board board) {
        this.board = board;
    }

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
}
