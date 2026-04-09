package choose;

import choose.ListChooser;

import java.util.List;

public class AutoListChooser<T extends choose.action.Action>  implements ListChooser<T> {
    /**
     * Les attributs de AutoListChooser
     * L'index du choix
     */
    private int index = 0;

    @Override
    /**
     * Methode retourner le
     * @param options l'option 
     */
    public T choose(String p, List<T> options){
        if(options.isEmpty()){
            return null;
        }
        T choice = options.get(index % options.size());
        index++;
        return choice;
    }
}
