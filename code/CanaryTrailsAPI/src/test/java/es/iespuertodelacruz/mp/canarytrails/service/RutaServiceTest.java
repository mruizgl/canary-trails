package es.iespuertodelacruz.mp.canarytrails.service;

import es.iespuertodelacruz.mp.canarytrails.entities.*;
import es.iespuertodelacruz.mp.canarytrails.repository.*;
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
public class RutaServiceTest {

    @InjectMocks
    private RutaService rutaService;

    @Mock
    private RutaRepository rutaRepository;
    @Mock
    private ComentarioRepository comentarioRepository;
    @Mock
    private MunicipioRepository municipioRepository;
    @Mock
    private CoordenadaRepository coordenadaRepository;

    private Ruta rutaValida;
    private Usuario usuario;
    private Municipio municipio;
    private Coordenada coordenada;

    @BeforeEach
    public void beforeEach() {
        usuario = new Usuario();
        usuario.setId(1);

        municipio = new Municipio();
        municipio.setId(1);

        coordenada = new Coordenada();
        coordenada.setId(1);

        rutaValida = new Ruta();
        rutaValida.setId(1);
        rutaValida.setNombre("Ruta Test");
        rutaValida.setDificultad("Media");
        rutaValida.setTiempoDuracion(120L);
        rutaValida.setDistanciaMetros(10.5f);
        rutaValida.setDesnivel(200f);
        rutaValida.setUsuario(usuario);
        rutaValida.setMunicipios(List.of(municipio));
        rutaValida.setCoordenadas(List.of(coordenada, coordenada, coordenada, coordenada, coordenada));
    }


    @Test
    public void saveRutaValidaTest() {
        when(rutaRepository.save(rutaValida)).thenReturn(rutaValida);

        Ruta saved = rutaService.save(rutaValida);

        assertNotNull(saved);
        assertEquals("Ruta Test", saved.getNombre());
    }


    @Test
    public void saveRutaSinNombre_LanzaExcepcion() {
        rutaValida.setNombre(null);
        assertThrows(RuntimeException.class, () -> rutaService.save(rutaValida));
    }

    @Test
    public void updateRutaExistenteTest() {
        when(rutaRepository.findById(1)).thenReturn(Optional.of(rutaValida));
        when(rutaRepository.save(any())).thenReturn(rutaValida);

        boolean actualizado = rutaService.update(rutaValida);

        assertTrue(actualizado);
        verify(rutaRepository).deleteRutaFaunaRelation(1);
        verify(rutaRepository).deleteRutaFloraRelation(1);
        verify(rutaRepository).deleteRutaCoordenadaRelation(1);
        verify(rutaRepository).deleteRutaMunicipioRelation(1);
    }

    @Test
    public void updateRutaInexistente_LanzaExcepcion() {
        Ruta ruta = new Ruta();
        ruta.setId(999);
        when(rutaRepository.findById(999)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> rutaService.update(ruta));
        assertTrue(ex.getMessage().contains("No existe la ruta"));
    }

    @Test
    public void deleteRutaPorIdTest_True() {
        when(rutaRepository.deleteRutaById(1)).thenReturn(1);

        boolean result = rutaService.deleteById(1);

        assertTrue(result);
        verify(comentarioRepository).deleteComentarioByRutaId(1);
    }

    @Test
    public void deleteRutaPorIdTest_False() {
        when(rutaRepository.deleteRutaById(2)).thenReturn(0);

        boolean result = rutaService.deleteById(2);

        assertFalse(result);
    }

    @Test
    public void findRutasFavoritasByUserIdTest() {
        when(rutaRepository.findFavoritasByUserId(1)).thenReturn(List.of(1));
        when(rutaRepository.findAllById(List.of(1))).thenReturn(List.of(rutaValida));

        List<Ruta> favoritas = rutaService.findRutasFavoritasByUserId(1);

        assertEquals(1, favoritas.size());
        assertEquals("Ruta Test", favoritas.get(0).getNombre());
    }

    @Test
    public void aniadirRutaFavoritaTest() {
        when(rutaRepository.addFavoritaById(1, 1)).thenReturn(1);

        assertTrue(rutaService.aniadirRutaFavorita(1, 1));
    }

    @Test
    public void eliminarRutaFavoritaTest() {
        when(rutaRepository.deleteFavoritaById(1, 1)).thenReturn(1);

        assertTrue(rutaService.deleteRutaFavorita(1, 1));
    }
}
