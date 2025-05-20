package es.iespuertodelacruz.mp.canarytrails.service;

import es.iespuertodelacruz.mp.canarytrails.entities.Fauna;
import es.iespuertodelacruz.mp.canarytrails.entities.Usuario;
import es.iespuertodelacruz.mp.canarytrails.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @InjectMocks
    private UsuarioService usuarioService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private Usuario usuario;

    @BeforeEach
    public void setUp() {
        usuario = new Usuario();
        usuario.setId(1);
        usuario.setNombre("TestUser");
        usuario.setCorreo("test@correo.com");
        usuario.setPassword("TestPass123!");
    }

    @Test
    public void findAllTest() {
        when(usuarioRepository.findAll()).thenReturn(List.of(usuario));

        List<Usuario> result = usuarioService.findAll();

        assertEquals(1, result.size());
        assertEquals("TestUser", result.get(0).getNombre());
    }

    @Test
    public void findByIdTest() {
        when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuario));

        Usuario result = usuarioService.findById(1);

        assertNotNull(result);
        assertEquals("TestUser", result.getNombre());
    }

    @Test
    public void findByUserNameTest() {
        when(usuarioRepository.findByNombre("TestUser")).thenReturn(Optional.of(usuario));

        Usuario result = usuarioService.findByUserName("TestUser");

        assertNotNull(result);
        assertEquals("TestUser", result.getNombre());
    }

    @Test
    public void saveUsuarioValidoTest() {
        when(passwordEncoder.encode(any())).thenReturn("hashedPassword");
        when(usuarioRepository.save(any())).thenReturn(usuario);

        Usuario result = usuarioService.save(usuario);

        assertNotNull(result);
        assertEquals("TestUser", result.getNombre());
        assertNotNull(result.getTokenVerificacion());
        assertNotNull(result.getFechaCreacion());
        assertEquals("src/main/resources/uploads/usuario/default.png", result.getFoto());
        assertEquals("ROLE_USER", result.getRol());
    }

    @Test
    public void saveUsuarioCorreoInvalido_LanzaExcepcion() {
        usuario.setCorreo("correo_invalido");
        assertThrows(RuntimeException.class, () -> usuarioService.save(usuario));
    }

    @Test
    public void saveUsuarioSinNombre_LanzaExcepcion() {
        usuario.setNombre(null);
        assertThrows(RuntimeException.class, () -> usuarioService.save(usuario));
    }

    @Test
    public void saveUsuarioPasswordCorta_LanzaExcepcion() {
        usuario.setPassword("123");
        assertThrows(RuntimeException.class, () -> usuarioService.save(usuario));
    }


    @Test
    public void updateUsuarioExistenteTest() {
        when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(any())).thenReturn(usuario);

        usuario.setRol("ROLE_ADMIN");
        usuario.setVerificado(true);
        usuario.setFoto("foto.png");

        boolean result = usuarioService.update(usuario);

        assertTrue(result);
    }



    @Test
    public void updateUsuarioInexistente_LanzaExcepcion() {
        Usuario inexistente = new Usuario();
        inexistente.setId(999);
        when(usuarioRepository.findById(999)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> usuarioService.update(inexistente));
        assertTrue(ex.getMessage().contains("No existe el usuario"));
    }

    @Test
    public void updateUsuarioSinId_ReturnsFalse() {
        Usuario sinId = new Usuario();
        boolean result = usuarioService.update(sinId);
        assertFalse(result);
    }

    @Test
    public void updateUsuarioNulo_ReturnsFalse() {
        boolean result = usuarioService.update(null);
        assertFalse(result);
    }

    @Test
    public void deleteUsuarioConContenido_Renombrado() {
        usuario.setFaunas(List.of(new Fauna()));
        when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuario));

        boolean result = usuarioService.deleteById(1);

        assertTrue(result);
        verify(usuarioRepository).save(any());
    }

    @Test
    public void deleteUsuarioSinContenido_Eliminado() {
        usuario.setFaunas(null);
        usuario.setRutas(null);
        usuario.setFloras(null);
        usuario.setComentarios(null);
        when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.deleteUsuarioBydId(1)).thenReturn(1);

        boolean result = usuarioService.deleteById(1);

        assertTrue(result);
    }

    @Test
    public void deleteUsuarioSinContenido_Falla() {
        usuario.setFaunas(null);
        usuario.setRutas(null);
        usuario.setFloras(null);
        usuario.setComentarios(null);
        when(usuarioRepository.findById(2)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.deleteUsuarioBydId(2)).thenReturn(0);

        boolean result = usuarioService.deleteById(2);

        assertFalse(result);
    }
}
