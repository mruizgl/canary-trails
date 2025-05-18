package es.iespuertodelacruz.mp.canarytrails.mapper;

import es.iespuertodelacruz.mp.canarytrails.dto.zona.ZonaEntradaCreateDto;
import es.iespuertodelacruz.mp.canarytrails.dto.zona.ZonaEntradaUpdateDto;
import es.iespuertodelacruz.mp.canarytrails.dto.zona.ZonaSalidaDto;
import es.iespuertodelacruz.mp.canarytrails.entities.Zona;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ZonaMapperTest {

    private ZonaMapperImpl mapper;

    @BeforeEach
    void setUp() {
        mapper = new ZonaMapperImpl();
    }

    @Test
    void toDTO_nullZona_returnsNullTest() {
        assertNull(mapper.toDTO(null));
    }

    @Test
    void toDTO_validZona_mapsCorrectlyTest() {
        Zona zona = new Zona();
        zona.setId(1);
        zona.setNombre("Zona Norte");

        ZonaSalidaDto dto = mapper.toDTO(zona);

        assertNotNull(dto);
        assertEquals(1, dto.id());
        assertEquals("Zona Norte", dto.nombre());

    }

    @Test
    void toEntityCreate_nullDto_returnsNullTest() {
        assertNull(mapper.toEntityCreate(null));
    }

    @Test
    void toEntityCreate_validDto_mapsCorrectlyTest() {
        ZonaEntradaCreateDto dto = new ZonaEntradaCreateDto("Zona Sur", List.of(1, 2));
        Zona zona = mapper.toEntityCreate(dto);

        assertEquals("Zona Sur", zona.getNombre());
    }

    @Test
    void toEntityUpdate_nullDto_returnsNullTest() {
        assertNull(mapper.toEntityUpdate(null));
    }

    @Test
    void toEntityUpdate_validDto_mapsCorrectlyTest() {
        ZonaEntradaUpdateDto dto = new ZonaEntradaUpdateDto(1, "Zona Centro", List.of(1, 2));
        Zona zona = mapper.toEntityUpdate(dto);

        assertEquals(1, zona.getId());
        assertEquals("Zona Centro", zona.getNombre());
    }

    @Test
    void toDTOList_null_returnsNullTest() {
        assertNull(mapper.toDTOList(null));
    }

    @Test
    void toDTOList_validList_mapsCorrectlyTest() {
        Zona zona1 = new Zona();
        zona1.setId(1);
        zona1.setNombre("Zona Este");

        Zona zona2 = new Zona();
        zona2.setId(2);
        zona2.setNombre("Zona Oeste");

        List<ZonaSalidaDto> dtos = mapper.toDTOList(List.of(zona1, zona2));

        assertEquals(2, dtos.size());
        assertEquals("Zona Este", dtos.get(0).nombre());
        assertEquals("Zona Oeste", dtos.get(1).nombre());
    }
}
