package board;

/**
 * Main class to test the DefinedBoard with command line arguments.
 */

public class Livrable1a {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Erreur : précisez la hauteur et la largeur.");
            return;
        }

        int height = Integer.parseInt(args[0]);
        int width  = Integer.parseInt(args[1]);

        DefinedBoard board = new DefinedBoard(height, width);

        System.out.println("Plateau " + height + "x" + width);

        for (int r = 0; r < board.getHeight(); r++) {
            for (int c = 0; c < board.getWidth(); c++) {
                Cell cell = board.getCell(r, c);

                if (cell.isPath()) {
                    int index = board.getChemins().indexOf(cell);
                    if (index == 0) {
                        System.out.print("D ");
                    } else if (index == board.getCheminLong() - 1) {
                        System.out.print("F ");
                    } else {
                        System.out.print("c ");
                    }
                } else {
                    System.out.print(". ");
                }
            }
            System.out.println();
        }

        System.out.println("\nChemin :");
        for (int i = 0; i < board.getChemins().size(); i++) {
            System.out.print(board.getChemins().get(i).getPosition() + (i < board.getChemins().size() - 1 ? " -> " : ""));
        }
        System.out.println();
    }
}