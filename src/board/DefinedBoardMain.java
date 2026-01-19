package board;

public class DefinedBoardMain {
    public static void main(String[] args) {

        int height = 10;
        int width  = 15;

        DefinedBoard board = new DefinedBoard(height, width);
        int cheminLong = 0;
        Cell[] chemins = new Cell[100];

        System.out.println("Plateau (" + height + " x " + width + ")");
        System.out.println("C = chemin, X = vide");

        for (int r = 0; r < board.getHeight(); r++) {
            for (int c = 0; c < board.getWidth(); c++) {
                if ( board.getCell(r, c).isPath()) {
                    chemins[cheminLong] = board.getCell(r, c);
                    cheminLong += 1;
                }
                if (board.getCell(r, c).isPath()) {
                    System.out.print("C "); // C si chemin
                } 
                else {
                    System.out.print(". "); // . sinon
                }
            }
            System.out.println(); //nouvelle ligne
        }
    }
}
