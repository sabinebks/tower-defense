package game;
import java.util.*;
import object.tower.*;
/**
 * Une class player pour gerer les actions du joueurs
 */
public class Player {
    /**
     * Les attributs du joueurs
     * Le nombre de cerdit, init en 2500
     * Le nombre de vie, init en 20
     * Sa liste des tours 
     */
    public static final int CREDIT_INITIAL = 2500;
    public static final int VIE_INIT = 20;
    public static final double REVENDRE = 0.25;
    private int credit;
    private int vie;
    private List<Tower> towers;
    public Player () {
        this.credit = CREDIT_INITIAL;
        this.vie = VIE_INIT;
        this.towers = new ArrayList<>();
    }
}
