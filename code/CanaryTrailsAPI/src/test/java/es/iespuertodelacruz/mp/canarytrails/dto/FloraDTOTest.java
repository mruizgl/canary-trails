package es.iespuertodelacruz.mp.canarytrails.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FloraDTOTest {

    @Test
    void testEqualsAndHashCode() {
        FloraDTO f1 = new FloraDTO(1, "A", "E1", "T1", "Prim", "Ver", "Desc", true);
        FloraDTO f2 = new FloraDTO(1, "B", "E2", "T2", "Oto", "Inv", "Otra", false);

        assertEquals(f1, f2);
        assertEquals(f1.hashCode(), f2.hashCode());
    }

    @Test
    void testToString() {
        FloraDTO flora = new FloraDTO(1, "A", "E", "T", "P", "C", "D", true);
        assertTrue(flora.toString().contains("FloraDTO"));
    }

    @Test
    void testGettersAndSetters() {
        FloraDTO flora = new FloraDTO();
        flora.setNombre("Test");
        assertEquals("Test", flora.getNombre());
    }
}
