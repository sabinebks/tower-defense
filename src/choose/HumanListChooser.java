package choose;
import choose.action.Action;
import java.util.List;
import java.util.Scanner;

public class HumanListChooser<T extends choose.action.Action> implements chooser.ListChooser<T> {
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public T choose(String prompt, List<T> options) {
        System.out.println("\n" + prompt);
        for (int i = 0; i < options.size(); i++) {
            System.out.println("  " + i + ". " + options.get(i).getDescription());
        }
        System.out.print("Votre choix : ");

        int index = -1;
        while (index < 0 || index >= options.size()) {
            try {
                index = scanner.nextInt();
            } catch (Exception e) {
                scanner.nextLine();
                System.out.print("Index invalide, réessaie : ");
            }
        }
        return options.get(index);
    }
}
