package Livrables;

public class LauncherBRandom {
    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("Usage: java -cp classes Livrables.LauncherBRandom <largeur> <hauteur> <nbChemins>");
            return;
        }
        Livrable6.main(new String[]{args[0], args[1], args[2], "random", "b"});
    }
}
