package clock;

import java.util.ArrayList;
import java.util.List;

public class Clock {
    private final List<ClockListener> listeners = new ArrayList<>();
    private final int fps;
    private int tickCount = 0;
    private boolean running = false;
    private Thread thread;

    public Clock(int fps) {
        this.fps = fps;
    }

    public void addListener(ClockListener listener) { listeners.add(listener); }
    public void removeListener(ClockListener listener) { listeners.remove(listener); }
}
