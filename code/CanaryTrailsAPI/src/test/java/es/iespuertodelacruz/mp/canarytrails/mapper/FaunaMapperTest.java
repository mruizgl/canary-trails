package es.iespuertodelacruz.mp.canarytrails.mapper;

import es.iespuertodelacruz.mp.canarytrails.dto.fauna.*;
import es.iespuertodelacruz.mp.canarytrails.entities.Fauna;
import es.iespuertodelacruz.mp.canarytrails.entities.Ruta;
import es.iespuertodelacruz.mp.canarytrails.entities.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class FaunaMapperTest {

    @Autowired
    FaunaMapper faunaMapper;

    @Test
    void toEntityCreate_validDto_mapsCorrectlyTest() {
        FaunaEntradaCreateDto dto = new FaunaEntradaCreateDto("Lagarto", "Descripción del lagarto", true, 1, List.of(1, 2));
        Fauna fauna = faunaMapper.toEntityCreate(dto);

        assertEquals("Lagarto", fauna.getNombre());
        assertEquals("Descripción del lagarto", fauna.getDescripcion());
        assertTrue(fauna.getAprobada());
    }

    @Test
    void toEntityCreate_nullDto_returnsNullTest() {
        assertNull(faunaMapper.toEntityCreate(null));
    }

    @Test
    void toEntityUpdate_validDto_mapsCorrectlyTest() {
        FaunaEntradaUpdateDto dto = new FaunaEntradaUpdateDto(10, "Lagarto Actualizado", "Nueva descripción", true, 1, List.of(1));
        Fauna fauna = faunaMapper.toEntityUpdate(dto);

        assertEquals(10, fauna.getId());
        assertEquals("Lagarto Actualizado", fauna.getNombre());
        assertEquals("Nueva descripción", fauna.getDescripcion());
        assertTrue(fauna.getAprobada());
    }

    @Test
    void toEntityUpdate_nullDto_returnsNullTest() {
        assertNull(faunaMapper.toEntityUpdate(null));
    }

    @Test
    void toDto_validEntity_mapsCorrectlyTest() {
        Usuario usuario = new Usuario();
        usuario.setId(1);
        usuario.setNombre("Carlos");

        Ruta ruta = new Ruta();
        ruta.setId(99);
        ruta.setNombre("Sendero Monteverde");

        Fauna fauna = new Fauna();
        fauna.setId(20);
        fauna.setNombre("Gavilán");
        fauna.setDescripcion("Ave rapaz diurna");
        fauna.setAprobada(true);
        fauna.setUsuario(usuario);
        fauna.setRutas(List.of(ruta));
        fauna.setFoto("foto.png");

        FaunaSalidaDto dto = faunaMapper.toDto(fauna);

        assertNotNull(dto);
        assertEquals("Gavilán", dto.nombre());
        assertEquals("Ave rapaz diurna", dto.descripcion());
        assertTrue(dto.aprobada());
        assertNotNull(dto.usuario());
        assertEquals("Carlos", dto.usuario().nombre());
        assertEquals(1, dto.rutas().size());
        assertEquals("Sendero Monteverde", dto.rutas().get(0).nombre());
        assertEquals("foto.png", dto.foto());
    }

    @Test
    void toDto_nullEntity_returnsNullTest() {
        assertNull(faunaMapper.toDto(null));
    }

    @Test
    void rutaListToRutaSalidaFaunaDtoList_null_returnsNullTest() {
        FaunaMapperImpl mapper = new FaunaMapperImpl();
        assertNull(mapper.rutaListToRutaSalidaFaunaDtoList(null));
    }
}
