package choose;

import java.util.List;

public class AutoListChooser<T extends choose.action.Action>  implements ListChooser<T> {
    private int index = 0;

    @Override
    public T choose(String p, List<T> options){
        if(options.isEmpty()){
            return null;
        }
        T choice = options.get(index % options.size());
        index++;
        return choice;
    }
}
