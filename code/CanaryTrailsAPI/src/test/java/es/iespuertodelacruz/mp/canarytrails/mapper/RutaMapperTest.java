package es.iespuertodelacruz.mp.canarytrails.mapper;

import es.iespuertodelacruz.mp.canarytrails.dto.ruta.*;
import es.iespuertodelacruz.mp.canarytrails.entities.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class RutaMapperTest {

    @Mock
    private RelacionesMapper relacionesMapper;

    @Spy
    @InjectMocks
    private RutaMapperImpl rutaMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void toDto_nullEntity_returnsNull() {
        assertThat(rutaMapper.toDto(null)).isNull();
    }

    @Test
    void toDto_validEntity_mapsCorrectly() {
        Ruta ruta = new Ruta();
        ruta.setId(1);
        ruta.setNombre("Ruta Montaña");
        ruta.setDificultad("Media");
        ruta.setTiempoDuracion(3600L);
        ruta.setDistanciaMetros(4500.0f);
        ruta.setDesnivel(300.0f);
        ruta.setAprobada(true);

        Usuario usuario = new Usuario();
        usuario.setId(10);
        usuario.setNombre("Usuario");
        ruta.setUsuario(usuario);

        Comentario comentario = new Comentario();
        Fauna fauna = new Fauna();
        Flora flora = new Flora();
        Coordenada coord = new Coordenada();
        Municipio municipio = new Municipio();

        ruta.setComentarios(List.of(comentario));
        ruta.setFaunas(List.of(fauna));
        ruta.setFloras(List.of(flora));
        ruta.setCoordenadas(List.of(coord));
        ruta.setMunicipios(List.of(municipio));

        when(relacionesMapper.toSalidaRutaDto(usuario)).thenReturn(
                new UsuarioSalidaRutaDto(10, "Usuario", "correo@test.com", "pass", true, "USER")
        );
        when(relacionesMapper.toSalidaRutaDto(comentario)).thenReturn(
                new ComentarioSalidaRutaDto(1, "Título", "Texto",
                        new UsuarioSalidaRutaDto(10, "Usuario", "correo@test.com", "pass", true, "USER"))
        );
        when(relacionesMapper.toSalidaRutaDto(fauna)).thenReturn(
                new FaunaSalidaRutaDto(1, "Fauna", "Descripción", true, "foto")
        );
        when(relacionesMapper.toSalidaRutaDto(flora)).thenReturn(
                new FloraSalidaRutaDto(1, "Flora", "Especie", "Hoja", "Primavera", "Verano", "Desc", true, "foto")
        );
        when(relacionesMapper.toSalidaRutaDto(coord)).thenReturn(
                new CoordenadaSalidaRutaDto(1, null, null)
        );
        when(relacionesMapper.toSalidaRutaDto(municipio)).thenReturn(
                new MunicipioSalidaRutaDto(1, "Municipio", 200, 28.5f, -16.3f)
        );

        RutaSalidaDto dto = rutaMapper.toDto(ruta);

        assertThat(dto).isNotNull();
        assertThat(dto.id()).isEqualTo(1);
        assertThat(dto.nombre()).isEqualTo("Ruta Montaña");
        assertThat(dto.faunas()).hasSize(1);
        assertThat(dto.floras()).hasSize(1);
        assertThat(dto.comentarios()).hasSize(1);
        assertThat(dto.coordenadas()).hasSize(1);
        assertThat(dto.municipios()).hasSize(1);
    }

    @Test
    void toEntityCreate_nullInput_returnsNull() {
        assertThat(rutaMapper.toEntityCreate(null)).isNull();
    }

    @Test
    void toEntityCreate_validInput_mapsCorrectly() {
        CoordenadaEntradaCreate coordDto = new CoordenadaEntradaCreate(new BigDecimal("33.222222"), new BigDecimal("22.333333"));

        RutaEntradaCreateDto dto = new RutaEntradaCreateDto(
                "Sendero", "Fácil", 1800L, 2000.0f, 120.0f, false,
                1, List.of(2), List.of(3), List.of(coordDto), List.of(5)
        );

        Ruta result = rutaMapper.toEntityCreate(dto);

        assertThat(result).isNotNull();
        assertThat(result.getNombre()).isEqualTo("Sendero");
        assertThat(result.getDificultad()).isEqualTo("Fácil");
        assertThat(result.getTiempoDuracion()).isEqualTo(1800L);
        assertThat(result.getDistanciaMetros()).isEqualTo(2000.0f);
        assertThat(result.getDesnivel()).isEqualTo(120.0f);
        assertThat(result.getAprobada()).isFalse();
    }

    @Test
    void toEntityUpdate_nullInput_returnsNull() {
        assertThat(rutaMapper.toEntityUpdate(null)).isNull();
    }

    @Test
    void toEntityUpdate_validInput_mapsCorrectly() {
        RutaEntradaUpdateDto dto = new RutaEntradaUpdateDto(
                3, "Cumbre", "Alta", 7200L, 8000.0f, 500.0f, true,
                1, List.of(2), List.of(3), List.of(4), List.of(5)
        );

        Ruta result = rutaMapper.toEntityUpdate(dto);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(3);
        assertThat(result.getNombre()).isEqualTo("Cumbre");
        assertThat(result.getAprobada()).isTrue();
    }

    @Test
    void comentarioListToDtoList_mapsCorrectly() {
        Comentario comentario = new Comentario();
        when(relacionesMapper.toSalidaRutaDto(comentario)).thenReturn(
                new ComentarioSalidaRutaDto(1, "Comentario", "Contenido",
                        new UsuarioSalidaRutaDto(99, "Pepe", "pepe@mail.com", "clave", false, "USER"))
        );

        List<ComentarioSalidaRutaDto> result = rutaMapper.comentarioListToComentarioSalidaRutaDtoList(List.of(comentario));
        assertThat(result).hasSize(1);
        assertThat(result.get(0).usuario().nombre()).isEqualTo("Pepe");
    }

    @Test
    void faunaListToDtoList_mapsCorrectly() {
        Fauna fauna = new Fauna();
        when(relacionesMapper.toSalidaRutaDto(fauna)).thenReturn(new FaunaSalidaRutaDto(2, "Lagarto", "Reptil", true, "foto"));

        List<FaunaSalidaRutaDto> result = rutaMapper.faunaListToFaunaSalidaRutaDtoList(List.of(fauna));
        assertThat(result).hasSize(1);
    }

    @Test
    void floraListToDtoList_mapsCorrectly() {
        Flora flora = new Flora();
        when(relacionesMapper.toSalidaRutaDto(flora)).thenReturn(
                new FloraSalidaRutaDto(3, "Pino", "Pinus", "Agujas", "Ene", "Jun", "Descripción", true, "foto")
        );

        List<FloraSalidaRutaDto> result = rutaMapper.floraListToFloraSalidaRutaDtoList(List.of(flora));
        assertThat(result).hasSize(1);
    }

    @Test
    void coordenadaListToDtoList_mapsCorrectly() {
        Coordenada coord = new Coordenada();
        when(relacionesMapper.toSalidaRutaDto(coord)).thenReturn(new CoordenadaSalidaRutaDto(4, null, null));

        List<CoordenadaSalidaRutaDto> result = rutaMapper.coordenadaListToCoordenadaSalidaRutaDtoList(List.of(coord));
        assertThat(result).hasSize(1);
    }

    @Test
    void municipioListToDtoList_mapsCorrectly() {
        Municipio municipio = new Municipio();
        when(relacionesMapper.toSalidaRutaDto(municipio)).thenReturn(
                new MunicipioSalidaRutaDto(5, "El Paso", 300, 28.0f, -16.0f)
        );

        List<MunicipioSalidaRutaDto> result = rutaMapper.municipioListToMunicipioSalidaRutaDtoList(List.of(municipio));
        assertThat(result).hasSize(1);
    }
}
