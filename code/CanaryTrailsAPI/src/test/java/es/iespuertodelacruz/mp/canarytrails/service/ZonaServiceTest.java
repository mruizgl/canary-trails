package es.iespuertodelacruz.mp.canarytrails.service;

import es.iespuertodelacruz.mp.canarytrails.entities.Zona;
import es.iespuertodelacruz.mp.canarytrails.repository.ZonaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ZonaServiceTest {

    @InjectMocks
    private ZonaService zonaService;

    @Mock
    private ZonaRepository zonaRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        Zona z1 = new Zona(); z1.setId(1); z1.setNombre("Norte");
        Zona z2 = new Zona(); z2.setId(2); z2.setNombre("Sur");

        when(zonaRepository.findAll()).thenReturn(Arrays.asList(z1, z2));

        List<Zona> zonas = zonaService.findAll();

        assertEquals(2, zonas.size());
        assertEquals("Norte", zonas.get(0).getNombre());
    }

    @Test
    void testFindByIdExists() {
        Zona zona = new Zona();
        zona.setId(1);
        zona.setNombre("Este");

        when(zonaRepository.findById(1)).thenReturn(Optional.of(zona));

        Optional<Zona> result = zonaService.findById(1);
        assertTrue(result.isPresent());
        assertEquals("Este", result.get().getNombre());
    }

    @Test
    void testFindByIdNotExists() {
        when(zonaRepository.findById(999)).thenReturn(Optional.empty());

        Optional<Zona> result = zonaService.findById(999);
        assertFalse(result.isPresent());
    }

    @Test
    void testSave() {
        Zona zona = new Zona();
        zona.setNombre("Centro");

        when(zonaRepository.save(zona)).thenReturn(zona);

        Zona result = zonaService.save(zona);
        assertEquals("Centro", result.getNombre());
    }

    @Test
    void testDeleteById() {
        doNothing().when(zonaRepository).deleteById(1);
        zonaService.deleteById(1);
        verify(zonaRepository, times(1)).deleteById(1);
    }
}
