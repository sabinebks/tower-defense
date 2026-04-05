package clock;

import java.util.ArrayList;
import java.util.List;

public class Clock {
    private final List<Clock> listeners = new ArrayList<>();
    private final int fps;
    private int tickCount = 0;
    private boolean running = false;
    private Thread thread;

    public Clock(int fps) {
        this.fps = fps;
    }
}
