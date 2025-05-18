package es.iespuertodelacruz.mp.canarytrails.mapper;

import es.iespuertodelacruz.mp.canarytrails.dto.ruta.*;
import es.iespuertodelacruz.mp.canarytrails.entities.Ruta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class RutaMapperTest {

    private RutaMapperImpl mapper;

    @BeforeEach
    void setUp() {
        mapper = new RutaMapperImpl();
    }

    @Test
    void toDto_nullEntity_returnsNullTest() {
        assertNull(mapper.toDto(null));
    }

    @Test
    void toEntityCreate_nullDto_returnsNullTest() {
        assertNull(mapper.toEntityCreate(null));
    }

    @Test
    void toEntityCreate_validDto_mapsCorrectlyTest() {
        RutaEntradaCreateDto dto = new RutaEntradaCreateDto("Ruta Bosque", "Media", 7200L,
                8.5f, 120.0f, true, 1, null, null, null, List.of(1, 2));
        Ruta ruta = mapper.toEntityCreate(dto);

        assertEquals("Ruta Bosque", ruta.getNombre());
        assertEquals("Media", ruta.getDificultad());
        assertEquals(7200L, ruta.getTiempoDuracion());
        assertEquals(8.5f, ruta.getDistanciaMetros());
        assertEquals(120.0f, ruta.getDesnivel());
        assertTrue(ruta.getAprobada());
    }

    @Test
    void toEntityUpdate_nullDto_returnsNullTest() {
        assertNull(mapper.toEntityUpdate(null));
    }

    @Test
    void toEntityUpdate_validDto_mapsCorrectlyTest() {
        RutaEntradaUpdateDto dto = new RutaEntradaUpdateDto(1, "Ruta Volcánica", "Alta",
                10800L, 15.2f, 300.0f, false, 1, null, null, null, List.of(1, 2));
        Ruta ruta = mapper.toEntityUpdate(dto);

        assertEquals(1, ruta.getId());
        assertEquals("Ruta Volcánica", ruta.getNombre());
        assertEquals("Alta", ruta.getDificultad());
        assertEquals(10800L, ruta.getTiempoDuracion());
        assertEquals(15.2f, ruta.getDistanciaMetros());
        assertEquals(300.0f, ruta.getDesnivel());
        assertFalse(ruta.getAprobada());
    }
}
