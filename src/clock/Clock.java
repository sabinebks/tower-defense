package clock;

import java.util.ArrayList;
import java.util.List;

public class Clock {
    private final List<ClockListener> listeners = new ArrayList<>();
    private final int fps;
    private int tickCount = 0;
    private boolean running = false;
    private Thread thread;
  /* Constructeur de la classe Clock.
     * @param fps le nombre de ticks par seconde
     */
    public Clock(int fps) {
        this.fps = fps;
    }
 /* Ajoute un écouteur au horloge.
     * @param listener l'écouteur à ajouter
     */
    public void addListener(ClockListener listener) { listeners.add(listener); }
        /* Retire un écouteur du horloge.
        * @param listener l'écouteur à retirer
        */
    public void removeListener(ClockListener listener) { listeners.remove(listener); }
    /* Réinitialise le compteur de ticks à zéro.
     */
    public void reset() {
        tickCount = 0;
    }
/* Démarre le horloge.
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
                    try { Thread.sleep(sleep); }
                    catch (InterruptedException e) { Thread.currentThread().interrupt(); }
                }
            }
        });
        thread.setDaemon(false);
        thread.start();
    }
/* Arrête le horloge.
     */
    public void stop() {
        running = false;
        if (thread != null) {
            try { thread.join(500); }
            catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }
    }
/* Retourne le nombre de ticks écoulés depuis le démarrage du horloge.
     * @return le nombre de ticks
     */
    public int getTickCount() { return tickCount; }
/* Retourne le nombre de ticks par seconde (FPS) du horloge.
     * @return le nombre de ticks par seconde
     */
    public int getFps() { return fps; }
}
