package es.iespuertodelacruz.mp.canarytrails.dto;

import es.iespuertodelacruz.mp.canarytrails.dto.zona.ZonaSalidaDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ZonaSalidaDtoTest {

    @Test
    void testConstructorAndGetters() {
        ZonaSalidaDto dto = new ZonaSalidaDto(1, "Norte");

        assertEquals(1, dto.getId());
        assertEquals("Norte", dto.getNombre());
    }

    @Test
    void testSetters() {
        ZonaSalidaDto dto = new ZonaSalidaDto();
        dto.setId(2);
        dto.setNombre("Sur");

        assertEquals(2, dto.getId());
        assertEquals("Sur", dto.getNombre());
    }

    @Test
    void testToStringNotNull() {
        ZonaSalidaDto dto = new ZonaSalidaDto(3, "Este");
        assertNotNull(dto.toString());
    }

    @Test
    void testEqualsAndHashCode() {
        ZonaSalidaDto dto1 = new ZonaSalidaDto(1, "Norte");
        ZonaSalidaDto dto2 = new ZonaSalidaDto(1, "Norte");
        ZonaSalidaDto dto3 = new ZonaSalidaDto(2, "Sur");

        assertEquals(dto1, dto2);
        assertNotEquals(dto1, dto3);
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }
}