package choose;

import choose.action.Action;
import java.util.List;
import java.util.Scanner;

/**
 * human list chooser
 */
public class HumanListChooser<T extends Action> implements ListChooser<T> {

    /**
     * scanner input
     */
    private final Scanner scanner = new Scanner(System.in);

    @Override
    /**
     * choose an option manually
     * @param prompt message to display
     * @param options list of options
     * @return selected option
     */
    public T choose(String prompt, List<T> options) {
        System.out.println("\n" + prompt);

        for (int i = 0; i < options.size(); i++) {
            System.out.println("  " + i + ". " + options.get(i).getDescription());
        }

        System.out.print("votre choix : ");

        int index = -1;

        while (index < 0 || index >= options.size()) {
            try {
                index = scanner.nextInt();
            } catch (Exception e) {
                scanner.nextLine();
                System.out.print("index invalide, reessaie : ");
            }
        }

        return options.get(index);
    }
}