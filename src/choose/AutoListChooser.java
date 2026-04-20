package choose;

import java.util.List;

/**
 * auto list chooser
 */
public class AutoListChooser<T extends choose.action.Action> implements ListChooser<T> {

    /**
     * current index
     */
    private int index = 0;

    @Override
    /**
     * choose an option automatically
     * @param p prompt
     * @param options list of options
     * @return chosen option
     */
    public T choose(String p, List<T> options) {
        if (options.isEmpty()) {
            return null;
        }

        int size = options.size();

        for (int i = 0; i < size; i++) {
            T choice = options.get(index % size);
            index++;

            if (choice.isAllowedForAuto()) {
                return choice;
            }
        }

        return options.get(0);
    }
}