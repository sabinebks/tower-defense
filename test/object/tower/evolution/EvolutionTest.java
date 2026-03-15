package test.object.tower.evolution;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import object.tower.evolution.*;

public class EvolutionTest {
    @Test
    public void testInitialisation() {
        Evolution e = new Evolution();
        assertTrue(e.getEvolutions().isEmpty());
        assertTrue(e.getEvolutionsPossedees().isEmpty());
    }

}
