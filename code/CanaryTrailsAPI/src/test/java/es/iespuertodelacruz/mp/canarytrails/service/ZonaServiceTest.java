package es.iespuertodelacruz.mp.canarytrails.service;

import es.iespuertodelacruz.mp.canarytrails.entities.Municipio;
import es.iespuertodelacruz.mp.canarytrails.entities.Zona;
import es.iespuertodelacruz.mp.canarytrails.repository.ZonaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ZonaServiceTest {

    @InjectMocks
    private ZonaService zonaService;

    @Mock
    private ZonaRepository zonaRepository;

    private Zona zona;

    @BeforeEach
    public void setUp() {
        zona = new Zona();
        zona.setId(1);
        zona.setNombre("Norte");
    }

    @Test
    public void findAllTest() {
        when(zonaRepository.findAll()).thenReturn(List.of(zona));

        List<Zona> zonas = zonaService.findAll();

        assertNotNull(zonas);
        assertEquals(1, zonas.size());
        assertEquals("Norte", zonas.get(0).getNombre());
    }

    @Test
    public void findByIdTest() {
        when(zonaRepository.findById(1)).thenReturn(Optional.of(zona));

        Zona result = zonaService.findById(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
    }

    @Test
    public void saveZonaValidaTest() {
        when(zonaRepository.save(zona)).thenReturn(zona);

        Zona result = zonaService.save(zona);

        assertNotNull(result);
        assertEquals("Norte", result.getNombre());
    }

    @Test
    public void saveZonaSinNombre_LanzaExcepcion() {
        zona.setNombre(null);
        assertThrows(RuntimeException.class, () -> zonaService.save(zona));
    }

    @Test
    public void updateZonaExistenteTest() {
        when(zonaRepository.findById(1)).thenReturn(Optional.of(zona));
        when(zonaRepository.save(any())).thenReturn(zona);

        boolean actualizado = zonaService.update(zona);

        assertTrue(actualizado);
        verify(zonaRepository).save(any());
    }

    @Test
    public void updateZonaInexistente_LanzaExcepcion() {
        Zona inexistente = new Zona();
        inexistente.setId(999);
        when(zonaRepository.findById(999)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> zonaService.update(inexistente));
        assertTrue(ex.getMessage().contains("No existe"));
    }

    @Test
    public void updateZonaSinId_ReturnsFalse() {
        Zona sinId = new Zona();
        boolean resultado = zonaService.update(sinId);
        assertFalse(resultado);
    }

    @Test
    public void updateZonaNula_ReturnsFalse() {
        boolean resultado = zonaService.update(null);
        assertFalse(resultado);
    }

    @Test
    public void deleteZonaSinMunicipiosTest() {
        zona.setMunicipios(List.of());
        when(zonaRepository.findById(1)).thenReturn(Optional.of(zona));
        when(zonaRepository.deleteZonaById(1)).thenReturn(1);

        boolean eliminado = zonaService.deleteById(1);

        assertTrue(eliminado);
    }

    @Test
    public void deleteZonaConMunicipios_LanzaExcepcion() {
        Municipio municipio = new Municipio();
        municipio.setId(1);
        zona.setMunicipios(List.of(municipio));
        when(zonaRepository.findById(1)).thenReturn(Optional.of(zona));

        RuntimeException ex = assertThrows(RuntimeException.class, () -> zonaService.deleteById(1));
        assertTrue(ex.getMessage().contains("no puede contener municipios"));
    }

    @Test
    public void deleteZonaInexistenteTest() {
        when(zonaRepository.findById(2)).thenReturn(Optional.empty());

        Zona zonaNula = zonaService.findById(2);
        assertNull(zonaNula);
    }
}
