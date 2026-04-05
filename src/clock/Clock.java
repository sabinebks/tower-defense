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

    public void reset() {
        tickCount = 0;
    }

    public void start() {
        running = true;
        thread = new Thread(() -> {
            long delay = 1000L / fps;
            while (running) {
                long start = System.currentTimeMillis();
                tickCount++;
                for (ClockListener l : new ArrayList<>(listeners)) {
                    l.onTick(tickCount);
                }
                long elapsed = System.currentTimeMillis() - start;
                long sleep = delay - elapsed;
                if (sleep > 0) {
                    try { Thread.sleep(sleep); }
                    catch (InterruptedException e) { Thread.currentThread().interrupt(); }
                }
            }
        });
        thread.setDaemon(false);
        thread.start();
    }

    public void stop() {
        running = false;
        if (thread != null) {
            try { thread.join(500); }
            catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }
    }

    public int getTickCount() { return tickCount; }
    public int getFps() { return fps; }
}
