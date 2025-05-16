package es.iespuertodelacruz.mp.canarytrails.service;

import es.iespuertodelacruz.mp.canarytrails.entities.Fauna;
import es.iespuertodelacruz.mp.canarytrails.entities.Flora;
import es.iespuertodelacruz.mp.canarytrails.entities.Ruta;
import es.iespuertodelacruz.mp.canarytrails.entities.Usuario;
import es.iespuertodelacruz.mp.canarytrails.repository.FloraRepository;
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

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FloraServiceTest {

    @InjectMocks
    private FloraService floraService;

    @Mock
    private FloraRepository floraRepository;

    private Flora floraValida;

    private Usuario usuario;

    private Ruta ruta;

    private static String MESSAGE_ERROR = "No se ha obtenido el error esperado";

    @BeforeEach
    public void beforeEach() {
        usuario = new Usuario();
        usuario.setId(1);
        usuario.setNombre("Test");

        ruta = new Ruta();
        ruta.setId(1);
        ruta.setNombre("Ruta");

        floraValida = new Flora();
        floraValida.setId(1);
        floraValida.setNombre("Flora");
        floraValida.setDescripcion("Descripción");
        floraValida.setEspecie("Especie");
        floraValida.setTipoHoja("TipoHoja");
        floraValida.setSalidaFlor("Primavera");
        floraValida.setCaidaFlor("Invierno");
        floraValida.setUsuario(usuario);
        floraValida.setRutas(new ArrayList<>(List.of(ruta)));
    }

    @Test
    public void findAllFlorasTest() {
        when(floraRepository.findAll()).thenReturn(List.of(floraValida));

        List<Flora> result = floraService.findAll();

        Assertions.assertEquals(1, result.size(), MESSAGE_ERROR);
        Assertions.assertEquals("Flora", result.get(0).getNombre(), MESSAGE_ERROR);
    }

    @Test
    public void updateFloraConAprobadaYFotoTest() {
        Flora existente = new Flora();
        existente.setId(1);

        floraValida.setAprobada(true);
        floraValida.setFoto("foto.jpg");

        when(floraRepository.findById(1)).thenReturn(Optional.of(existente));
        when(floraRepository.save(any())).thenReturn(floraValida);
        when(floraRepository.deleteRutaFloraRelation(1)).thenReturn(1);

        boolean actualizado = floraService.update(floraValida);

        Assertions.assertTrue(actualizado, MESSAGE_ERROR);
        verify(floraRepository).addRutaFloraRelation(1, ruta.getId());
    }


    @Test
    public void saveFloraTest() {
        when(floraRepository.save(floraValida)).thenReturn(floraValida);

        Flora result = floraService.save(floraValida);

        Assertions.assertEquals(floraValida, result, MESSAGE_ERROR);
        verify(floraRepository).addRutaFloraRelation(floraValida.getId(), ruta.getId());
    }

    @Test
    public void saveFloraSinNombreTest_LanzaExcepcion() {
        floraValida.setNombre(null);
        assertThrows(RuntimeException.class, () -> floraService.save(floraValida));
    }

    @Test
    public void updateFloraExistenteTest() {
        Flora existente = new Flora();
        existente.setId(1);
        existente.setNombre("Viejo nombre");

        when(floraRepository.findById(1)).thenReturn(Optional.of(existente));
        when(floraRepository.save(any())).thenReturn(floraValida);
        when(floraRepository.deleteRutaFloraRelation(1)).thenReturn(1);

        boolean actualizado = floraService.update(floraValida);

        Assertions.assertTrue(actualizado, "No se actualizó la flora");
        verify(floraRepository).addRutaFloraRelation(1, ruta.getId());
    }

    @Test
    public void updateFloraInexistenteTest_LanzaExcepcion() {
        when(floraRepository.findById(999)).thenReturn(Optional.empty());
        Flora inexistente = new Flora();
        inexistente.setId(999);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> floraService.update(inexistente));
        Assertions.assertTrue(ex.getMessage().contains("No existe la flora"), MESSAGE_ERROR);
    }

    @Test
    public void deleteFloraPorIdTest_True() {
        when(floraRepository.deleteFloraById(1)).thenReturn(1);
        Assertions.assertTrue(floraService.deleteById(1), MESSAGE_ERROR);
    }

    @Test
    public void deleteFloraPorIdTest_False() {
        when(floraRepository.deleteFloraById(2)).thenReturn(0);
        Assertions.assertFalse(floraService.deleteById(2), MESSAGE_ERROR);
    }

    @Test
    public void findByIdTest() {
        when(floraRepository.findById(1)).thenReturn(Optional.of(floraValida));

        Flora result = floraService.findById(1);

        Assertions.assertNotNull(result, MESSAGE_ERROR);
        Assertions.assertEquals("Flora", result.getNombre(), MESSAGE_ERROR);
    }

    @Test
    public void saveFloraSinDescripcion_LanzaExcepcion() {
        floraValida.setDescripcion(null);
        assertThrows(RuntimeException.class, () -> floraService.save(floraValida));
    }

    @Test
    public void saveFloraSinEspecie_LanzaExcepcion() {
        floraValida.setEspecie(null);
        assertThrows(RuntimeException.class, () -> floraService.save(floraValida));
    }

    @Test
    public void saveFloraSinTipoHoja_LanzaExcepcion() {
        floraValida.setTipoHoja(null);
        assertThrows(RuntimeException.class, () -> floraService.save(floraValida));
    }

    @Test
    public void saveFloraSinSalidaFlor_LanzaExcepcion() {
        floraValida.setSalidaFlor(null);
        assertThrows(RuntimeException.class, () -> floraService.save(floraValida));
    }

    @Test
    public void saveFloraSinCaidaFlor_LanzaExcepcion() {
        floraValida.setCaidaFlor(null);
        assertThrows(RuntimeException.class, () -> floraService.save(floraValida));
    }

    @Test
    public void saveFloraSinUsuario_LanzaExcepcion() {
        floraValida.setUsuario(null);
        assertThrows(RuntimeException.class, () -> floraService.save(floraValida));
    }

    @Test
    public void updateFloraSinId_ReturnsFalse() {
        Flora sinId = new Flora(); // id es null
        boolean resultado = floraService.update(sinId);
        Assertions.assertFalse(resultado);
    }

    @Test
    public void updateFloraNulo_ReturnsFalse() {
        boolean resultado = floraService.update(null);
        Assertions.assertFalse(resultado, MESSAGE_ERROR);
    }

}

