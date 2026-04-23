package Livrables;

/**
 * Launch an interactive game of DefinedBoard
 */
public class LauncherAInteractive {
    /**
     * Main function run the game
     * @param args the input of users
     */
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: java -cp classes Livrables.LauncherAInteractive <largeur> <hauteur>");
            return;
        }
        Livrable6.main(new String[]{args[0], args[1], "15","chemin", "humain" });
    }
}
