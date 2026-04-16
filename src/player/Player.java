package player;

import choose.action.Action;
import choose.action.ActionBuilder;
import choose.action.DoNothingAction;
import game.GameContext;
import choose.ListChooser;
import entity.tower.Tower;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the player in the game.
 * Manages credits, towers, lives, and action choices.
 */
public class Player {

    /** Initial number of lives for the player. */
    public static final int INITIAL_LIVES = 20;

    private int credit;
    private final List<Tower> towers;
    private final ListChooser<Action> actionChooser;

    /** Number of lives remaining. One life is lost per balloon that escapes. */
    private int lives;

    /**
     * Creates a new Player with a given credit amount and action chooser.
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
     * @return true if lives is greater than 0, false otherwise
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
    }
}
