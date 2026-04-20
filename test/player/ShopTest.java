package test.player;

  import board.Board;
  import board.Cell;
  import board.Position;
  import board.typeOfBoard.FreeBoard;
  import org.junit.jupiter.api.Test;
  import player.Shop;

  import java.util.List;

  import static org.junit.jupiter.api.Assertions.*;

  public class ShopTest {

      @Test
      void listeToursDisponiblesNonVide() {
          Shop shop = new Shop(new FreeBoard(6, 6));
          assertEquals(7, shop.getAvailableTowers().size());
      }

      @Test
      void trouveUnePositionLibre() {
          Shop shop = new Shop(new FreeBoard(6, 6));
          assertNotNull(shop.findAvailablePosition());
      }

      @Test
      void retourneNullSiAucuneCaseLibre() {
          Shop shop = new Shop(new NoCellAvailableBoard(3, 3));
          assertNull(shop.getBoard().findAvailablePosition());
      }

      static class NoCellAvailableBoard extends Board {
          NoCellAvailableBoard(int h, int w) {
              super(h, w);
          }

          @Override
          public void init() {
              grid = new Cell[height][width];
              for (int r = 0; r < height; r++) {
                  for (int c = 0; c < width; c++) {
                      grid[r][c] = new Cell(new Position(r, c));
                  }
              }
              calculerPath();
          }

          @Override
          public void calculerPath() {
              allPath.clear();
              allCells.clear();
          }

          @Override
          public List<Position> getPathPositions() {
              return List.of();
          }

          @Override
          public List<Cell> allCells() {
              return allCells;
          }

          @Override
          public boolean isCellAvailable(int row, int col) {
              return false;
          }
      }
  }