package test.player;

import board.typeOfBoard.FreeBoard;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import player.Shop;


public class ShopTest {

      @Test
      void listeToursDisponiblesNonVide() {
          Shop shop = new Shop(new FreeBoard(6, 6,5));
          assertEquals(7, shop.getAvailableTowers().size());
      }

      @Test
      void trouveUnePositionLibre() {
          Shop shop = new Shop(new FreeBoard(6, 6,5));
          boolean found = false;
          for (int row = 0; row < shop.getBoard().getHeight() && !found; row++) {
              for (int col = 0; col < shop.getBoard().getWidth(); col++) {
                  if (shop.getBoard().isCellAvailable(row, col)) {
                      found = true;
                      break;
                  }
              }
          }
          assertTrue(found);
      }

  }
