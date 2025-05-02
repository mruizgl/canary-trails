package es.iespuertodelacruz.mp.canarytrails.dto;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CoordenadaDTOTest {

    @Test
    void testEqualsAndHashCode() {
        CoordenadaDTO c1 = new CoordenadaDTO(1, new BigDecimal("28.123456"), new BigDecimal("-16.654321"));
        CoordenadaDTO c2 = new CoordenadaDTO(1, new BigDecimal("30.000000"), new BigDecimal("-15.000000"));

        assertEquals(c1, c2);
        assertEquals(c1.hashCode(), c2.hashCode());
    }

    @Test
    void testToString() {
        CoordenadaDTO dto = new CoordenadaDTO(1, new BigDecimal("28.123456"), new BigDecimal("-16.654321"));
        assertTrue(dto.toString().contains("CoordenadaDTO"));
    }

    @Test
    void testGettersAndSetters() {
        CoordenadaDTO dto = new CoordenadaDTO();
        dto.setLatitud(new BigDecimal("28.000000"));
        assertEquals(new BigDecimal("28.000000"), dto.getLatitud());
    }
}