package test.round;

  import board.Board;
  import board.Cell;
  import board.Position;
  import choose.AutoListChooser;
  import entity.balloon.Balloon;
  import entity.balloon.BalloonType;
  import org.junit.jupiter.api.BeforeEach;
  import org.junit.jupiter.api.Test;
  import player.Player;
  import round.Round;
  import round.RoundManager;

  import java.util.ArrayList;
  import java.util.List;

  import static org.junit.jupiter.api.Assertions.*;

  public class RoundManagerTest {

      private FakeBoard board;
      private Player player;
      private RoundManager manager;

      @BeforeEach
      void setUp() {
          board = new FakeBoard(3, 3);
          player = new Player(100, new AutoListChooser<>(), new AutoListChooser<>());

          Balloon b = new Balloon(0, 0, 0, BalloonType.BASIC);
          Round round = new Round(1, new ArrayList<>(List.of(b)));

          manager = new RoundManager(new ArrayList<>(List.of(round)), board, player);
      }

      @Test
      void spawnSeulementAuTick5() {
          manager.update(1);
          manager.update(2);
          manager.update(3);
          manager.update(4);

          assertEquals(0, board.spawnCount);

          manager.update(5);
          assertEquals(1, board.spawnCount);
      }

      @Test
      void ballonEchappeFaitPerdreUneVie() {
          player.setLives(2);

          manager.update(5);

          assertEquals(1, player.getLives());
          assertEquals(1, manager.getEscapedCount());
      }

      @Test
      void roundSeTermineQuandToutEstVide() {
          manager.update(5);
          assertTrue(manager.isCurrentRoundFinished());

          manager.advanceRound();
          assertTrue(manager.isFinished());
      }

      static class FakeBoard extends Board {
          int spawnCount = 0;

          FakeBoard(int h, int w) {
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
              Cell c = getCell(1, 1);
              c.assignChemin();
              allCells.add(c);
              allPath.put(0, new ArrayList<>(List.of(c)));
          }

          @Override
          public List<Position> getPathPositions() {
              return List.of(new Position(1, 1));
          }

          @Override
          public List<Cell> allCells() {
              return allCells;
          }

          @Override
          public boolean isCellAvailable(int row, int col) {
              return true;
          }

          @Override
          public void spawnBalloon(Balloon balloon) {
              super.spawnBalloon(balloon);
              spawnCount++;
          }

          @Override
          public List<Balloon> update() {
              List<Balloon> removed = new ArrayList<>(balloons);
              for (Balloon b : removed) {
                  escapedBalloons.add(b);
              }
              balloons.clear();
              return removed;
          }
      }
  }
