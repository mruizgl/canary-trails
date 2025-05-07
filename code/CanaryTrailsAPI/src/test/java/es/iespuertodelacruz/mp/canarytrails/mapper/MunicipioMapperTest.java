package es.iespuertodelacruz.mp.canarytrails.mapper;

import es.iespuertodelacruz.mp.canarytrails.dto.municipio.MunicipioEntradaDto;
import es.iespuertodelacruz.mp.canarytrails.dto.municipio.MunicipioSalidaDto;
import es.iespuertodelacruz.mp.canarytrails.dto.zona.ZonaSalidaDto;
import es.iespuertodelacruz.mp.canarytrails.entities.Municipio;
import es.iespuertodelacruz.mp.canarytrails.entities.Zona;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MunicipioMapperTest {

    @Autowired
    MunicipioMapper municipioMapper;

    
    private final ZonaMapper zonaMapper = Mappers.getMapper(ZonaMapper.class);

    @Test
    void testToDto() {
        Zona zona = new Zona();
        zona.setId(2);
        zona.setNombre("Norte");

        Municipio municipio = new Municipio();
        municipio.setId(1);
        municipio.setNombre("La Orotava");
        municipio.setAltitudMedia(390);
        municipio.setLatitudGeografica(BigDecimal.valueOf(28.3891));
        municipio.setLongitudGeografica(BigDecimal.valueOf(-16.5237));
        municipio.setZona(zona);

        MunicipioSalidaDto dto = municipioMapper.toDto(municipio);

        assertEquals(1, dto.id());
        assertEquals("La Orotava", dto.nombre());
        assertEquals(390, dto.altitudMedia());
        assertEquals(28.3891, dto.latitudGeografica());
        assertEquals(-16.5237, dto.longitudGeografica());
        assertEquals(new ZonaSalidaDto(2, "Norte"), dto.zona());
    }

    @Test
    void testToEntity() {
        MunicipioEntradaDto dto = new MunicipioEntradaDto(
                1, "La Orotava", 390,
                28.3891, -16.5237, 2
        );

        Municipio municipio = municipioMapper.toEntity(dto);

        assertEquals(1, municipio.getId());
        assertEquals("La Orotava", municipio.getNombre());
        assertEquals(390, municipio.getAltitudMedia());
        assertEquals(BigDecimal.valueOf(28.3891), municipio.getLatitudGeografica());
        assertEquals(BigDecimal.valueOf(-16.5237), municipio.getLongitudGeografica());
        assertNull(municipio.getZona()); // se espera null porque zona se inyecta en el servicio
    }
}
