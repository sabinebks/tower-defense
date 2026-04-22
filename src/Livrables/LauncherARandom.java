package Livrables;

public class LauncherARandom {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: java -cp classes Livrables.LauncherARandom <largeur> <hauteur>");
            return;
        }
        Livrable6.main(new String[]{args[0], args[1], "random", "a"});
    }
}
