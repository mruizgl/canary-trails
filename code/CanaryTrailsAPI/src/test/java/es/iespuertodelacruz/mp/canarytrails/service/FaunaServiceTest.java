package es.iespuertodelacruz.mp.canarytrails.service;

import es.iespuertodelacruz.mp.canarytrails.entities.Fauna;
import es.iespuertodelacruz.mp.canarytrails.entities.Ruta;
import es.iespuertodelacruz.mp.canarytrails.entities.Usuario;
import es.iespuertodelacruz.mp.canarytrails.repository.FaunaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FaunaServiceTest {

    @Mock
    private FaunaRepository faunaRepository;

    @InjectMocks
    private FaunaService faunaService;

    private Fauna fauna;
    private Usuario usuario;
    private Ruta ruta;

    @BeforeEach
    public void beforeEach() {
        MockitoAnnotations.openMocks(this);

        usuario = new Usuario();
        usuario.setId(1);
        usuario.setNombre("Admin");

        ruta = new Ruta();
        ruta.setId(1);
        ruta.setNombre("Ruta del Teide");

        fauna = new Fauna();
        fauna.setId(1);
        fauna.setNombre("Lagarto Gigante");
        fauna.setDescripcion("Reptil endémico");
        fauna.setUsuario(usuario);
        fauna.setRutas(List.of(ruta));
    }

    @Test
    public void findAllTest() {
        when(faunaRepository.findAll()).thenReturn(List.of(fauna));
        List<Fauna> result = faunaService.findAll();
        assertEquals(1, result.size());
        assertEquals("Lagarto Gigante", result.get(0).getNombre());
    }

    @Test
    public void findByIdFoundTest() {
        when(faunaRepository.findById(1)).thenReturn(Optional.of(fauna));
        Fauna result = faunaService.findById(1);
        assertNotNull(result);
        assertEquals("Lagarto Gigante", result.getNombre());
    }

    @Test
    public void findByIdNotFoundTest() {
        when(faunaRepository.findById(2)).thenReturn(Optional.empty());
        Fauna result = faunaService.findById(2);
        assertNull(result);
    }

    @Test
    public void saveSuccessTest() {
        when(faunaRepository.save(fauna)).thenReturn(fauna);
        Fauna result = faunaService.save(fauna);
        verify(faunaRepository).addRutaFaunaRelation(fauna.getId(), ruta.getId());
        assertEquals("Lagarto Gigante", result.getNombre());
    }

    @Test
    public void saveMissingNombreTest() {
        fauna.setNombre(null);
        Exception ex = assertThrows(RuntimeException.class, () -> faunaService.save(fauna));
        assertEquals("El fauna ha de tener nombre", ex.getMessage());
    }

    @Test
    public void saveMissingDescripcionTest() {
        fauna.setDescripcion(null);
        Exception ex = assertThrows(RuntimeException.class, () -> faunaService.save(fauna));
        assertEquals("La descripción ha de estar completa para crearla", ex.getMessage());
    }

    @Test
    public void saveMissingUsuarioTest() {
        fauna.setUsuario(null);
        Exception ex = assertThrows(RuntimeException.class, () -> faunaService.save(fauna));
        assertEquals("El usuario ha de existir", ex.getMessage());
    }

    @Test
    public void updateSuccessTest() {
        Fauna original = new Fauna();
        original.setId(1);
        when(faunaRepository.findById(1)).thenReturn(Optional.of(original));
        when(faunaRepository.save(any())).thenReturn(fauna);

        boolean result = faunaService.update(fauna);

        assertTrue(result);
        verify(faunaRepository).deleteRutaFaunaRelation(fauna.getId());
        verify(faunaRepository).addRutaFaunaRelation(fauna.getId(), ruta.getId());
    }

    @Test
    public void updateNotFoundTest() {
        when(faunaRepository.findById(1)).thenReturn(Optional.empty());
        Exception ex = assertThrows(RuntimeException.class, () -> faunaService.update(fauna));
        assertTrue(ex.getMessage().contains("No existe la fauna"));
    }

    @Test
    public void deleteByIdSuccessTest() {
        when(faunaRepository.deleteFaunaById(1)).thenReturn(1);
        boolean result = faunaService.deleteById(1);
        verify(faunaRepository).deleteRutaFaunaRelation(1);
        assertTrue(result);
    }

    @Test
    public void deleteByIdFailTest() {
        when(faunaRepository.deleteFaunaById(1)).thenReturn(0);
        boolean result = faunaService.deleteById(1);
        assertFalse(result);
    }
}
