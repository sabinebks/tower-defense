package choose;

import java.util.List;
import java.util.Random;

public class AutoListChooser<T> implements ListChooser<T> {

    private final Random random = new Random();

    private int index = 0;

    @Override
    public T choose(String p, List<T> options) {
        if (options.isEmpty()) {
            return null;
        }

        int size = options.size();

        for (int i = 0; i < size; i++) {
            T choice = options.get(index % size);
            index++;

            if (choice instanceof choose.action.Action action) {
                if (action.isAllowedForAuto()) {
                    return choice;
                }
            } else {
                return options.get(random.nextInt(size));
            }
        }

        return options.get(0);
    }
}