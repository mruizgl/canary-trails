package es.iespuertodelacruz.mp.canarytrails.service;

import es.iespuertodelacruz.mp.canarytrails.entities.Coordenada;
import es.iespuertodelacruz.mp.canarytrails.repository.CoordenadaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CoordenadaServiceTest {

    @InjectMocks
    private CoordenadaService coordenadaService;

    @Mock
    private CoordenadaRepository coordenadaRepository;

    private Coordenada coordenada;

    @BeforeEach
    public void setUp() {
        coordenada = new Coordenada();
        coordenada.setId(1);
        coordenada.setLatitud(BigDecimal.valueOf(28.123456));
        coordenada.setLongitud(BigDecimal.valueOf(-16.123456));
    }

    @Test
    public void findAllTest() {
        when(coordenadaRepository.findAll()).thenReturn(List.of(coordenada));

        List<Coordenada> result = coordenadaService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(coordenada.getLatitud(), result.get(0).getLatitud());
    }

    @Test
    public void findByIdTest() {
        when(coordenadaRepository.findById(1)).thenReturn(Optional.of(coordenada));

        Coordenada result = coordenadaService.findById(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
    }

    @Test
    public void saveCoordenadaValidaTest() {
        when(coordenadaRepository.save(coordenada)).thenReturn(coordenada);

        Coordenada result = coordenadaService.save(coordenada);

        assertNotNull(result);
        assertEquals(coordenada.getLatitud(), result.getLatitud());
        assertEquals(coordenada.getLongitud(), result.getLongitud());
    }

    @Test
    public void saveCoordenadaSinLatitud_LanzaExcepcion() {
        coordenada.setLatitud(null);
        assertThrows(RuntimeException.class, () -> coordenadaService.save(coordenada));
    }

    @Test
    public void saveCoordenadaSinLongitud_LanzaExcepcion() {
        coordenada.setLongitud(null);
        assertThrows(RuntimeException.class, () -> coordenadaService.save(coordenada));
    }

    @Test
    public void updateCoordenadaExistenteTest() {
        when(coordenadaRepository.findById(1)).thenReturn(Optional.of(coordenada));
        when(coordenadaRepository.save(any())).thenReturn(coordenada);

        boolean actualizado = coordenadaService.update(coordenada);

        assertTrue(actualizado);
        verify(coordenadaRepository).save(any());
    }

    @Test
    public void updateCoordenadaInexistente_LanzaExcepcion() {
        Coordenada inexistente = new Coordenada();
        inexistente.setId(999);
        when(coordenadaRepository.findById(999)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> coordenadaService.update(inexistente));
        assertTrue(ex.getMessage().contains("No existe"));
    }

    @Test
    public void updateCoordenadaSinId_ReturnsFalse() {
        Coordenada sinId = new Coordenada();
        boolean resultado = coordenadaService.update(sinId);
        assertFalse(resultado);
    }

    @Test
    public void updateCoordenadaNulo_ReturnsFalse() {
        boolean resultado = coordenadaService.update(null);
        assertFalse(resultado);
    }

    @Test
    public void deleteCoordenadaPorIdTest_True() {
        when(coordenadaRepository.deleteCoordenadaById(1)).thenReturn(1);

        boolean result = coordenadaService.deleteById(1);

        assertTrue(result);
    }

    @Test
    public void deleteCoordenadaPorIdTest_False() {
        when(coordenadaRepository.deleteCoordenadaById(2)).thenReturn(0);

        boolean result = coordenadaService.deleteById(2);

        assertFalse(result);
    }
}
