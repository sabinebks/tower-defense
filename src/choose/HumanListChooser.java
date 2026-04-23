package choose;

import java.util.List;
import java.util.Scanner;

/**
 * Chooser humain générique : affiche les options et lit le choix au clavier.
 * Utilise getDescription() si T est une Action, sinon toString().
 * En cas de position indisponible (liste vide), informe l'utilisateur.
 */
public class HumanListChooser<T> implements ListChooser<T> {

    private final Scanner scanner = new Scanner(System.in);

    @Override
    /**
     * Function return action choosed by human
     * @param prompt
     * @param options list of options to choose
     */
    public T choose(String prompt, List<T> options) {
        if (options.isEmpty()) {
            System.out.println("Aucune option disponible pour : " + prompt);
            return null;
        }

        System.out.println("\n" + prompt);
        for (int i = 0; i < options.size(); i++) {
            T option = options.get(i);
            String label = (option instanceof choose.action.Action action)
                    ? action.getDescription()
                    : option.toString();
            System.out.println("  " + i + ". " + label);
        }
        System.out.print("Votre choix : ");

        int index = -1;
        while (index < 0 || index >= options.size()) {
            try {
                index = scanner.nextInt();
                if (index < 0 || index >= options.size()) {
                    System.out.print("Choix hors limites (0-" + (options.size() - 1) + "), reessaie : ");
                }
            } catch (Exception e) {
                scanner.nextLine();
                System.out.print("Index invalide, reessaie : ");
            }
        }
        return options.get(index);
    }
}
