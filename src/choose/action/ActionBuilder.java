package choose.action;

import player.Player;
import player.Shop;
import entity.tower.Tower;
import entity.tower.evolution.TypeEvolution;

import java.util.ArrayList;
import java.util.List;

import board.Position;

public class ActionBuilder {
    
    /**
     * Methode retourner une liste de action, donc dit menu
     * @param player le joueur
     * @param shop le shop
     * @return une liste des actions a choisi
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
     * Methode retourner l'action acheter un tour 
     * @param player le joueur
     * @param shop le shop
     * @return une action de type BuyTowerAction
     */
    public static Action buildAcheterTour(Player player, Shop shop) {
        List<Action> sousMenu = new ArrayList<>();

        for (Tower t : shop.getAvailableTowers()) {
            if (player.getCredit() >= t.getCost()) {
                // La position sera choisie au moment de execute(), pas ici
                sousMenu.add(new BuyTowerAction(t, shop.getBoard(), player.getPositionChooser()));
            }
        }
        sousMenu.add(new RetourAction());

        return new MenuAction("Acheter une tour", sousMenu);
    }

    /**
     * Methode retourner l'action d'evoluer un tour
     * @param player
     * @return une action de type evoluer tour
     */
    public static Action buildEvoluerTour(Player player) {
        List<Action> sousMenu = new ArrayList<>();

        for (Tower t : player.getTowers()) {
            if (!t.getEvolution()) continue;
            List<Action> evoMenu = new ArrayList<>();
            for (TypeEvolution evo : TypeEvolution.values()) {
                // ← vérifier que l'évolution est disponible ET pas déjà achetée
                if (!t.getEvolutions().getEvolutions().contains(evo)) continue;
                if (t.getEvolutions().getEvolutionsPossedees().containsKey(evo)) continue;
                int cost = t.getCoutEvolution(evo);
                if (player.getCredit() >= cost) {
                    evoMenu.add(new Upgrade(t, evo));
                }
            }
            evoMenu.add(new RetourAction());
            if (evoMenu.size() > 1) {
                sousMenu.add(new MenuAction("Évoluer " + t.getName(), evoMenu));
            }
        }
        sousMenu.add(new RetourAction());
        return new MenuAction("Évoluer une tour", sousMenu);
    }

    /**
     * Methode retourner l'action vendre un tour
     * @param player le joueur
     * @return l'action de type vendre tour
     */
    public static Action buildVendreTour(Player player) {
        List<Action> sousMenu = new ArrayList<>();

        for (Tower t : player.getTowers()) {
            sousMenu.add(new SellTowerAction(t));
        }
        sousMenu.add(new RetourAction());

        return new MenuAction("Vendre une tour", sousMenu);
    }

    /**
     * Methode retourner l'action vendre une evolution de tour
     * @param player le joueur
     * @return l'action de type vendre evolution
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
            sousMenu.add(new MenuAction("Vendre évolution de " + t.getName(), evoMenu));
        }
        sousMenu.add(new RetourAction());

        return new MenuAction("Vendre une évolution", sousMenu);
    }
}