package es.iespuertodelacruz.mp.canarytrails.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ZonaDTOTest {

    @Test
    void testConstructorAndGetters() {
        ZonaDTO dto = new ZonaDTO(1, "Norte");

        assertEquals(1, dto.getId());
        assertEquals("Norte", dto.getNombre());
    }

    @Test
    void testSetters() {
        ZonaDTO dto = new ZonaDTO();
        dto.setId(2);
        dto.setNombre("Sur");

        assertEquals(2, dto.getId());
        assertEquals("Sur", dto.getNombre());
    }

    @Test
    void testToStringNotNull() {
        ZonaDTO dto = new ZonaDTO(3, "Este");
        assertNotNull(dto.toString());
    }

    @Test
    void testEqualsAndHashCode() {
        ZonaDTO dto1 = new ZonaDTO(1, "Norte");
        ZonaDTO dto2 = new ZonaDTO(1, "Norte");
        ZonaDTO dto3 = new ZonaDTO(2, "Sur");

        assertEquals(dto1, dto2);
        assertNotEquals(dto1, dto3);
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }
}