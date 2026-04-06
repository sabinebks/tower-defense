package test.entity.tower.evolution;
import org.junit.jupiter.api.Test;

import entity.tower.evolution.*;

import static org.junit.jupiter.api.Assertions.*;

public class EvolutionTest {
    @Test
    public void testInitialisation() {
        Evolution e = new Evolution();
        assertTrue(e.getEvolutions().isEmpty());
        assertTrue(e.getEvolutionsPossedees().isEmpty());
    }
    @Test
    public void testAddAndDeleteEvolution() {
        Evolution e = new Evolution();
        e.addEvolution(TypeEvolution.PORTEE);
        assertTrue(e.getEvolutions().contains(TypeEvolution.PORTEE));

        e.deleteEvolutions(TypeEvolution.PORTEE);
        assertFalse(e.getEvolutions().contains(TypeEvolution.PORTEE));
    }

    @Test
    public void testVendreEvolution() {
        Evolution e = new Evolution();
        e.getEvolutionsPossedees().put(TypeEvolution.PUISSANCE, 100);

        e.vendreEvolution(TypeEvolution.PUISSANCE);
        assertFalse(e.getEvolutionsPossedees().containsKey(TypeEvolution.PUISSANCE));
    }

    @Test
    public void testGetEvolutionsPossedees() {
        Evolution e = new Evolution();
        e.getEvolutionsPossedees().put(TypeEvolution.PROJECTILES, 150);

        assertEquals(150, e.getEvolutionsPossedees().get(TypeEvolution.PROJECTILES));
    }
}
