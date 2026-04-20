package choose;

import java.util.List;

/**
 * list chooser interface
 * @param <action> the type of action to choose
 */
public interface ListChooser<action> {

    /**
     * choose an option from a list
     * @param p prompt message
     * @param options list of options
     * @return chosen option
     */
    action choose(String p, List<action> options);
}