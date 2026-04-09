package Livrables;

import board.Board;
import board.Position;
import board.typeOfBoard.DefinedBoard;
import board.typeOfBoard.FreeBoard;
import choose.AutoListChooser;
import choose.HumanListChooser;
import choose.ListChooser;
import choose.action.Action;
import choose.action.ActionBuilder;
import choose.action.Upgrade;
import choose.action.DownGrade;
import game.Game;
import entity.balloon.Balloon;
import entity.balloon.BalloonType;
import entity.tower.evolution.TypeEvolution;
import player.Player;
import player.Shop;
import round.Round;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Livrable5 {

    private static final int NB_ROUNDS = 10;
    private static final int REWARD_PER_ROUND = 150;
    private static final int FPS = 3;

    private final Board board;
    private final Player player;
    private final Shop shop;
    private final Game game;
    private final int nbBallons;
    private final boolean isHuman;
    private final Random rng = new Random();

    public Livrable5(int hauteur, int largeur, int nbBallons,
                     String typePlateau, String typeJoueur) {

        this.nbBallons = nbBallons;
        this.isHuman   = typeJoueur.equalsIgnoreCase("humain");

        this.board = typePlateau.equalsIgnoreCase("libre")
                ? new FreeBoard(hauteur, largeur)
                : new DefinedBoard(hauteur, largeur);

        ListChooser<Action> chooser = isHuman
                ? new HumanListChooser<>()
                : new AutoListChooser<>();

        this.player = new Player(1000, chooser);
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


    public List<Balloon> makeBalloons(int count, BalloonType type, List<Position> path) {
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


    public void start() {
        System.out.println("====================================");
        System.out.println("|        BLOONS TOWER DEFENSE       |");
        System.out.println("|  Vies : " + player.getLives() + " | Credits : " + player.getCredit() + "       |");
        System.out.println("====================================");

        if (!isHuman) {
            placeInitialTowers();
        }

        System.out.println("\n=== PHASE D'ACHAT INITIALE ===");
        player.runActionPhase(game, shop);

        int roundCount = game.getRoundCount();
        for (int i = 0; i < roundCount; i++) {
            int roundNum = i + 1;
            System.out.println("\n===========================");
            System.out.printf( "|        MANCHE %-2d         |%n", roundNum);
            System.out.println("===========================");
            System.out.println("Vies : " + player.getLives()
                    + " | Credits : " + player.getCredit());

            if (!isHuman) {
                applyRoundEvolution(roundNum);
            }


            game.runOneRound();

            if (game.hasPlayerLost()) {
                System.out.println("========================");
                System.out.println("|      GAME OVER !     |");
                System.out.println("|  Vies restantes : 0  |");
                System.out.println("========================");
                return;
            }

            if (game.getBoard().getEscapedBalloons().isEmpty()) {
                player.addCredit(REWARD_PER_ROUND);
                System.out.println(">>> Round parfait ! +" + REWARD_PER_ROUND
                        + " credits. Total : " + player.getCredit()
                        + " | Vies : " + player.getLives());
            } else {
                System.out.println(">>> " + game.getBoard().getEscapedBalloons().size() + " ballon(s) echappe(s) ce round."
                        + " Pas de recompense. Credits : " + player.getCredit()
                        + " | Vies : " + player.getLives());
            }

            if (i < roundCount - 1) {
                System.out.println("=== Phase d'action ===");
                player.runActionPhase(game, shop);
            }
        }

        System.out.println("========================");
        System.out.println("|  VICTOIRE TOTALE !   |");
        System.out.printf( "|  Vies restantes : %-2d |%n", player.getLives());
        System.out.println("========================");
    }


    public void placeInitialTowers() {
        System.out.println("[BOT] Placement initial de 2 tours de chaque type...");
        List<Action> actions = ActionBuilder.buildMenu(player, shop);
        for (int i = 0; i < 4; i++) {
            List<Action> menu = ActionBuilder.buildMenu(player, shop);
            if (!menu.isEmpty()) {
                menu.get(0).execute(game);
            }
        }
    }


    public void applyRoundEvolution(int roundNum) {
        if (roundNum <= 5) {
            List<Action> upgrades = new ArrayList<>();
            for (var tower : player.getTowers()) {
                if (!tower.getEvolution()) continue;
                var evolutions = tower.getEvolutions();
                if (evolutions == null) continue;
                for (TypeEvolution evo : evolutions.getEvolutions()) {
                    if (!evolutions.getEvolutionsPossedees().containsKey(evo)) {
                        int cost = tower.getCoutEvolution(evo);
                        if (player.getCredit() >= cost) {
                            upgrades.add(new Upgrade(tower, evo));
                        }
                    }
                }
            }
            if (!upgrades.isEmpty()) {
                Action chosen = upgrades.get(rng.nextInt(upgrades.size()));
                System.out.println("[BOT] Manche " + roundNum + " -> Application d'une evolution via action");
                chosen.execute(game);
            } else {
                System.out.println("[BOT] Manche " + roundNum + " -> Aucune evolution disponible a appliquer.");
            }
        } else {
            List<Action> sells = new ArrayList<>();
            for (var tower : player.getTowers()) {
                var evolutions = tower.getEvolutions();
                if (evolutions == null) continue;
                for (TypeEvolution evo : evolutions.getEvolutionsPossedees().keySet()) {
                    sells.add(new DownGrade(tower, evo));
                }
            }
            if (!sells.isEmpty()) {
                Action chosen = sells.get(rng.nextInt(sells.size()));
                System.out.println("[BOT] Manche " + roundNum + " -> Suppression d'une evolution via action");
                chosen.execute(game);
            } else {
                System.out.println("[BOT] Manche " + roundNum + " -> Aucune evolution a supprimer.");
            }
        }
    }

    public static void main(String[] args) {
        if (args.length < 5) {
            System.err.println("Usage: java -jar livrable5.jar <hauteur> <largeur> <nb ballons> <type plateau> <type joueur>");
            System.err.println("  type plateau : chemin | libre");
            System.err.println("  type joueur  : humain | aleatoire");
            System.exit(1);
        }

        int hauteur, largeur, nbBallons;
        try {
            hauteur   = Integer.parseInt(args[0]);
            largeur   = Integer.parseInt(args[1]);
            nbBallons = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            System.err.println("Erreur : hauteur, largeur et nb ballons doivent etre des entiers.");
            System.exit(1);
            return;
        }

        String typePlateau = args[3];
        String typeJoueur  = args[4];

        if (!typePlateau.equalsIgnoreCase("chemin") && !typePlateau.equalsIgnoreCase("libre")) {
            System.err.println("Erreur : type plateau doit etre 'chemin' ou 'libre'.");
            System.exit(1);
        }
        if (!typeJoueur.equalsIgnoreCase("humain") && !typeJoueur.equalsIgnoreCase("aleatoire")) {
            System.err.println("Erreur : type joueur doit etre 'humain' ou 'aleatoire'.");
            System.exit(1);
        }

        new Livrable5(hauteur, largeur, nbBallons, typePlateau, typeJoueur).start();
    }
}