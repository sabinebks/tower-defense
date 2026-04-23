package clock;

public interface ClockListener {
    /**
     * Appelé à chaque tick de l'horloge
     *
     * @param tickCount Le nombre de ticks écoulés
     */
    void onTick(int tickCount);
}
