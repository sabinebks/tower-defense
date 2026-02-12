package game;

import board.typeOfBoard.*;
import board.Position;
import object.balloon.Balloon;
import object.balloon.*;
import object.tower.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        DefinedBoard board = new DefinedBoard(10, 20);
        board.calculerPath();

        Game game = new Game();
        game.setBoard(board);

        // Ajout d'un ballon pour le test
        game.getBalloons().add(new Balloon(1, 0, 0, 0.1, BalloonType.BASIC));

        Clock clock = new Clock(game);

        // Effacer l'écran une fois au début
        System.out.print("\033[H\033[2J");
        clock.start();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            // On ne print rien ici, le render s'en occupe
            if (scanner.hasNextLine()) {
                String command = scanner.nextLine().toLowerCase();
                if (command.startsWith("pos")) {
                    try {
                        String[] parts = command.split(" ");
                        int row = Integer.parseInt(parts[1]);
                        int col = Integer.parseInt(parts[2]);

                        Position p = new Position(row, col);
                        if (board.isInside(row, col) && !board.getCell(p).isPath()) {
                            game.getTowers().add(new DartMonkey(p));
                        }
                    } catch (Exception e) {
                        // Erreur silencieuse ou log léger pour ne pas casser l'UI
                    }
                } else if (command.equals("quit")) {
                    System.exit(0);
                }
            }
        }
    }
}