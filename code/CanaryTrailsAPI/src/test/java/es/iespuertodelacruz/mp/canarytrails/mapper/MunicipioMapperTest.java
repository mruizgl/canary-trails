package es.iespuertodelacruz.mp.canarytrails.mapper;

import es.iespuertodelacruz.mp.canarytrails.dto.municipio.MunicipioEntradaDto;
import es.iespuertodelacruz.mp.canarytrails.dto.municipio.MunicipioSalidaDto;
import es.iespuertodelacruz.mp.canarytrails.entities.Municipio;
import es.iespuertodelacruz.mp.canarytrails.entities.Zona;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;


import static org.junit.jupiter.api.Assertions.*;


public class MunicipioMapperTest {

    private final MunicipioMapper mapper = Mappers.getMapper(MunicipioMapper.class);

    @Test
    void testToDto() {
        Zona zona = new Zona();
        zona.setId(2);
        zona.setNombre("Norte");

        Municipio municipio = new Municipio();
        municipio.setId(1);
        municipio.setNombre("La Orotava");
        municipio.setAltitudMedia(390);
        municipio.setLatitudGeografica(new java.math.BigDecimal("28.3891"));
        municipio.setLongitudGeografica(new java.math.BigDecimal("-16.5237"));
        municipio.setZona(zona);

        MunicipioSalidaDto dto = mapper.toDto(municipio);

        assertEquals(1, dto.id());
        assertEquals("La Orotava", dto.nombre());
        assertEquals(390, dto.altitudMedia());
        assertEquals(28.3891, dto.latitudGeografica());
        assertEquals(-16.5237, dto.longitudGeografica());
        assertNotNull(dto.zona());
    }

    @Test
    void testToEntity() {
        MunicipioEntradaDto dto = new MunicipioEntradaDto(
                "Tacoronte", 300, 28.4, -16.3, 1
        );

        Municipio municipio = mapper.toEntity(dto);

        assertEquals("Tacoronte", municipio.getNombre());
        assertEquals(300, municipio.getAltitudMedia());
        assertEquals(new java.math.BigDecimal("28.4"), municipio.getLatitudGeografica());
        assertEquals(new java.math.BigDecimal("-16.3"), municipio.getLongitudGeografica());
        // Zona y rutas se ignoran por configuración del mapper
        assertNull(municipio.getZona());
        assertNull(municipio.getRutas());
    }
}