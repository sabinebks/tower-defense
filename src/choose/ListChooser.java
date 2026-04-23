package choose;

import java.util.List;
/**
 * Interface ListChooser
 * @param <Action> le type d'action que le ListChooser va choisir
 */
public interface ListChooser<Action> {
    Action choose(String p, List<Action> options);
}
