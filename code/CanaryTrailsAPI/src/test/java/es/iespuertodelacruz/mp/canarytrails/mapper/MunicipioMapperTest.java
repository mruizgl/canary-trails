package es.iespuertodelacruz.mp.canarytrails.mapper;

import es.iespuertodelacruz.mp.canarytrails.dto.municipio.*;
import es.iespuertodelacruz.mp.canarytrails.dto.municipio.RutaSalidaMunicipioDto;
import es.iespuertodelacruz.mp.canarytrails.dto.municipio.ZonaSalidaMunicipioDto;
import es.iespuertodelacruz.mp.canarytrails.entities.Municipio;
import es.iespuertodelacruz.mp.canarytrails.entities.Ruta;
import es.iespuertodelacruz.mp.canarytrails.entities.Zona;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MunicipioMapperTest {

    @Mock
    private RelacionesMapper relacionesMapper;

    @Spy
    @InjectMocks
    private MunicipioMapperImpl municipioMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void toDto_nullMunicipio_returnsNullTest() {
        assertNull(municipioMapper.toDto(null));
    }

    @Test
    void toEntityCreate_nullDto_returnsNullTest() {
        assertNull(municipioMapper.toEntityCreate(null));
    }

    @Test
    void toEntityCreate_validDto_mapsCorrectlyTest() {
        MunicipioEntradaCreateDto dto = new MunicipioEntradaCreateDto(
                "Tacoronte", 350, 28.4765, -16.4163, 1, List.of(1, 2));

        Municipio municipio = municipioMapper.toEntityCreate(dto);

        assertNotNull(municipio);
        assertEquals("Tacoronte", municipio.getNombre());
        assertEquals(350, municipio.getAltitudMedia());
        assertEquals(BigDecimal.valueOf(28.4765), municipio.getLatitudGeografica());
        assertEquals(BigDecimal.valueOf(-16.4163), municipio.getLongitudGeografica());
    }

    @Test
    void toEntityUpdate_nullDto_returnsNullTest() {
        assertNull(municipioMapper.toEntityUpdate(null));
    }

    @Test
    void toEntityUpdate_validDto_mapsCorrectlyTest() {
        MunicipioEntradaUpdateDto dto = new MunicipioEntradaUpdateDto(
                9, "Puerto de la Cruz", 15, BigDecimal.valueOf(28.411), BigDecimal.valueOf(-16.547), 1, List.of(1, 2));

        Municipio municipio = municipioMapper.toEntityUpdate(dto);

        assertNotNull(municipio);
        assertEquals(9, municipio.getId());
        assertEquals("Puerto de la Cruz", municipio.getNombre());
        assertEquals(15, municipio.getAltitudMedia());
        assertEquals(BigDecimal.valueOf(28.411), municipio.getLatitudGeografica());
        assertEquals(BigDecimal.valueOf(-16.547), municipio.getLongitudGeografica());
    }

    @Test
    void toDto_fullMunicipio_mapsCorrectly() {
        Zona zona = new Zona();
        zona.setId(1);
        zona.setNombre("Zona Norte");

        Ruta ruta = new Ruta();
        ruta.setId(10);
        ruta.setNombre("Ruta X");
        ruta.setDificultad("Fácil");
        ruta.setTiempoDuracion(3600L);
        ruta.setDistanciaMetros(5000.0f);
        ruta.setDesnivel(300.0f);
        ruta.setAprobada(true);

        Municipio municipio = new Municipio();
        municipio.setId(100);
        municipio.setNombre("Tegueste");
        municipio.setAltitudMedia(600);
        municipio.setLatitudGeografica(new BigDecimal("28.516667"));
        municipio.setLongitudGeografica(new BigDecimal("-16.333333"));
        municipio.setZona(zona);
        municipio.setRutas(List.of(ruta));

        ZonaSalidaMunicipioDto zonaDto = new ZonaSalidaMunicipioDto(1, "Zona Norte");
        RutaSalidaMunicipioDto rutaDto = new RutaSalidaMunicipioDto(
                10, "Ruta X", "Fácil", 3600L, 5000.0f, 300.0f, true
        );

        when(relacionesMapper.toSalidaMunicipioDto(zona)).thenReturn(zonaDto);
        doReturn(List.of(rutaDto)).when(municipioMapper).rutaListToRutaSalidaMunicipioDtoList(List.of(ruta));

        // WHEN
        MunicipioSalidaDto result = municipioMapper.toDto(municipio);

        // THEN
        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(100);
        assertThat(result.nombre()).isEqualTo("Tegueste");
        assertThat(result.altitudMedia()).isEqualTo(600);
        assertThat(result.latitudGeografica()).isEqualTo(28.516667);
        assertThat(result.longitudGeografica()).isEqualTo(-16.333333);
        assertThat(result.zona()).isEqualTo(zonaDto);
        assertThat(result.rutas()).containsExactly(rutaDto);

        verify(relacionesMapper).toSalidaMunicipioDto(zona);
        verify(municipioMapper).rutaListToRutaSalidaMunicipioDtoList(List.of(ruta));
    }

    @Test
    void toDto_nullMunicipio_returnsNull() {
        assertThat(municipioMapper.toDto(null)).isNull();
    }

    @Test
    void rutaListToRutaSalidaMunicipioDtoList_nullInput_returnsNull() {
        List<RutaSalidaMunicipioDto> result = municipioMapper.rutaListToRutaSalidaMunicipioDtoList(null);
        assertThat(result).isNull();
    }

    @Test
    void rutaListToRutaSalidaMunicipioDtoList_validList_mapsCorrectly() {
        Ruta ruta1 = new Ruta();
        ruta1.setId(1);
        ruta1.setNombre("Ruta 1");

        Ruta ruta2 = new Ruta();
        ruta2.setId(2);
        ruta2.setNombre("Ruta 2");

        RutaSalidaMunicipioDto dto1 = new RutaSalidaMunicipioDto(1, "Ruta 1", "Fácil", 3600L, 5000.0f, 300.0f, true);
        RutaSalidaMunicipioDto dto2 = new RutaSalidaMunicipioDto(2, "Ruta 2", "Media", 1800L, 3200.0f, 120.0f, false);

        when(relacionesMapper.toSalidaMunicipioDto(ruta1)).thenReturn(dto1);
        when(relacionesMapper.toSalidaMunicipioDto(ruta2)).thenReturn(dto2);

        List<Ruta> rutas = List.of(ruta1, ruta2);

        List<RutaSalidaMunicipioDto> result = municipioMapper.rutaListToRutaSalidaMunicipioDtoList(rutas);

        assertThat(result).hasSize(2);
        assertThat(result).containsExactly(dto1, dto2);

        verify(relacionesMapper).toSalidaMunicipioDto(ruta1);
        verify(relacionesMapper).toSalidaMunicipioDto(ruta2);
    }


}
