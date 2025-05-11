package es.iespuertodelacruz.mp.canarytrails.service;

import es.iespuertodelacruz.mp.canarytrails.entities.Coordenada;
import es.iespuertodelacruz.mp.canarytrails.repository.CoordenadaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CoordenadaServiceTest {

    /*@Mock
    private CoordenadaRepository coordenadaRepository;

    @InjectMocks
    private CoordenadaService coordenadaService;

    private Coordenada coordenada;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        coordenada = new Coordenada();
        coordenada.setId(1);
        coordenada.setLatitud(new BigDecimal("28.123456"));
        coordenada.setLongitud(new BigDecimal("-16.654321"));
    }

    @Test
    void testFindAll() {
        when(coordenadaRepository.findAll()).thenReturn(Arrays.asList(coordenada));
        List<Coordenada> result = coordenadaService.findAll();
        assertEquals(1, result.size());
    }

    @Test
    void testFindById() {
        when(coordenadaRepository.findById(1)).thenReturn(Optional.of(coordenada));
        Optional<Coordenada> result = coordenadaService.findById(1);
        assertTrue(result.isPresent());
    }

    @Test
    void testSave() {
        when(coordenadaRepository.save(coordenada)).thenReturn(coordenada);
        Coordenada saved = coordenadaService.save(coordenada);
        assertEquals(coordenada.getLatitud(), saved.getLatitud());
    }

    @Test
    void testDeleteById() {
        doNothing().when(coordenadaRepository).deleteById(1);
        coordenadaService.deleteById(1);
        verify(coordenadaRepository, times(1)).deleteById(1);
    }*/
}
