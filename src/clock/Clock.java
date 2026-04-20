package clock;

import java.util.ArrayList;
import java.util.List;

/**
 * clock
 */
public class Clock {

    /**
     * listeners list
     * fps value
     * tick counter
     * running state
     * thread
     */
    private final List<ClockListener> listeners = new ArrayList<>();
    private final int fps;
    private int tickCount = 0;
    private boolean running = false;
    private Thread thread;

    /**
     * constructor
     * @param fps ticks per second
     */
    public Clock(int fps) {
        this.fps = fps;
    }

    /**
     * add listener
     * @param listener listener to add
     */
    public void addListener(ClockListener listener) {
        listeners.add(listener);
    }

    /**
     * remove listener
     * @param listener listener to remove
     */
    public void removeListener(ClockListener listener) {
        listeners.remove(listener);
    }

    /**
     * reset tick counter
     */
    public void reset() {
        tickCount = 0;
    }

    /**
     * start clock
     */
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
                    try {
                        Thread.sleep(sleep);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        });

        thread.setDaemon(false);
        thread.start();
    }

    /**
     * stop clock
     */
    public void stop() {
        running = false;

        if (thread != null) {
            try {
                thread.join(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * get tick count
     * @return tick count
     */
    public int getTickCount() {
        return tickCount;
    }

    /**
     * get fps
     * @return fps
     */
    public int getFps() {
        return fps;
    }
}