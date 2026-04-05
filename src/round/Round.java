package round;

import object.balloon.Balloon;

import java.util.List;

public class Round {
    private int number;
    private List<Balloon> balloons;

    public Round(int number, List<Balloon> balloons) {
        this.number = number;
        this.balloons = balloons;
    }

    public int getNumber() {
        return number;
    }

    public List<Balloon> getBalloons() {
        return balloons;
    }
}