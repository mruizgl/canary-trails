package es.iespuertodelacruz.mp.canarytrails.service;

import es.iespuertodelacruz.mp.canarytrails.entities.Fauna;
import es.iespuertodelacruz.mp.canarytrails.entities.Flora;
import es.iespuertodelacruz.mp.canarytrails.entities.Ruta;
import es.iespuertodelacruz.mp.canarytrails.entities.Usuario;
import es.iespuertodelacruz.mp.canarytrails.repository.FaunaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FaunaServiceTest {

    @InjectMocks
    private FaunaService faunaService;

    @Mock
    private FaunaRepository faunaRepository;

    private Fauna faunaValida;
    private Usuario usuario;
    private Ruta ruta;

    private static String MESSAGE_ERROR = "No se ha obtenido el error esperado";

    @BeforeEach
    public void beforeEach() {
        usuario = new Usuario();
        usuario.setId(1);
        usuario.setNombre("TestUser");

        ruta = new Ruta();
        ruta.setId(1);
        ruta.setNombre("Ruta test");

        faunaValida = new Fauna();
        faunaValida.setId(1);
        faunaValida.setNombre("Lagarto");
        faunaValida.setDescripcion("Reptil endémico");
        faunaValida.setUsuario(usuario);
        faunaValida.setRutas(new ArrayList<>(List.of(ruta)));
    }

    @Test
    public void testFindAllFauna() {
        List<Fauna> lista = List.of(faunaValida);
        when(faunaRepository.findAll()).thenReturn(lista);

        List<Fauna> result = faunaService.findAll();

        Assertions.assertEquals(1, result.size(), MESSAGE_ERROR);
        Assertions.assertEquals("Lagarto", result.get(0).getNombre(), MESSAGE_ERROR);
    }

    @Test
    public void findByIdTest() {
        when(faunaRepository.findById(1)).thenReturn(Optional.of(faunaValida));

        Fauna result = faunaService.findById(1);

        Assertions.assertNotNull(result, MESSAGE_ERROR);
        Assertions.assertEquals("Lagarto", result.getNombre(), MESSAGE_ERROR);
    }

    @Test
    public void testUpdateFaunaConAprobadaYFoto() {
        Fauna faunaEnBd = new Fauna();
        faunaEnBd.setId(1);

        faunaValida.setAprobada(true);
        faunaValida.setFoto("foto.jpg");

        when(faunaRepository.findById(1)).thenReturn(Optional.of(faunaEnBd));
        when(faunaRepository.save(any())).thenReturn(faunaValida);
        when(faunaRepository.deleteRutaFaunaRelation(1)).thenReturn(1);

        boolean actualizado = faunaService.update(faunaValida);

        assertTrue(actualizado);
        verify(faunaRepository).addRutaFaunaRelation(1, ruta.getId());
    }



    @Test
    public void saveFaunaValidaTest() {
        when(faunaRepository.save(faunaValida)).thenReturn(faunaValida);

        Fauna result = faunaService.save(faunaValida);

        Assertions.assertEquals(faunaValida, result, MESSAGE_ERROR);
        verify(faunaRepository).addRutaFaunaRelation(faunaValida.getId(), ruta.getId());
    }

    @Test
    public void saveFaunaSinNombreTest() {
        faunaValida.setNombre(null);
        Exception ex = assertThrows(RuntimeException.class, () -> faunaService.save(faunaValida));
        Assertions.assertTrue(ex.getMessage().contains("nombre"), MESSAGE_ERROR);
    }

    @Test
    public void saveFaunaSinDescripcionTest() {
        faunaValida.setDescripcion(null);
        Exception ex = assertThrows(RuntimeException.class, () -> faunaService.save(faunaValida));
        Assertions.assertTrue(ex.getMessage().contains("descripción"), MESSAGE_ERROR);
    }

    @Test
    public void saveFaunaSinUsuarioTest() {
        faunaValida.setUsuario(null);
        Exception ex = assertThrows(RuntimeException.class, () -> faunaService.save(faunaValida));
        assertTrue(ex.getMessage().contains("usuario"));
    }

    @Test
    public void updateFaunaExistenteTest() {
        Fauna antigua = new Fauna();
        antigua.setId(1);

        when(faunaRepository.findById(1)).thenReturn(Optional.of(antigua));
        when(faunaRepository.save(any())).thenReturn(faunaValida);
        when(faunaRepository.deleteRutaFaunaRelation(1)).thenReturn(1);

        boolean actualizado = faunaService.update(faunaValida);

        Assertions.assertTrue(actualizado, MESSAGE_ERROR);
        verify(faunaRepository).addRutaFaunaRelation(1, ruta.getId());
    }

    @Test
    public void updateFaunaInexistenteTest() {
        when(faunaRepository.findById(99)).thenReturn(Optional.empty());
        Fauna inexistente = new Fauna();
        inexistente.setId(99);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> faunaService.update(inexistente));
        Assertions.assertTrue(ex.getMessage().contains("No existe la fauna"), MESSAGE_ERROR);
    }

    @Test
    public void updateFaunaSinIdTest() {
        Fauna sinId = new Fauna();
        sinId.setNombre("Lagarto");

        boolean result = faunaService.update(sinId);
        assertFalse(result);
    }

    @Test
    public void deleteFaunaTest_Correcto() {
        when(faunaRepository.deleteRutaFaunaRelation(1)).thenReturn(1);
        when(faunaRepository.deleteFaunaById(1)).thenReturn(1);

        boolean result = faunaService.deleteById(1);

        assertTrue(result);
        verify(faunaRepository).deleteRutaFaunaRelation(1);
        verify(faunaRepository).deleteFaunaById(1);
    }

    @Test
    public void deleteFaunaTest_NoEncontrado() {
        when(faunaRepository.deleteRutaFaunaRelation(2)).thenReturn(0);
        when(faunaRepository.deleteFaunaById(2)).thenReturn(0);

        boolean result = faunaService.deleteById(2);

        Assertions.assertFalse(result, MESSAGE_ERROR);
    }
}

