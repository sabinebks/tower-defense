package game;

import choose.action.Action;
import choose.action.ActionBuilder;
import choose.action.DownGrade;
import choose.action.Upgrade;
import entity.tower.evolution.TypeEvolution;
import player.Player;
import player.Shop;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Strategie du joueur automatique (bot).
 */
public class BotStrategy {

    private final Player player;
    private final Shop shop;
    private final Random rng = new Random();

    public BotStrategy(Player player, Shop shop) {
        this.player = player;
        this.shop   = shop;
    }

    /**
     * Place automatiquement des tours au debut de la partie.
     */
    public void placeInitialTowers(Game game) {
        System.out.println("[BOT] Placement initial de 2 tours de chaque type...");
        for (int i = 0; i < 4; i++) {
            List<Action> menu = ActionBuilder.buildMenu(player, shop);
            if (!menu.isEmpty()) menu.get(0).execute(game);
        }
    }

    /**
     * Applique une evolution ou une suppression d'evolution selon le round.
     * @param roundNum le numero du round courant
     * @param game le contexte de jeu
     */
    public void applyRoundEvolution(int roundNum, Game game) {
        if (roundNum <= 5) {
            List<Action> upgrades = new ArrayList<>();
            for (var tower : player.getTowers()) {
                if (!tower.getEvolution()) continue;
                var evolutions = tower.getEvolutions();
                if (evolutions == null) continue;
                for (TypeEvolution evo : evolutions.getEvolutions()) {
                    if (!evolutions.getEvolutionsPossedees().containsKey(evo)) {
                        int cost = tower.getCoutEvolution(evo);
                        if (player.getCredit() >= cost) upgrades.add(new Upgrade(tower, evo));
                    }
                }
            }
            if (!upgrades.isEmpty()) {
                System.out.println("[BOT] Manche " + roundNum + " -> Application d'une evolution via action");
                upgrades.get(rng.nextInt(upgrades.size())).execute(game);
            } else {
                System.out.println("[BOT] Manche " + roundNum + " -> Aucune evolution disponible a appliquer.");
            }
        } else {
            List<Action> sells = new ArrayList<>();
            for (var tower : player.getTowers()) {
                var evolutions = tower.getEvolutions();
                if (evolutions == null) continue;
                for (TypeEvolution evo : evolutions.getEvolutionsPossedees().keySet())
                    sells.add(new DownGrade(tower, evo));
            }
            if (!sells.isEmpty()) {
                System.out.println("[BOT] Manche " + roundNum + " -> Suppression d'une evolution via action");
                sells.get(rng.nextInt(sells.size())).execute(game);
            } else {
                System.out.println("[BOT] Manche " + roundNum + " -> Aucune evolution a supprimer.");
            }
        }
    }
}