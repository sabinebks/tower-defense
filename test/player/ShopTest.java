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
          assertNotNull(shop.getBoard().findAvailablePosition());
      }

  }
