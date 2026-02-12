package game;

import javax.swing.Timer;

public class Clock {

    private Timer timer;

    public Clock(Game game) {
        timer = new Timer(50, e -> game.gameTick());
    }

    public void start() {
        timer.start();
    }
}
