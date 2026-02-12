package game;

import board.Board;
import board.Cell;
import board.Position;
import board.typeOfBoard.DefinedBoard;
import board.typeOfBoard.FreeBoard;
import object.balloon.Balloon;
import object.tower.Tower;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Game {
    private Board board;
    private List<Balloon> balloons = new ArrayList<>();
    private List<Tower> towers = new ArrayList<>();
    private int totalTicks = 0;

    public void gameTick() {
        totalTicks++;

        // 1. Gérer les ballons (Mouvement)
        Iterator<Balloon> bIterator = balloons.iterator();
        while (bIterator.hasNext()) {
            Balloon b = bIterator.next();
            if (b.isActive()) {
                boolean encoreEnJeu = b.move();
                if (!encoreEnJeu) {
                    bIterator.remove();
                }
            } else {
                bIterator.remove();
            }
        }

        // 2. Gérer les tours (Attaque)
        for (Tower t : towers) {
            t.update(balloons);
        }

        // 3. Affichage
        render(this.board);
    }

    public void setBoard(Board board){
        this.board = board;
    }

    public List<Balloon> getBalloons(){
        return balloons;
    }

    public List<Tower> getTowers(){
        return towers;
    }

    private void render(Board board) {
        System.out.print("\033[H"); // Remonte le curseur

        int height = board.getHeight();
        int width = board.getWidth();

        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                String symbol = "";

                // 1. Priorité Ballon
                for (Balloon b : balloons) {
                    if (b.getCurrentPosition().getRow() == r && b.getCurrentPosition().getCol() == c) {
                        symbol = "B ";
                        break;
                    }
                }

                // 2. Priorité Tour
                if (symbol.isEmpty()) {
                    for (Tower t : towers) {
                        if (t.getPosition().getRow() == r && t.getPosition().getCol() == c) {
                            symbol = "T ";
                            break;
                        }
                    }
                }

                // 3. Sol (différencié selon le Board)
                if (symbol.isEmpty()) {
                    Cell cell = board.getCell(r, c);
                    if (cell.isPath()) {
                        // Optionnel : différencier D et F si c'est un DefinedBoard
                        symbol = "c ";
                    } else {
                        symbol = ". ";
                    }
                }
                System.out.print(symbol + "| ");
            }
            System.out.println();
        }

        System.out.println("\n=== STATUT DU JEU ===");
        System.out.println("Tours : " + towers.size() + " | Ballons : " + balloons.size());
        System.out.print("Entrez commande (pos r c) > ");
        System.out.flush();
    }
}