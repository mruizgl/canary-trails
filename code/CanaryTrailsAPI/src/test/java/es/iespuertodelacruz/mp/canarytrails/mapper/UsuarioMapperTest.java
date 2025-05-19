package es.iespuertodelacruz.mp.canarytrails.mapper;

import es.iespuertodelacruz.mp.canarytrails.dto.usuario.*;
import es.iespuertodelacruz.mp.canarytrails.entities.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioMapperTest {

    @Mock
    private RelacionesMapper relacionesMapper;

    @Spy
    @InjectMocks
    private UsuarioMapperImpl usuarioMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void toDto_nullUsuario_returnsNull() {
        assertNull(usuarioMapper.toDto(null));
    }

    @Test
    void toDto_validUsuario_mapsCorrectly() {
        Fauna fauna = new Fauna();
        Flora flora = new Flora();
        Ruta ruta = new Ruta();
        Comentario comentario = new Comentario();
        Ruta rutaFavorita = new Ruta();

        Usuario usuario = new Usuario();
        usuario.setId(1);
        usuario.setNombre("Ana");
        usuario.setCorreo("ana@test.com");
        usuario.setPassword("1234");
        usuario.setTokenVerificacion("abc");
        usuario.setFechaCreacion(new Date());
        usuario.setVerificado(true);
        usuario.setRol("USER");
        usuario.setFoto("foto.jpg");
        usuario.setFaunas(List.of(fauna));
        usuario.setFloras(List.of(flora));
        usuario.setRutas(List.of(ruta));
        usuario.setComentarios(List.of(comentario));
        usuario.setRutasFavoritas(List.of(rutaFavorita));

        when(relacionesMapper.toSalidaUsuarioDto(fauna))
                .thenReturn(new FaunaSalidaUsuarioDto(1, "Lagarto Canario", "Reptil endémico", true));
        when(relacionesMapper.toSalidaUsuarioDto(flora))
                .thenReturn(new FloraSalidaUsuarioDto(2, "Pino Canario", "Pinus", "Agujas", "Febrero", "Agosto", "Resistente", true));
        when(relacionesMapper.toSalidaUsuarioDto(ruta))
                .thenReturn(new RutaSalidaUsuarioDto(3, "Ruta Sur", "Media", 1200L, 3200f, 250f, true));
        when(relacionesMapper.toSalidaUsuarioDto(comentario))
                .thenReturn(new ComentarioSalidaUsuarioDto(4, "Buen sitio", "Precioso recorrido"));
        when(relacionesMapper.toSalidaUsuarioRutaFavoritaDto(rutaFavorita))
                .thenReturn(new RutaFavoritaSalidaUsuarioDto(5, "Ruta Norte", "Fácil", 800L, 1400f, 60f, true));

        UsuarioSalidaDto dto = usuarioMapper.toDto(usuario);

        assertThat(dto).isNotNull();
        assertThat(dto.id()).isEqualTo(1);
        assertThat(dto.nombre()).isEqualTo("Ana");
        assertThat(dto.faunas()).hasSize(1);
        assertThat(dto.floras()).hasSize(1);
        assertThat(dto.rutas()).hasSize(1);
        assertThat(dto.comentarios()).hasSize(1);
        assertThat(dto.rutasFavoritas()).hasSize(1);
        assertThat(dto.foto()).isEqualTo("foto.jpg");
    }

    @Test
    void toEntityCreate_nullDto_returnsNull() {
        assertNull(usuarioMapper.toEntityCreate(null));
    }

    @Test
    void toEntityCreate_validDto_mapsCorrectly() {
        UsuarioEntradaCreateDto dto = new UsuarioEntradaCreateDto("Sara", "sara@test.com", "1234", true, "ADMIN");
        Usuario usuario = usuarioMapper.toEntityCreate(dto);

        assertThat(usuario).isNotNull();
        assertThat(usuario.getNombre()).isEqualTo("Sara");
        assertThat(usuario.getCorreo()).isEqualTo("sara@test.com");
        assertThat(usuario.getPassword()).isEqualTo("1234");
        assertThat(usuario.getVerificado()).isTrue();
        assertThat(usuario.getRol()).isEqualTo("ADMIN");
    }

    @Test
    void toEntityUpdate_nullDto_returnsNull() {
        assertNull(usuarioMapper.toEntityUpdate(null));
    }

    @Test
    void toEntityUpdate_validDto_mapsCorrectly() {
        UsuarioEntradaUpdateDto dto = new UsuarioEntradaUpdateDto(3, "Luis", "luis@test.com", "abcd", false, "MOD");
        Usuario usuario = usuarioMapper.toEntityUpdate(dto);

        assertThat(usuario).isNotNull();
        assertThat(usuario.getId()).isEqualTo(3);
        assertThat(usuario.getNombre()).isEqualTo("Luis");
        assertThat(usuario.getCorreo()).isEqualTo("luis@test.com");
        assertThat(usuario.getPassword()).isEqualTo("abcd");
        assertThat(usuario.getVerificado()).isFalse();
        assertThat(usuario.getRol()).isEqualTo("MOD");
    }

    @Test
    void faunaListToDtoList_nullInput_returnsNull() {
        assertNull(usuarioMapper.faunaListToFaunaSalidaUsuarioDtoList(null));
    }

    @Test
    void faunaListToDtoList_validInput_mapsCorrectly() {
        Fauna fauna = new Fauna();
        when(relacionesMapper.toSalidaUsuarioDto(fauna))
                .thenReturn(new FaunaSalidaUsuarioDto(1, "Murciélago", "Único mamífero volador", true));
        List<FaunaSalidaUsuarioDto> result = usuarioMapper.faunaListToFaunaSalidaUsuarioDtoList(List.of(fauna));
        assertThat(result).hasSize(1);
    }

    @Test
    void floraListToDtoList_validInput_mapsCorrectly() {
        Flora flora = new Flora();
        when(relacionesMapper.toSalidaUsuarioDto(flora))
                .thenReturn(new FloraSalidaUsuarioDto(2, "Laurel", "Laurus", "Ovalada", "Enero", "Mayo", "Planta endémica", true));
        List<FloraSalidaUsuarioDto> result = usuarioMapper.floraListToFloraSalidaUsuarioDtoList(List.of(flora));
        assertThat(result).hasSize(1);
    }

    @Test
    void rutaListToDtoList_validInput_mapsCorrectly() {
        Ruta ruta = new Ruta();
        when(relacionesMapper.toSalidaUsuarioDto(ruta))
                .thenReturn(new RutaSalidaUsuarioDto(3, "Sendero Azul", "Difícil", 900L, 1500f, 400f, false));
        List<RutaSalidaUsuarioDto> result = usuarioMapper.rutaListToRutaSalidaUsuarioDtoList(List.of(ruta));
        assertThat(result).hasSize(1);
    }

    @Test
    void comentarioListToDtoList_validInput_mapsCorrectly() {
        Comentario comentario = new Comentario();
        when(relacionesMapper.toSalidaUsuarioDto(comentario))
                .thenReturn(new ComentarioSalidaUsuarioDto(4, "Opinión", "Muy bueno"));
        List<ComentarioSalidaUsuarioDto> result = usuarioMapper.comentarioListToComentarioSalidaUsuarioDtoList(List.of(comentario));
        assertThat(result).hasSize(1);
    }

    @Test
    void rutaFavoritaListToDtoList_validInput_mapsCorrectly() {
        Ruta ruta = new Ruta();
        when(relacionesMapper.toSalidaUsuarioRutaFavoritaDto(ruta))
                .thenReturn(new RutaFavoritaSalidaUsuarioDto(5, "Camino Rojo", "Media", 1000L, 2000f, 75f, false));
        List<RutaFavoritaSalidaUsuarioDto> result = usuarioMapper.rutaListToRutaFavoritaSalidaUsuarioDtoList(List.of(ruta));
        assertThat(result).hasSize(1);
    }
}
