package clock;

/**
 * clock listener interface
 */
public interface ClockListener {
    /**
     * called every tick of the clock
     * @param tickCount number of ticks passed
     */
    void onTick(int tickCount);
}