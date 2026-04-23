package game;

import board.Board;
import board.Position;
import board.typeOfBoard.DefinedBoard;
import board.typeOfBoard.FreeBoard;
import choose.AutoListChooser;
import choose.HumanListChooser;
import choose.ListChooser;
import choose.action.Action;
import entity.balloon.Balloon;
import entity.balloon.BalloonType;
import player.Player;
import player.Shop;
import round.Round;

import java.util.ArrayList;
import java.util.List;

/**
 * Initialise tous les composants du jeu (board, player, shop, rounds, game).
 */
public class GameSetup {

    private static final int NB_ROUNDS  = 10;
    private static final int FPS = 3;

    /**
     * The board of the game
     * The player
     * The games shop
     * The game
     * Numbers of balloons
     * Verify if human/bot is playing
     */
    private final Board board;
    private final Player player;
    private final Shop shop;
    private final Game game;
    private final int nbBallons;
    private final boolean isHuman;
    /**
     * Constructeur
     * @param hauteur height of board
     * @param largeur width of board
     * @param nbBallons numbers of balloons
     * @param typePlateau type of board
     * @param typeJoueur the games mode
     */
    public GameSetup(int hauteur, int largeur, int nbBallons,
                     String typePlateau, String typeJoueur) {

        this.nbBallons = nbBallons;
        this.isHuman   = typeJoueur.equalsIgnoreCase("humain");

        this.board = typePlateau.equalsIgnoreCase("libre")
                ? new FreeBoard(hauteur, largeur, nbBallons)
                : new DefinedBoard(hauteur, largeur);

        ListChooser<Action>   chooser          = isHuman ? new HumanListChooser<>() : new AutoListChooser<>();
        ListChooser<Position> positionChooser  = isHuman ? new HumanListChooser<>() : new AutoListChooser<>();

        this.player = new Player(1000, chooser, positionChooser);
        this.shop   = new Shop(board);

        List<Round> rounds = new ArrayList<>();
        BalloonType[] types = BalloonType.values();
        List<Position> path = board.getPathPositions();
        for (int i = 1; i <= NB_ROUNDS; i++) {
            BalloonType type = types[(i - 1) % types.length];
            rounds.add(new Round(i, makeBalloons(this.nbBallons, type, path)));
        }

        this.game = new Game(player, board, rounds, FPS);
    }

    /**
     * Return a list of created balloons
     * @param count numbers of balloons to create
     * @param type type of balloons
     * @param path theirs path
     * @return a list of created balloons
     */
    private List<Balloon> makeBalloons(int count, BalloonType type, List<Position> path) {
        List<Balloon> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Balloon b = new Balloon(i,
                    path.get(0).getRow() + 0.5,
                    path.get(0).getCol() + 0.5,
                    type);
            b.setPath(new ArrayList<>(path));
            list.add(b);
        }
        return list;
    }

    /**
     * Board getter
     * @return board
     */
    public Board  getBoard()   { return board;   }
    /**
     * Player getter
     * @return player
     */
    public Player getPlayer()  { return player;  }
    /**
     * Shop getter
     * @return shop
     */
    public Shop   getShop()    { return shop;    }
    /**
     * Game getter
     * @return game
     */
    public Game   getGame()    { return game;    }
    /**
     * Verify if is human or not
     * @return true if human, false if not
     */
    public boolean isHuman()   { return isHuman; }
}