package choose;

import java.util.List;
/*
    * Interface pour choisir les actions d'un joueur
    */
public interface ListChooser {

    List<Action> choisirActions(Game game, Player player);
}