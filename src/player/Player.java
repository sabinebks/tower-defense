package player;

import choose.action.Action;
import choose.action.ActionBuilder;
import choose.action.DoNothingAction;
import game.GameContext;
import choose.ListChooser;
import entity.tower.Tower;

import java.util.ArrayList;
import java.util.List;
import choose.action.Action;
import entity.tower.*;
import choose.ListChooser;

<<<<<<< HEAD


=======
>>>>>>> origin/main
/**
 * Represents the player in the game.
 * Manages credits, towers, lives, and action choices.
 */
public class Player {
<<<<<<< HEAD
    public static final int CREDIT_INITIAL = 2500;
    public static final int VIE_INIT = 20;
    public static final double REVENDRE = 0.25;
    private ListChooser<Action> actionChooser;
    private int vie;
    private int credit;
    private List<Tower> towers;
  
    
        /**
        * Constructeur de la classe
        * @param actionChooser le type de list chooser pour choisir les actions du joueur
        */
    public Player(ListChooser<Action> actionChooser) {
    this.credit = CREDIT_INITIAL;
    this.vie = VIE_INIT;
    this.actionChooser = actionChooser;
    this.towers = new ArrayList<>();
}
  
=======

    /** Initial number of lives for the player. */
    public static final int INITIAL_LIVES = 20;

    private int credit;
    private final List<Tower> towers;
    private final ListChooser<Action> actionChooser;

    /** Number of lives remaining. One life is lost per balloon that escapes. */
    private int lives;
>>>>>>> origin/main

    /**
     * Creates a new Player with a given credit amount and action chooser.
     * Lives are initialized to {@value #INITIAL_LIVES}.
     *
     * @param credit  Starting credits
     * @param chooser The action chooser (human or automatic)
     */
    public Player(int credit, ListChooser<Action> chooser) {
        this.credit = credit;
        this.lives = INITIAL_LIVES;
        this.towers = new ArrayList<>();
        this.actionChooser = chooser;
    }

    /**
     * Attempts to buy a tower if the player has enough credits.
     *
     * @param tower The tower to buy
     */
    public void buyTower(Tower tower) {
        if (credit >= tower.getCost()) {
            towers.add(tower);
            credit -= tower.getCost();
        }
    }

    /**
     * Sells a tower and refunds the sell price to the player's credits.
     *
     * @param tower The tower to sell
     */
    public void sellTower(Tower tower) {
        if (towers.contains(tower)) {
            towers.remove(tower);
            credit += tower.getSellPrice();
        }
    }

    /**
     * Asks the action chooser to select an action from the given list.
     * Does not execute the action.
     *
     * @param actions List of available actions
     * @param ctx     The current game context
     * @return The chosen action
     */
    public Action chooseAction(List<Action> actions, GameContext ctx) {
        return actionChooser.choose("Choisissez une action :", actions);
    }

    /**
<<<<<<< HEAD
     * retirer une tour de la liste des tours du joueur
     * @param tower
     */
    public void removeTower(Tower tower) {
        this.towers.remove(tower);
    }

    /**
     * choisir une action parmi une liste d'actions
     * @param actions
     * @return l'action choisie
     */
    public Action choisirAction(List<Action> actions) {
        return actionChooser.choose("Choisissez une action :", actions);
=======
     * Runs the full action phase: loops until the player chooses "Do nothing".
     *
     * @param ctx  The current game context
     * @param shop The shop used to build available actions
     */
    public void runActionPhase(GameContext ctx, Shop shop) {
        boolean done = false;
        while (!done) {
            System.out.println("\nCredits : " + credit
                    + " | Tours : " + towers.size()
                    + " | Vies : " + lives);
            List<Action> menu = ActionBuilder.buildMenu(this, shop);
            Action chosen = chooseAction(menu, ctx);
            chosen.execute(ctx);
            if (chosen instanceof DoNothingAction) done = true;
        }
    }

    /**
     * Decrements the player's lives by 1 when a balloon escapes.
     * Prints a message indicating the life loss.
     */
    public void loseLife() {
        if (lives > 0) {
            lives--;
            System.out.println("!!! Un ballon a quitté le plateau ! Vies restantes : " + lives);
        }
    }

    /**
     * Returns whether the player is still alive (has at least 1 life).
     *
     * @return true if lives > 0, false otherwise
     */
    public boolean isAlive() {
        return lives > 0;
    }

    /**
     * Returns the current number of lives.
     *
     * @return current lives
     */
    public int getLives() { return lives; }

    /**
     * Sets the lives to a specific value (used for testing or resets).
     *
     * @param lives New lives value
     */
    public void setLives(int lives) { this.lives = lives; }

    /** @return current credit amount */
    public int getCredit() { return credit; }

    /**
     * Adds credits to the player's total.
     *
     * @param amount Amount to add
     */
    public void addCredit(int amount) { credit += amount; }

    /**
     * Removes credits from the player's total.
     *
     * @param amount Amount to remove
     */
    public void removeCredit(int amount) { credit -= amount; }

    /** @return the list of towers owned by the player */
    public List<Tower> getTowers() { return towers; }

    /**
     * Sets the player's credit directly.
     *
     * @param i New credit value
     */
    public void setCredit(int i) {
        this.credit = i;
>>>>>>> origin/main
    }
}
