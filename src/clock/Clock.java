package clock;

import javax.swing.Timer;

public class Clock {
    /**
     * Les attributs de la classe Clock
     * timer le temps 
     */
    private Timer timer;
    /**
     * Constructeur de Clock
     * @param game
     */
    public Clock(Game game) {
        timer = new Timer(50, e -> game.gameTick());
    }

    /**
     * Methode pour commencer le timer
     */
    public void start() {
        timer.start();
    }
}
