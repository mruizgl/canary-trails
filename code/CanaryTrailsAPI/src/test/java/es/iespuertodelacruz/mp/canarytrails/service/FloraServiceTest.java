package es.iespuertodelacruz.mp.canarytrails.service;

import es.iespuertodelacruz.mp.canarytrails.entities.Flora;
import es.iespuertodelacruz.mp.canarytrails.repository.FloraRepository;
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

class FloraServiceTest {

    /*@Mock
    private FloraRepository floraRepository;

    @InjectMocks
    private FloraService floraService;

    private Flora flora;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        flora = new Flora();
        flora.setId(1);
        flora.setNombre("Cardón");
        flora.setEspecie("Euphorbia canariensis");
    }

    @Test
    void testFindAll() {
        when(floraRepository.findAll()).thenReturn(Arrays.asList(flora));
        List<Flora> result = floraService.findAll();
        assertEquals(1, result.size());
        assertEquals("Cardón", result.get(0).getNombre());
    }

    @Test
    void testFindById() {
        when(floraRepository.findById(1)).thenReturn(Optional.of(flora));
        Optional<Flora> result = floraService.findById(1);
        assertTrue(result.isPresent());
        assertEquals("Cardón", result.get().getNombre());
    }

    @Test
    void testSave() {
        when(floraRepository.save(flora)).thenReturn(flora);
        Flora saved = floraService.save(flora);
        assertNotNull(saved);
        assertEquals("Cardón", saved.getNombre());
    }

    @Test
    void testDeleteById() {
        doNothing().when(floraRepository).deleteById(1);
        floraService.deleteById(1);
        verify(floraRepository, times(1)).deleteById(1);
    }*/
}
