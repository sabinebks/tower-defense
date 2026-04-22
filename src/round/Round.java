package round;

import java.util.ArrayList;
import java.util.List;

import entity.balloon.Balloon;

/**
 * round
 */
public class Round {
    /**
     * Number of the round
     * List of balloon in the current round
     * State
     * List of escaped balloons
     */
    private int number;
    private List<Balloon> balloons;
    private boolean state;
    private List<Balloon> escapedBalloons = new ArrayList<>();

    /**
     * constructor
     * @param number number
     * @param balloons balloons
     */
    public Round(int number, List<Balloon> balloons) {
        this.number = number;
        this.balloons = balloons;
        this.state = false;
    }

    /**
     * get number
     * @return number
     */
    public int getNumber() {
        return number;
    }

    /**
     * get balloons
     * @return balloons list
     */
    public List<Balloon> getBalloons() {
        return balloons;
    }

    /**
     * get state
     * @return state
     */
    public boolean getState() {
        return this.state;
    }

    /**
     * add escaped balloon
     * @param b balloon
     */
    public void addEscaped(Balloon b) {
        escapedBalloons.add(b);
    }

    /**
     * get escaped balloons
     * @return escaped list
     */
    public List<Balloon> getEscapedBalloons() {
        return escapedBalloons;
    }
}