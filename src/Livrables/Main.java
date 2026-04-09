/*package Livrables;

import board.Board;
import board.Position;
import board.typeOfBoard.FreeBoard;
import choose.AutoListChooser;
import choose.HumanListChooser;
import choose.ListChooser;
import choose.action.Action;
import game.Game;
import round.Round;
import entity.balloon.Balloon;
import entity.balloon.BalloonType;
import player.Player;
import player.Shop;

import java.util.ArrayList;
import java.util.List;

public class Main {

    private static final int REWARD_PER_ROUND = 150;

    private final Board board;
    private final Player player;
    private final Shop shop;
    private final Game game;
    private final List<List<Position>> allPaths;

    public Main(Board board, String mode) {
        ListChooser<Action> chooser = mode.equals("auto")
                ? new AutoListChooser<>()
                : new HumanListChooser<>();

        this.board  = board;
        this.player = new Player(1000, chooser);
        this.shop   = new Shop(board);
        this.allPaths = getAllPaths(board);

        List<Round> rounds = List.of(
                new Round(1, makeBalloons(3, BalloonType.BASIC,  allPaths)),
                new Round(2, makeBalloons(4, BalloonType.MEDIUM, allPaths)),
                new Round(3, makeBalloons(3, BalloonType.STRONG, allPaths))
        );

        this.game = new Game(player, board, rounds, 3);
    }

    private List<List<Position>> getAllPaths(Board board) {
        List<List<Position>> result = new ArrayList<>();

        if (board instanceof FreeBoard) {
            FreeBoard fb = (FreeBoard) board;
            int nb = fb.retourneNbrChemins();
            for (int i = 0; i < nb; i++) {
                Position[] ext = fb.retourneLesChemins(i);
                List<Position> path = buildPath(ext[0], ext[1]);
                if (!path.isEmpty()) result.add(path);
            }
        }

        if (result.isEmpty()) {
            result.add(board.getPathPositions());
        }

        return result;
    }

    private List<Position> buildPath(Position start, Position end) {
        List<Position> path = new ArrayList<>();
        if (start.getRow() == end.getRow()) {
            int step = start.getCol() < end.getCol() ? 1 : -1;
            for (int c = start.getCol(); c != end.getCol() + step; c += step)
                path.add(new Position(start.getRow(), c));
        } else {
            int step = start.getRow() < end.getRow() ? 1 : -1;
            for (int r = start.getRow(); r != end.getRow() + step; r += step)
                path.add(new Position(r, start.getCol()));
        }
        return path;
    }

    private List<Balloon> makeBalloons(int count, BalloonType type, List<List<Position>> allPaths) {
        List<Balloon> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            List<Position> path = allPaths.get(i % allPaths.size());
            Balloon b = new Balloon(i,
                    path.get(0).getRow() + 0.5,
                    path.get(0).getCol() + 0.5,
                    type);
            b.setPath(new ArrayList<>(path));
            list.add(b);
        }
        return list;
    }

    public void start() {
        // Afficher les chemins disponibles
        System.out.println("=== Chemins disponibles (" + allPaths.size() + ") ===");
        for (int i = 0; i < allPaths.size(); i++) {
            List<Position> p = allPaths.get(i);
            System.out.println("  Chemin " + i + ": "
                    + p.get(0) + " → " + p.get(p.size()-1)
                    + " (" + p.size() + " cases)");
        }
        System.out.println("PHASE D'ACHAT");
        player.runActionPhase(game, shop);

        boolean gameOver = false;
        int roundCount = game.getRoundCount();

        for (int i = 0; i < roundCount && !gameOver; i++) {
            System.out.println("\n=== Round " + (i + 1) + " ===");
            game.runOneRound();

            if (game.hasPlayerLost()) {
                System.out.println(" GAME OVER ");
                gameOver = true;
                break;
            }

            player.addCredit(REWARD_PER_ROUND);
            System.out.println(">>> Récompense : +" + REWARD_PER_ROUND
                    + " crédits ! Total : " + player.getCredit());

            if (i < roundCount - 1) {
                System.out.println("=== Phase d'action ===");
                player.runActionPhase(game, shop);
            }
        }

        if (!gameOver) {
            System.out.println(" VICTOIRE TOTALE ");
        }
    }
}*/