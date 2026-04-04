package game;

import java.util.List;

/**
 * Interface pour choisir les actions du joueur
 */
public interface ListChooser {
    List<Action> choisirActions(Game game, Player player);
}