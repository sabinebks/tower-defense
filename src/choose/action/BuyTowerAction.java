package choose.action;


import board.Position;
import game.GameContext;
import player.Player;
import entity.tower.Tower;
import java.util.*;;


/**
 * buy tower action
 */
public class BuyTowerAction extends Action {
    /**
     * a scanner to scan input from keyboard
     * tower to buy
     * position to place it
     */
    private Scanner scanner = new Scanner(System.in);
    private Tower tower;
    private Position pos;

    /**
     * constructor
     * @param t the tower to buy
     * @param p the position to place it
     */
    public BuyTowerAction(Tower t, Position p) {
        this.tower = t;
        this.pos = p;
    }

    @Override
    /**
     * execute the action: buy and place the tower
     * @param game the game context
     */
    public void execute(GameContext game) {
        Player player = game.getPlayer();

        if (player.getCredit() < tower.getCost()) {
            System.out.println("Pas assez de credits");
            return;
        }
        Position p = this.pos;
        if (p == null) {
            p = getPos(game);
        }
        player.buyTower(this.tower);
        game.getBoard().placeTower(this.tower, p);
        this.pos = p;

        System.out.println(tower.getName() + " placee en " + p);
    }

    @Override
    /**
     * description of the action
     * @return text description
     */
    public String getDescription() {
        return "buy " + tower.getName() + " (" + tower.getCost() + " credits)";
    }

    /**
     * Fucntion that return a valid position to place the tower
     * @param game the game context
     * @return a valid position
     */
    public Position getPos(GameContext game) {
        List<Position> p = new ArrayList<>();
        for (int row = 0; row < game.getBoard().getHeight(); row++) {
            for (int col = 0; col < game.getBoard().getWidth(); col++) {
                if (game.getBoard().isCellAvailable(row, col)) {
                    p.add(new Position(row, col));
                }
            }
        }
        if (p.isEmpty()) {
            System.out.println("Aucune case pour la tour");
            return null;
        }
        System.out.println("Cases disponibles : " + p);
        while (true) {
            try {
                System.out.print("Ligne : ");
                int row = Integer.parseInt(scanner.nextLine());
                System.out.print("Colonne : ");
                int col = Integer.parseInt(scanner.nextLine());
                if (game.getBoard().isCellAvailable(row, col)) {
                    return new Position(row, col);
                }
                System.out.println("Case invalide");
            } catch (NumberFormatException e) {
                System.out.println("Entree invalide");
            }
        }
    }
}