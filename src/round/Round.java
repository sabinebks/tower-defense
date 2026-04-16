package round;

import java.util.ArrayList;
import java.util.List;

import entity.balloon.Balloon;

public class Round {
    /**
     * Les attributs de round
     * Le nombre de ce round
     * La liste des ballons
     * Un state
     */
    private int number;
    private List<Balloon> balloons;
    private boolean state;

    /**
     * Constructeur
     * @param number le nombre de ce round
     * @param balloons liste de ballons
     */
    public Round(int number, List<Balloon> balloons) {
        this.number = number;
        this.balloons = balloons;
        this.state = false;
    }

    /**
     * Retourner le nombre de ce round
     * @return nombre de ce round
     */
    public int getNumber() {
        return number;
    }

    /**
     * Retourner la liste des ballons
     * @return liste des ballons
     */
    public List<Balloon> getBalloons() {
        return balloons;
    }

    /**
     * retourner le state
     * @return le state
     */
    public boolean getState(){
        return this.state;
    }

    private List<Balloon> escapedBalloons = new ArrayList<>();

    public void addEscaped(Balloon b) {
        escapedBalloons.add(b);
    }

    public List<Balloon> getEscapedBalloons() {
        return escapedBalloons;
    }
}