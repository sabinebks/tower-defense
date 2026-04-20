package choose.action;

import player.Player;
import player.Shop;
import entity.tower.Tower;
import entity.tower.evolution.TypeEvolution;

import java.util.ArrayList;
import java.util.List;

import board.Position;

/**
 * class that builds the action menu
 */
public class ActionBuilder {

    /**
     * returns a list of actions (menu)
     *
     * @param player the player
     * @param shop the shop
     * @return list of available actions
     */
    public static List<Action> buildMenu(Player player, Shop shop) {
        List<Action> menu = new ArrayList<>();
        menu.add(buildAcheterTour(player, shop));
        menu.add(buildEvoluerTour(player));
        menu.add(buildVendreTour(player));
        menu.add(buildVendreEvolution(player));
        menu.add(new DoNothingAction());
        menu.add(new QuitAction());
        return menu;
    }

    /**
     * builds buy tower action
     *
     * @param player the player
     * @param shop the shop
     * @return buy tower menu action
     */
    public static Action buildAcheterTour(Player player, Shop shop) {
        List<Action> sousMenu = new ArrayList<>();

        for (Tower t : shop.getAvailableTowers()) {
            Position pos = shop.getBoard().findAvailablePosition();
            if (pos != null && player.getCredit() >= t.getCost()) {
                sousMenu.add(new BuyTowerAction(t, pos));
            }
        }
        sousMenu.add(new RetourAction());

        return new MenuAction("acheter une tour", sousMenu);
    }

    /**
     * builds upgrade tower action
     *
     * @param player the player
     * @return upgrade tower menu action
     */
    public static Action buildEvoluerTour(Player player) {
        List<Action> sousMenu = new ArrayList<>();

        for (Tower t : player.getTowers()) {
            if (!t.getEvolution()) continue;

            List<Action> evoMenu = new ArrayList<>();

            for (TypeEvolution evo : TypeEvolution.values()) {
                if (!t.getEvolutions().getEvolutions().contains(evo)) continue;
                if (t.getEvolutions().getEvolutionsPossedees().containsKey(evo)) continue;

                int cost = t.getCoutEvolution(evo);
                if (player.getCredit() >= cost) {
                    evoMenu.add(new Upgrade(t, evo));
                }
            }

            evoMenu.add(new RetourAction());

            if (evoMenu.size() > 1) {
                sousMenu.add(new MenuAction("evoluer " + t.getName(), evoMenu));
            }
        }

        sousMenu.add(new RetourAction());
        return new MenuAction("evoluer une tour", sousMenu);
    }

    /**
     * builds sell tower action
     *
     * @param player the player
     * @return sell tower menu action
     */
    public static Action buildVendreTour(Player player) {
        List<Action> sousMenu = new ArrayList<>();

        for (Tower t : player.getTowers()) {
            sousMenu.add(new SellTowerAction(t));
        }

        sousMenu.add(new RetourAction());
        return new MenuAction("vendre une tour", sousMenu);
    }

    /**
     * builds sell evolution action
     *
     * @param player the player
     * @return sell evolution menu action
     */
    public static Action buildVendreEvolution(Player player) {
        List<Action> sousMenu = new ArrayList<>();

        for (Tower t : player.getTowers()) {
            if (t.getEvolutions() == null) continue;

            var possedees = t.getEvolutions().getEvolutionsPossedees();
            if (possedees.isEmpty()) continue;

            List<Action> evoMenu = new ArrayList<>();

            for (TypeEvolution evo : possedees.keySet()) {
                evoMenu.add(new DownGrade(t, evo));
            }

            evoMenu.add(new RetourAction());
            sousMenu.add(new MenuAction("vendre evolution de " + t.getName(), evoMenu));
        }

        sousMenu.add(new RetourAction());
        return new MenuAction("vendre une evolution", sousMenu);
    }
}