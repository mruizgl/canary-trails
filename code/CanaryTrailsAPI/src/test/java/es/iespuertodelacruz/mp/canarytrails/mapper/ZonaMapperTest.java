package es.iespuertodelacruz.mp.canarytrails.mapper;

import es.iespuertodelacruz.mp.canarytrails.dto.ZonaDTO;
import es.iespuertodelacruz.mp.canarytrails.entities.Zona;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class ZonaMapperTest {

    private final ZonaMapper mapper = Mappers.getMapper(ZonaMapper.class);

    @Test
    void testToDTO() {
        Zona zona = new Zona();
        zona.setId(1);
        zona.setNombre("Norte");

        ZonaDTO dto = mapper.toDTO(zona);

        assertNotNull(dto);
        assertEquals(1, dto.getId());
        assertEquals("Norte", dto.getNombre());
    }

    @Test
    void testToEntity() {
        ZonaDTO dto = new ZonaDTO(2, "Sur");

        Zona zona = mapper.toEntity(dto);

        assertNotNull(zona);
        assertEquals(2, zona.getId());
        assertEquals("Sur", zona.getNombre());
    }

    @Test
    void testNullConversion() {
        assertNull(mapper.toDTO(null));
        assertNull(mapper.toEntity(null));
    }
}
