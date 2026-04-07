package board;

import entity.balloon.Balloon;
import entity.tower.Tower;
import entity.tower.projectil.NotMatchException;
import entity.tower.projectil.Projectil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Board {

    protected int width;
    protected int height;
    protected Cell[][] grid;
    protected Map<Integer, List<Cell>> allPath;
    protected List<Tower> towers;
    protected List<Balloon> balloons;
    protected List<Projectil> activeProjectiles;
    protected List<Balloon> escapedBalloons = new ArrayList<>();
    protected List<Cell> allCells;

    public Board(int height, int width) {
        this.width = width;
        this.height = height;
        this.allPath = new HashMap<>();
        this.towers = new ArrayList<>();
        this.balloons = new ArrayList<>();
        this.activeProjectiles = new ArrayList<>();
        this.allCells = new ArrayList<>();
        init();
    }

    public abstract void calculerPath();
    public abstract void init();
    public abstract List<Position> getPathPositions();
    public abstract List<Cell> allCells();

    public Cell getCell(int row, int col) {
        if (!isInside(row, col))
            throw new IllegalArgumentException("Coordonnees hors du plateau");
        return this.grid[row][col];
    }

    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public Cell[][] getGrid() { return this.grid; }
    public Map<Integer, List<Cell>> getAllPath() { return this.allPath; }
    public List<Projectil> getActiveProjectiles() { return activeProjectiles; }

    public boolean isInside(int row, int col) {
        return row >= 0 && row < height && col >= 0 && col < width;
    }

    public void placeTower(Tower tower, Position position) {
        tower.setPosition(position);
        towers.add(tower);
    }

    public void removeTower(Tower tower) {
        towers.remove(tower);
    }

    public void spawnBalloon(Balloon balloon) {
        List<Position> path = balloon.getPath();

        balloon.setPath(getPathPositions());
        path = balloon.getPath();

        if (path != null && !path.isEmpty()) {
            Position start = path.get(0);
            balloon.setStartPosition(start.getRow() + 0.5, start.getCol() + 0.5);
        }
        balloons.add(balloon);
    }

    public List<Tower> getTowers() { return towers; }
    public List<Balloon> getBalloons() { return balloons; }


    public List<Balloon> update() {

        // 1. Les tours evaluent et tirent sur les ballons a leur position actuelle
        for (Tower tower : towers) {
            try {
                tower.update(this.balloons, this.activeProjectiles);
            } catch (NotMatchException e) {
                e.getMessage();
            }
        }

        for (Projectil projectil : new ArrayList<>(this.activeProjectiles)) {
            projectil.update();
        }
        this.activeProjectiles.removeIf(p -> !p.isActive());

        for (Balloon balloon : new ArrayList<>(balloons)) {
            balloon.update();
            balloon.move();
        }

        List<Balloon> toRemove = new ArrayList<>();
        for (Balloon balloon : this.balloons) {
            if (balloon.getHealth() <= 0 || !balloon.isActive()) {
                if (balloon.hasEscaped()) {
                    escapedBalloons.add(balloon);
                }
                toRemove.add(balloon);
            }
        }
        this.balloons.removeAll(toRemove);

        return toRemove;
    }

    public abstract boolean isCellAvailable(int row, int col);

    public List<Balloon> getEscapedBalloons() { return new ArrayList<>(escapedBalloons); }
    public void clearEscapedBalloons() { escapedBalloons.clear(); }

}