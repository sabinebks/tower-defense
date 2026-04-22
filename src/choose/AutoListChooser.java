package choose;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * auto list chooser
 */
public class AutoListChooser<T extends choose.action.Action> implements ListChooser<T> {

    private final Random random = new Random();

    @Override
    /**
     * Function help choose an action 
     * @param p 
     * @param options list of actions to choose
     * @return a list of action choosed randomly
     */
    public T choose(String p, List<T> options) {
        if (options == null || options.isEmpty()) {
            return null;
        }

        List<T> allowed = new ArrayList<>();
        for (T option : options) {
            if (option.isAllowedForAuto()) {
                allowed.add(option);
            }
        }

        if (allowed.isEmpty()) {
            return options.get(random.nextInt(options.size()));
        }
        return allowed.get(random.nextInt(allowed.size()));
    }
}