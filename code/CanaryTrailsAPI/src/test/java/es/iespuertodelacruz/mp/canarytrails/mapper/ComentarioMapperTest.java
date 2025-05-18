package es.iespuertodelacruz.mp.canarytrails.mapper;

import es.iespuertodelacruz.mp.canarytrails.dto.comentario.*;
import es.iespuertodelacruz.mp.canarytrails.entities.Comentario;
import es.iespuertodelacruz.mp.canarytrails.entities.Ruta;
import es.iespuertodelacruz.mp.canarytrails.entities.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ComentarioMapperTest {

    @Autowired
    ComentarioMapper comentarioMapper;

    private ComentarioMapperImpl mapper;
    private RelacionesMapper relacionesMapper;

    @BeforeEach
    void setUp() {
        mapper = new ComentarioMapperImpl();
        relacionesMapper = new RelacionesMapperImpl();
    }

    @Test
    void toDto_nullComentario_returnsNullTest() {
        assertNull(mapper.toDto(null));
    }

    @Test
    void toDto_validComentario_mapsCorrectlyTest() {
        Usuario usuario = new Usuario();
        usuario.setId(1);
        usuario.setNombre("Carlos");

        Ruta ruta = new Ruta();
        ruta.setId(2);
        ruta.setNombre("Ruta del Norte");

        Comentario comentario = new Comentario();
        comentario.setId(5);
        comentario.setTitulo("Espectacular");
        comentario.setDescripcion("Muy recomendable");
        comentario.setUsuario(usuario);
        comentario.setRuta(ruta);

        ComentarioSalidaDto dto = comentarioMapper.toDto(comentario);

        assertNotNull(dto);
        assertEquals(5, dto.id());
        assertEquals("Espectacular", dto.titulo());
        assertEquals("Muy recomendable", dto.descripcion());
        assertEquals("Carlos", dto.usuario().nombre());
        assertEquals("Ruta del Norte", dto.ruta().nombre());
    }

    @Test
    void toEntityCreate_nullDto_returnsNullTest() {
        assertNull(mapper.toEntityCreate(null));
    }

    @Test
    void toEntityCreate_validDto_mapsCorrectlyTest() {
        ComentarioEntradaCreateDto dto = new ComentarioEntradaCreateDto("Título", "Contenido del comentario", 1, 1);

        Comentario comentario = mapper.toEntityCreate(dto);

        assertEquals("Título", comentario.getTitulo());
        assertEquals("Contenido del comentario", comentario.getDescripcion());
    }

    @Test
    void toEntityUpdate_nullDto_returnsNullTest() {
        assertNull(mapper.toEntityUpdate(null));
    }

    @Test
    void toEntityUpdate_validDto_mapsCorrectlyTest() {
        ComentarioEntradaUpdateDto dto = new ComentarioEntradaUpdateDto(10, "Nuevo título", "Nuevo contenido", 1, 1);

        Comentario comentario = mapper.toEntityUpdate(dto);

        assertEquals(10, comentario.getId());
        assertEquals("Nuevo título", comentario.getTitulo());
        assertEquals("Nuevo contenido", comentario.getDescripcion());
    }
}
