package game;

import object.balloon.Balloon;
import object.tower.Tower;

import java.util.ArrayList;

public class Round {

    private int numRound;
    private ArrayList<Balloon> balloons;
    private ArrayList<Tower> towers;

    public Round(int numRound) {
        this.numRound = numRound;
        this.balloons = new ArrayList<>();
        this.towers = new ArrayList<>();
    }

    //getters

    public int getNumRound() {
        return numRound;
    }

    public ArrayList<Balloon> getBalloons() {
        return balloons;
    }

    public ArrayList<Tower> getTowers() {
        return towers;
    }

    //methodes

    public void addBalloon(Balloon balloon) {
        this.balloons.add(balloon);
    }

    public void addTower(Tower tower) {
        this.towers.add(tower);
    }

    public void removeBalloon(Balloon balloon) {
        this.balloons.remove(balloon);
    }

    public void removeTower(Tower tower) {
        this.towers.remove(tower);
    }

    public boolean estFinie(){
        if(balloons.isEmpty() && towers.isEmpty()){
            return true;
        }

        return false;
    }
}
