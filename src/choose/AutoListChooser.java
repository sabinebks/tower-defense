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
