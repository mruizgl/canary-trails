package es.iespuertodelacruz.mp.canarytrails.controller.v2;

import es.iespuertodelacruz.mp.canarytrails.dto.comentario.ComentarioEntradaCreateDto;
import es.iespuertodelacruz.mp.canarytrails.dto.comentario.ComentarioSalidaDto;
import es.iespuertodelacruz.mp.canarytrails.entities.Comentario;
import es.iespuertodelacruz.mp.canarytrails.entities.Ruta;
import es.iespuertodelacruz.mp.canarytrails.entities.Usuario;
import es.iespuertodelacruz.mp.canarytrails.mapper.ComentarioMapper;
import es.iespuertodelacruz.mp.canarytrails.service.ComentarioService;
import es.iespuertodelacruz.mp.canarytrails.service.RutaService;
import es.iespuertodelacruz.mp.canarytrails.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ComentarioControllerV2Test {

    @InjectMocks
    private ComentarioControllerV2 controller;

    @Mock private ComentarioService comentarioService;
    @Mock private ComentarioMapper comentarioMapper;
    @Mock private UsuarioService usuarioService;
    @Mock private RutaService rutaService;

    private Usuario usuario;
    private Comentario comentario;
    private Ruta ruta;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setId(1);
        usuario.setNombre("user");

        ruta = new Ruta();
        ruta.setId(1);

        comentario = new Comentario();
        comentario.setId(1);
        comentario.setUsuario(usuario);
        comentario.setRuta(ruta);

        Authentication auth = mock(Authentication.class);
        lenient().when(auth.getName()).thenReturn("user");

        SecurityContext context = mock(SecurityContext.class);
        lenient().when(context.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(context);
    }

    @Test
    public void testGetById_Success() {
        when(comentarioService.findById(1)).thenReturn(comentario);
        when(comentarioMapper.toDto(comentario)).thenReturn(new ComentarioSalidaDto(1, "Título", "Texto", null, null));

        ResponseEntity<?> response = controller.getById(1);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void testGetById_NotFound() {
        when(comentarioService.findById(1)).thenReturn(null);
        ResponseEntity<?> response = controller.getById(1);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    public void testCrearComentario_Success() {
        ComentarioEntradaCreateDto dto = new ComentarioEntradaCreateDto("título", "texto", 1, 1);
        when(comentarioMapper.toEntityCreate(dto)).thenReturn(new Comentario());
        when(usuarioService.findByUserName("user")).thenReturn(usuario);
        when(rutaService.findById(1)).thenReturn(ruta);
        when(comentarioService.save(any())).thenReturn(comentario);
        when(comentarioMapper.toDto(any())).thenReturn(new ComentarioSalidaDto(1, "Título", "Texto", null, null));

        ResponseEntity<?> response = controller.crearComentario(dto);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void testCrearComentario_UsuarioNotFound() {
        ComentarioEntradaCreateDto dto = new ComentarioEntradaCreateDto("título", "texto", 1, 1);
        when(comentarioMapper.toEntityCreate(dto)).thenReturn(new Comentario());
        when(usuarioService.findByUserName("user")).thenReturn(null);

        ResponseEntity<?> response = controller.crearComentario(dto);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    public void testCrearComentario_RutaNotFound() {
        ComentarioEntradaCreateDto dto = new ComentarioEntradaCreateDto("título", "texto", 1, 1);
        when(comentarioMapper.toEntityCreate(dto)).thenReturn(new Comentario());
        when(usuarioService.findByUserName("user")).thenReturn(usuario);
        when(rutaService.findById(1)).thenReturn(null);

        ResponseEntity<?> response = controller.crearComentario(dto);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    public void testCrearComentario_Exception() {
        ComentarioEntradaCreateDto dto = new ComentarioEntradaCreateDto("título", "texto", 1, 1);
        when(comentarioMapper.toEntityCreate(dto)).thenReturn(new Comentario());
        when(usuarioService.findByUserName("user")).thenReturn(usuario);
        when(rutaService.findById(1)).thenReturn(ruta);
        when(comentarioService.save(any())).thenThrow(new RuntimeException("error"));

        ResponseEntity<?> response = controller.crearComentario(dto);
        assertEquals(400, response.getStatusCodeValue());
        assertTrue(response.getBody().toString().contains("error"));
    }

    @Test
    public void testEliminarComentario_Success() {
        when(comentarioService.findById(1)).thenReturn(comentario);
        when(comentarioService.deleteById(1)).thenReturn(true);

        ResponseEntity<?> response = controller.eliminarComentario(1);
        assertEquals(200, response.getStatusCodeValue());
        assertTrue((Boolean) response.getBody());
    }

    @Test
    public void testEliminarComentario_NotFound() {
        when(comentarioService.findById(1)).thenReturn(null);

        ResponseEntity<?> response = controller.eliminarComentario(1);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    public void testEliminarComentario_Forbidden() {
        Usuario otro = new Usuario();
        otro.setNombre("otro");
        comentario.setUsuario(otro);

        when(comentarioService.findById(1)).thenReturn(comentario);

        ResponseEntity<?> response = controller.eliminarComentario(1);
        assertEquals(403, response.getStatusCodeValue());
    }
}
