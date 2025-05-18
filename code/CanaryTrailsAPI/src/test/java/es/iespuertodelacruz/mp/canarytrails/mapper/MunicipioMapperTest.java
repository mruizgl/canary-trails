package es.iespuertodelacruz.mp.canarytrails.mapper;

import es.iespuertodelacruz.mp.canarytrails.dto.municipio.MunicipioEntradaCreateDto;
import es.iespuertodelacruz.mp.canarytrails.dto.municipio.MunicipioEntradaUpdateDto;
import es.iespuertodelacruz.mp.canarytrails.dto.municipio.MunicipioSalidaDto;
import es.iespuertodelacruz.mp.canarytrails.entities.Municipio;
import es.iespuertodelacruz.mp.canarytrails.entities.Ruta;
import es.iespuertodelacruz.mp.canarytrails.entities.Zona;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MunicipioMapperTest {

    private MunicipioMapperImpl mapper;

    @BeforeEach
    void setUp() {
        mapper = new MunicipioMapperImpl();
    }

    @Test
    void toDto_nullMunicipio_returnsNullTest() {
        assertNull(mapper.toDto(null));
    }


    @Test
    void toEntityCreate_nullDto_returnsNullTest() {
        assertNull(mapper.toEntityCreate(null));
    }

    @Test
    void toEntityCreate_validDto_mapsCorrectlyTest() {
        MunicipioEntradaCreateDto dto = new MunicipioEntradaCreateDto(
                "Tacoronte", 350, 28.4765, -16.4163, 1, List.of(1, 2));

        Municipio municipio = mapper.toEntityCreate(dto);

        assertNotNull(municipio);
        assertEquals("Tacoronte", municipio.getNombre());
        assertEquals(350, municipio.getAltitudMedia());
        assertEquals(BigDecimal.valueOf(28.4765), municipio.getLatitudGeografica());
        assertEquals(BigDecimal.valueOf(-16.4163), municipio.getLongitudGeografica());
    }

    @Test
    void toEntityUpdate_nullDto_returnsNullTest() {
        assertNull(mapper.toEntityUpdate(null));
    }

    @Test
    void toEntityUpdate_validDto_mapsCorrectlyTest() {
        MunicipioEntradaUpdateDto dto = new MunicipioEntradaUpdateDto(
                9, "Puerto de la Cruz", 15, BigDecimal.valueOf(28.411), BigDecimal.valueOf(-16.547), 1, List.of(1, 2));

        Municipio municipio = mapper.toEntityUpdate(dto);

        assertNotNull(municipio);
        assertEquals(9, municipio.getId());
        assertEquals("Puerto de la Cruz", municipio.getNombre());
        assertEquals(15, municipio.getAltitudMedia());
        assertEquals(BigDecimal.valueOf(28.411), municipio.getLatitudGeografica());
        assertEquals(BigDecimal.valueOf(-16.547), municipio.getLongitudGeografica());
    }
}
