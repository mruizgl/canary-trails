package es.iespuertodelacruz.mp.canarytrails.service;

import es.iespuertodelacruz.mp.canarytrails.entities.Municipio;
import es.iespuertodelacruz.mp.canarytrails.entities.Zona;
import es.iespuertodelacruz.mp.canarytrails.repository.MunicipioRepository;
import es.iespuertodelacruz.mp.canarytrails.repository.ZonaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MunicipioServiceTest {

    /*private MunicipioRepository municipioRepository;
    private ZonaRepository zonaRepository;
    private MunicipioService municipioService;

    @BeforeEach
    void setUp() {
        municipioRepository = mock(MunicipioRepository.class);
        zonaRepository = mock(ZonaRepository.class);
        municipioService = new MunicipioService();
        municipioService.municipioRepository = municipioRepository;
        municipioService.zonaRepository = zonaRepository;
    }

    @Test
    void testSaveThrowsExceptionWhenMissingZona() {
        Municipio municipio = new Municipio();
        municipio.setNombre("Tegueste");
        municipio.setAltitudMedia(390);
        municipio.setLatitudGeografica(BigDecimal.valueOf(28.3891));
        municipio.setLongitudGeografica(BigDecimal.valueOf(-16.5237));
        // No zona

        RuntimeException exception = assertThrows(RuntimeException.class, () -> municipioService.save(municipio));
        assertEquals("El municipio debe tener una zona válida", exception.getMessage());
    }

    @Test
    void testSaveThrowsExceptionWhenZonaNotFound() {
        Municipio municipio = new Municipio();
        municipio.setNombre("Tacoronte");
        municipio.setAltitudMedia(400);
        municipio.setLatitudGeografica(BigDecimal.valueOf(28.4771));
        municipio.setLongitudGeografica(BigDecimal.valueOf(-16.4058));
        Zona zona = new Zona();
        zona.setId(99);
        municipio.setZona(zona);

        when(zonaRepository.findById(99)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> municipioService.save(municipio));
        assertEquals("Zona no encontrada", exception.getMessage());
    }

    @Test
    void testSaveSuccess() {
        Municipio municipio = new Municipio();
        municipio.setNombre("Tacoronte");
        municipio.setAltitudMedia(400);
        municipio.setLatitudGeografica(BigDecimal.valueOf(28.4771));
        municipio.setLongitudGeografica(BigDecimal.valueOf(-16.4058));
        Zona zona = new Zona();
        zona.setId(1);
        municipio.setZona(zona);

        when(zonaRepository.findById(1)).thenReturn(Optional.of(zona));
        when(municipioRepository.save(any())).thenReturn(municipio);

        Municipio saved = municipioService.save(municipio);
        assertNotNull(saved);
        verify(municipioRepository, times(1)).save(municipio);
    }*/
}
