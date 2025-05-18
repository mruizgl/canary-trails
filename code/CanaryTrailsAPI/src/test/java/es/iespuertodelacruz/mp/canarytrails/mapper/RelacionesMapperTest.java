package es.iespuertodelacruz.mp.canarytrails.mapper;

import es.iespuertodelacruz.mp.canarytrails.dto.comentario.*;
import es.iespuertodelacruz.mp.canarytrails.dto.coordenada.*;
import es.iespuertodelacruz.mp.canarytrails.dto.fauna.*;
import es.iespuertodelacruz.mp.canarytrails.dto.flora.*;
import es.iespuertodelacruz.mp.canarytrails.dto.municipio.*;
import es.iespuertodelacruz.mp.canarytrails.dto.ruta.*;
import es.iespuertodelacruz.mp.canarytrails.dto.usuario.*;
import es.iespuertodelacruz.mp.canarytrails.dto.zona.*;
import es.iespuertodelacruz.mp.canarytrails.entities.*;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

public class RelacionesMapperTest {

    private final RelacionesMapper mapper = Mappers.getMapper(RelacionesMapper.class);

    @Test
    void zonaRelacionesTest() {
        Zona zona = new Zona();
        zona.setId(1);
        zona.setNombre("Zona Norte");

        Municipio municipio = new Municipio();
        municipio.setId(2);
        municipio.setNombre("La Orotava");
        municipio.setZona(zona);

        MunicipioSalidaZonaDto municipioDto = mapper.toSalidaZonaDto(municipio);
        assertEquals(2, municipioDto.id());
        assertEquals("La Orotava", municipioDto.nombre());
    }

    @Test
    void municipioRelacionesTest() {
        Zona zona = new Zona();
        zona.setId(1);
        zona.setNombre("Centro");

        Ruta ruta = new Ruta();
        ruta.setId(5);
        ruta.setNombre("Ruta Central");

        ZonaSalidaMunicipioDto dto1 = mapper.toSalidaMunicipioDto(zona);
        assertEquals(1, dto1.id());
        assertEquals("Centro", dto1.nombre());

        RutaSalidaMunicipioDto dto2 = mapper.toSalidaMunicipioDto(ruta);
        assertEquals(5, dto2.id());
        assertEquals("Ruta Central", dto2.nombre());
    }

    @Test
    void rutasRelacionesTest() {
        Usuario usuario = new Usuario();
        usuario.setId(1);
        usuario.setNombre("Ana");

        Comentario comentario = new Comentario();
        comentario.setId(10);
        comentario.setTitulo("Muy buena");

        Coordenada coordenada = new Coordenada();
        coordenada.setId(20);
        coordenada.setLatitud(BigDecimal.valueOf(123.45));
        coordenada.setLongitud(BigDecimal.valueOf(123.45));

        Fauna fauna = new Fauna();
        fauna.setId(30);
        fauna.setNombre("Lagarto");

        Flora flora = new Flora();
        flora.setId(40);
        flora.setNombre("Cardón");

        Municipio municipio = new Municipio();
        municipio.setId(50);
        municipio.setNombre("Tacoronte");

        assertNotNull(mapper.toSalidaRutaDto(usuario));
        assertNotNull(mapper.toSalidaRutaDto(comentario));
        assertNotNull(mapper.toSalidaRutaDto(coordenada));
        assertNotNull(mapper.toSalidaRutaDto(fauna));
        assertNotNull(mapper.toSalidaRutaDto(flora));
        assertNotNull(mapper.toSalidaRutaDto(municipio));
    }

    @Test
    void usuariosRelacionesTest() {
        Comentario comentario = new Comentario();
        comentario.setId(10);
        comentario.setTitulo("Comentario");

        Fauna fauna = new Fauna();
        fauna.setId(20);
        fauna.setNombre("Lagarto");

        Flora flora = new Flora();
        flora.setId(30);
        flora.setNombre("Margarita");

        Ruta ruta = new Ruta();
        ruta.setId(40);
        ruta.setNombre("Ruta sur");

        RutaFavoritaSalidaUsuarioDto dto = mapper.toSalidaUsuarioRutaFavoritaDto(ruta);
        assertEquals(40, dto.id());
        assertEquals("Ruta sur", dto.nombre());

        assertNotNull(mapper.toSalidaUsuarioDto(comentario));
        assertNotNull(mapper.toSalidaUsuarioDto(fauna));
        assertNotNull(mapper.toSalidaUsuarioDto(flora));
        assertNotNull(mapper.toSalidaUsuarioDto(ruta));
    }

    @Test
    void faunaRelacionesTest() {
        Usuario usuario = new Usuario();
        usuario.setId(5);
        usuario.setNombre("Carlos");

        Ruta ruta = new Ruta();
        ruta.setId(99);
        ruta.setNombre("Monteverde");

        UsuarioSalidaFaunaDto dto1 = mapper.toSalidaFaunaDto(usuario);
        RutaSalidaFaunaDto dto2 = mapper.toSalidaFaunaDto(ruta);

        assertEquals(5, dto1.id());
        assertEquals("Carlos", dto1.nombre());
        assertEquals(99, dto2.id());
        assertEquals("Monteverde", dto2.nombre());
    }

    @Test
    void floraRelacionesTest() {
        Usuario usuario = new Usuario();
        usuario.setId(6);
        usuario.setNombre("Elena");

        Ruta ruta = new Ruta();
        ruta.setId(88);
        ruta.setNombre("Floral Trek");

        UsuarioSalidaFloraDto dto1 = mapper.toSalidaFloraDto(usuario);
        RutaSalidaFloraDto dto2 = mapper.toSalidaFloraDto(ruta);

        assertEquals(6, dto1.id());
        assertEquals("Elena", dto1.nombre());
        assertEquals(88, dto2.id());
        assertEquals("Floral Trek", dto2.nombre());
    }

    @Test
    void coordenadaRelacionesTest() {
        Ruta ruta = new Ruta();
        ruta.setId(77);
        ruta.setNombre("Camino viejo");

        RutaSalidaCoordenadaDto dto = mapper.toSalidaCoordenadaDto(ruta);

        assertEquals(77, dto.id());
        assertEquals("Camino viejo", dto.nombre());
    }

    @Test
    void comentarioRelacionesTest() {
        Usuario usuario = new Usuario();
        usuario.setId(7);
        usuario.setNombre("Pepe");

        Ruta ruta = new Ruta();
        ruta.setId(42);
        ruta.setNombre("Cumbre Trail");

        UsuarioSalidaComentarioDto dto1 = mapper.toSalidaComentario(usuario);
        RutaSalidaComentarioDto dto2 = mapper.toSalidaComentario(ruta);

        assertEquals(7, dto1.id());
        assertEquals("Pepe", dto1.nombre());
        assertEquals(42, dto2.id());
        assertEquals("Cumbre Trail", dto2.nombre());
    }
}
