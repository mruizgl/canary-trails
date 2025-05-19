package es.iespuertodelacruz.mp.canarytrails.service;

import es.iespuertodelacruz.mp.canarytrails.entities.Comentario;
import es.iespuertodelacruz.mp.canarytrails.entities.Ruta;
import es.iespuertodelacruz.mp.canarytrails.entities.Usuario;
import es.iespuertodelacruz.mp.canarytrails.mapper.ComentarioMapper;
import es.iespuertodelacruz.mp.canarytrails.repository.ComentarioRepository;
import es.iespuertodelacruz.mp.canarytrails.repository.RutaRepository;
import es.iespuertodelacruz.mp.canarytrails.repository.UsuarioRepository;
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
public class ComentarioServiceTest {

    @InjectMocks
    private ComentarioService comentarioService;

    @Mock
    private ComentarioRepository comentarioRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private RutaRepository rutaRepository;

    @Mock
    private ComentarioMapper comentarioMapper;

    private Comentario comentario;
    private Usuario usuario;
    private Ruta ruta;

    @BeforeEach
    public void setUp() {
        usuario = new Usuario();
        usuario.setId(1);
        usuario.setNombre("Usuario");

        ruta = new Ruta();
        ruta.setId(1);
        ruta.setNombre("Ruta");

        comentario = new Comentario();
        comentario.setId(1);
        comentario.setTitulo("Título");
        comentario.setDescripcion("Descripción del comentario");
        comentario.setUsuario(usuario);
        comentario.setRuta(ruta);
    }

    @Test
    public void findAllTest() {
        when(comentarioRepository.findAll()).thenReturn(List.of(comentario));

        List<Comentario> result = comentarioService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    public void findByIdTest() {
        when(comentarioRepository.findById(1)).thenReturn(Optional.of(comentario));

        Comentario result = comentarioService.findById(1);

        assertNotNull(result);
        assertEquals("Título", result.getTitulo());
    }

    @Test
    public void saveComentarioValidoTest() {
        when(comentarioRepository.save(comentario)).thenReturn(comentario);

        Comentario result = comentarioService.save(comentario);

        assertNotNull(result);
        assertEquals("Título", result.getTitulo());
    }

    @Test
    public void saveComentarioSinTitulo_LanzaExcepcion() {
        comentario.setTitulo(null);
        assertThrows(RuntimeException.class, () -> comentarioService.save(comentario));
    }

    @Test
    public void saveComentarioSinDescripcion_LanzaExcepcion() {
        comentario.setDescripcion(null);
        assertThrows(RuntimeException.class, () -> comentarioService.save(comentario));
    }

    @Test
    public void saveComentarioSinUsuario_LanzaExcepcion() {
        comentario.setUsuario(null);
        assertThrows(RuntimeException.class, () -> comentarioService.save(comentario));
    }

    @Test
    public void saveComentarioSinRuta_LanzaExcepcion() {
        comentario.setRuta(null);
        assertThrows(RuntimeException.class, () -> comentarioService.save(comentario));
    }

    @Test
    public void updateComentarioExistenteTest() {
        when(comentarioRepository.findById(1)).thenReturn(Optional.of(comentario));
        when(comentarioRepository.save(any())).thenReturn(comentario);

        comentario.setDescripcion("Nueva descripción");

        boolean actualizado = comentarioService.update(comentario);

        assertTrue(actualizado);
        verify(comentarioRepository).save(any());
    }

    @Test
    public void updateComentarioInexistente_LanzaExcepcion() {
        Comentario inexistente = new Comentario();
        inexistente.setId(999);
        when(comentarioRepository.findById(999)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> comentarioService.update(inexistente));
        assertTrue(ex.getMessage().contains("No existe el comentario"));
    }

    @Test
    public void updateComentarioSinId_ReturnsFalse() {
        Comentario sinId = new Comentario();
        boolean result = comentarioService.update(sinId);
        assertFalse(result);
    }

    @Test
    public void updateComentarioNulo_ReturnsFalse() {
        boolean result = comentarioService.update(null);
        assertFalse(result);
    }

    @Test
    public void deleteComentarioPorIdTest_True() {
        when(comentarioRepository.deleteComentarioById(1)).thenReturn(1);

        boolean result = comentarioService.deleteById(1);

        assertTrue(result);
    }

    @Test
    public void deleteComentarioPorIdTest_False() {
        when(comentarioRepository.deleteComentarioById(2)).thenReturn(0);

        boolean result = comentarioService.deleteById(2);

        assertFalse(result);
    }
}
