package es.iespuertodelacruz.mp.canarytrails.mapper;

import es.iespuertodelacruz.mp.canarytrails.dto.coordenada.*;
import es.iespuertodelacruz.mp.canarytrails.dto.ruta.CoordenadaSalidaRutaDto;
import es.iespuertodelacruz.mp.canarytrails.entities.Coordenada;
import es.iespuertodelacruz.mp.canarytrails.entities.Ruta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class CoordenadaMapperTest {

    @Mock
    private RelacionesMapper relacionesMapper;

    @Spy
    @InjectMocks
    private CoordenadaMapperImpl coordenadaMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void toDTO_nullInput_returnsNull() {
        assertThat(coordenadaMapper.toDTO(null)).isNull();
    }

    @Test
    void toDTO_validInput_mapsCorrectly() {
        Coordenada coordenada = new Coordenada();
        coordenada.setId(1);
        coordenada.setLatitud(BigDecimal.valueOf(28.123456));
        coordenada.setLongitud(BigDecimal.valueOf(-16.654321));

        Ruta ruta = new Ruta();
        coordenada.setRutas(List.of(ruta));

        RutaSalidaCoordenadaDto rutaDto = new RutaSalidaCoordenadaDto(1, "Ruta Test", "Fácil", 600L, 2000f, 150f, true);
        when(relacionesMapper.toSalidaCoordenadaDto(ruta)).thenReturn(rutaDto);

        CoordenadaSalidaDto dto = coordenadaMapper.toDTO(coordenada);

        assertThat(dto).isNotNull();
        assertThat(dto.id()).isEqualTo(1);
        assertThat(dto.latitud()).isEqualTo(BigDecimal.valueOf(28.123456));
        assertThat(dto.longitud()).isEqualTo(BigDecimal.valueOf(-16.654321));
        assertThat(dto.rutas()).containsExactly(rutaDto);
    }

    @Test
    void toEntityCreate_nullInput_returnsNull() {
        assertThat(coordenadaMapper.toEntityCreate(null)).isNull();
    }

    @Test
    void toEntityCreate_validInput_mapsCorrectly() {
        CoordenadaEntradaCreateDto dto = new CoordenadaEntradaCreateDto(BigDecimal.valueOf(28.5), BigDecimal.valueOf(-16.3));
        Coordenada entity = coordenadaMapper.toEntityCreate(dto);

        assertThat(entity).isNotNull();
        assertThat(entity.getLatitud()).isEqualTo(BigDecimal.valueOf(28.5));
        assertThat(entity.getLongitud()).isEqualTo(BigDecimal.valueOf(-16.3));
    }

    @Test
    void toEntityUpdate_nullInput_returnsNull() {
        assertThat(coordenadaMapper.toEntityUpdate(null)).isNull();
    }

    @Test
    void toEntityUpdate_validInput_mapsCorrectly() {
        CoordenadaEntradaUpdateDto dto = new CoordenadaEntradaUpdateDto(5, BigDecimal.valueOf(27.9), BigDecimal.valueOf(-15.7));
        Coordenada entity = coordenadaMapper.toEntityUpdate(dto);

        assertThat(entity).isNotNull();
        assertThat(entity.getId()).isEqualTo(5);
        assertThat(entity.getLatitud()).isEqualTo(BigDecimal.valueOf(27.9));
        assertThat(entity.getLongitud()).isEqualTo(BigDecimal.valueOf(-15.7));
    }

    @Test
    void toDTOList_nullInput_returnsNull() {
        assertThat(coordenadaMapper.toDTOList(null)).isNull();
    }

    @Test
    void toDTOList_validInput_mapsCorrectly() {
        Coordenada coord = new Coordenada();
        coord.setId(1);
        coord.setLatitud(BigDecimal.ONE);
        coord.setLongitud(BigDecimal.TEN);

        doReturn(new CoordenadaSalidaDto(1, BigDecimal.ONE, BigDecimal.TEN, null))
                .when(coordenadaMapper).toDTO(coord);

        List<CoordenadaSalidaDto> list = coordenadaMapper.toDTOList(List.of(coord));

        assertThat(list).hasSize(1);
        assertThat(list.get(0).id()).isEqualTo(1);
    }

    @Test
    void toSalidaRutaDto_nullInput_returnsNull() {
        assertThat(coordenadaMapper.toSalidaRutaDto(null)).isNull();
    }

    @Test
    void toSalidaRutaDto_validInput_mapsCorrectly() {
        Coordenada coord = new Coordenada();
        coord.setId(7);
        coord.setLatitud(BigDecimal.valueOf(28.0));
        coord.setLongitud(BigDecimal.valueOf(-16.0));

        CoordenadaSalidaRutaDto dto = coordenadaMapper.toSalidaRutaDto(coord);

        assertThat(dto).isNotNull();
        assertThat(dto.id()).isEqualTo(7);
        assertThat(dto.latitud()).isEqualTo(BigDecimal.valueOf(28.0));
        assertThat(dto.longitud()).isEqualTo(BigDecimal.valueOf(-16.0));
    }

    @Test
    void rutaListToRutaSalidaCoordenadaDtoList_nullInput_returnsNull() {
        assertThat(coordenadaMapper.rutaListToRutaSalidaCoordenadaDtoList(null)).isNull();
    }

    @Test
    void rutaListToRutaSalidaCoordenadaDtoList_validInput_mapsCorrectly() {
        Ruta ruta = new Ruta();
        RutaSalidaCoordenadaDto dto = new RutaSalidaCoordenadaDto(1, "Ruta", "Media", 300L, 1500f, 100f, true);

        when(relacionesMapper.toSalidaCoordenadaDto(ruta)).thenReturn(dto);

        List<RutaSalidaCoordenadaDto> result = coordenadaMapper.rutaListToRutaSalidaCoordenadaDtoList(List.of(ruta));

        assertThat(result).hasSize(1);
        assertThat(result.get(0).id()).isEqualTo(1);
    }
}
