package choose;

import java.util.List;

public interface ListChooser<Action> {
    Action choose(String p, List<Action> options);
}
