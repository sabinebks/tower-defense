package round;

import java.util.List;

import entity.balloon.Balloon;

public class Round {
    private int number;
    private List<Balloon> balloons;
    private boolean state;

    public Round(int number, List<Balloon> balloons) {
        this.number = number;
        this.balloons = balloons;
        this.state = false;
    }

    public int getNumber() {
        return number;
    }

    public List<Balloon> getBalloons() {
        return balloons;
    }

    public boolean getState(){
        return this.state;
    }
}