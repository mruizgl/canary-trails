package es.iespuertodelacruz.mp.canarytrails.controller.v3;

import es.iespuertodelacruz.mp.canarytrails.dto.comentario.ComentarioEntradaCreateDto;
import es.iespuertodelacruz.mp.canarytrails.dto.comentario.ComentarioEntradaUpdateDto;
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

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ComentarioControllerV3Test {

    @InjectMocks
    private ComentarioControllerV3 controller;

    @Mock private ComentarioService comentarioService;
    @Mock private ComentarioMapper comentarioMapper;
    @Mock private UsuarioService usuarioService;
    @Mock private RutaService rutaService;

    private Comentario comentario;
    private Usuario usuario;
    private Ruta ruta;

    @BeforeEach
    void setup() {
        usuario = new Usuario();
        usuario.setId(1);
        usuario.setNombre("test");

        ruta = new Ruta();
        ruta.setId(1);
        ruta.setNombre("Ruta test");

        comentario = new Comentario();
        comentario.setId(1);
        comentario.setTitulo("Buen camino");
        comentario.setDescripcion("Hermosa ruta");
        comentario.setUsuario(usuario);
        comentario.setRuta(ruta);
    }

    @Test
    void testGetAll() {
        when(comentarioService.findAll()).thenReturn(List.of(comentario));
        when(comentarioMapper.toDto(comentario)).thenReturn(new ComentarioSalidaDto(1, "Buen camino", "Hermosa ruta", null, null));

        ResponseEntity<?> response = controller.getAll();
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testGetById_Found() {
        when(comentarioService.findById(1)).thenReturn(comentario);
        when(comentarioMapper.toDto(comentario)).thenReturn(new ComentarioSalidaDto(1, "Buen camino", "Hermosa ruta", null, null));

        ResponseEntity<?> response = controller.getById(1);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testGetById_NotFound() {
        when(comentarioService.findById(1)).thenReturn(null);
        ResponseEntity<?> response = controller.getById(1);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testCrearComentario_Success() {
        ComentarioEntradaCreateDto dto = new ComentarioEntradaCreateDto("Buen camino", "Hermosa ruta", 1, 1);
        when(comentarioMapper.toEntityCreate(dto)).thenReturn(comentario);
        when(usuarioService.findById(1)).thenReturn(usuario);
        when(rutaService.findById(1)).thenReturn(ruta);
        when(comentarioService.save(comentario)).thenReturn(comentario);
        when(comentarioMapper.toDto(comentario)).thenReturn(new ComentarioSalidaDto(1, "Buen camino", "Hermosa ruta", null, null));

        ResponseEntity<?> response = controller.crearComentario(dto);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testCrearComentario_UsuarioNotFound() {
        ComentarioEntradaCreateDto dto = new ComentarioEntradaCreateDto("Buen camino", "Hermosa ruta", 99, 1);
        when(comentarioMapper.toEntityCreate(dto)).thenReturn(comentario);
        when(usuarioService.findById(99)).thenReturn(null);

        ResponseEntity<?> response = controller.crearComentario(dto);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testCrearComentario_RutaNotFound() {
        ComentarioEntradaCreateDto dto = new ComentarioEntradaCreateDto("Buen camino", "Hermosa ruta", 1, 1);
        when(comentarioMapper.toEntityCreate(dto)).thenReturn(comentario);
        when(usuarioService.findById(1)).thenReturn(usuario);
        when(rutaService.findById(1)).thenReturn(null);

        ResponseEntity<?> response = controller.crearComentario(dto);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testCrearComentario_ThrowError() {
        ComentarioEntradaCreateDto dto = new ComentarioEntradaCreateDto("Buen camino", "Hermosa ruta", 1, 1);
        when(comentarioMapper.toEntityCreate(dto)).thenReturn(comentario);
        when(usuarioService.findById(1)).thenReturn(usuario);
        when(rutaService.findById(1)).thenReturn(ruta);
        when(comentarioService.save(comentario)).thenThrow(new RuntimeException("Error"));

        ResponseEntity<?> response = controller.crearComentario(dto);
        assertEquals(400, response.getStatusCodeValue());
        assertTrue(response.getBody().toString().contains("Error"));
    }

    @Test
    void testEditarComentario_Success() {
        ComentarioEntradaUpdateDto dto = new ComentarioEntradaUpdateDto(1, "Editado", "Actualizado", 1, 1);
        when(comentarioMapper.toEntityUpdate(dto)).thenReturn(comentario);
        when(usuarioService.findById(1)).thenReturn(usuario);
        when(rutaService.findById(1)).thenReturn(ruta);
        when(comentarioService.update(comentario)).thenReturn(true);

        ResponseEntity<?> response = controller.editarComentario(dto);
        assertEquals(200, response.getStatusCodeValue());
        assertTrue((Boolean) response.getBody());
    }

    @Test
    void testEditarComentario_UsuarioNotFound() {
        ComentarioEntradaUpdateDto dto = new ComentarioEntradaUpdateDto(1, "Editado", "Actualizado", 99, 1);
        when(comentarioMapper.toEntityUpdate(dto)).thenReturn(comentario);
        when(usuarioService.findById(99)).thenReturn(null);

        ResponseEntity<?> response = controller.editarComentario(dto);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testEditarComentario_RutaNotFound() {
        ComentarioEntradaUpdateDto dto = new ComentarioEntradaUpdateDto(1, "Editado", "Actualizado", 1, 99);
        when(comentarioMapper.toEntityUpdate(dto)).thenReturn(comentario);
        when(usuarioService.findById(1)).thenReturn(usuario);
        when(rutaService.findById(99)).thenReturn(null);

        ResponseEntity<?> response = controller.editarComentario(dto);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testEditarComentario_ThrowException() {
        ComentarioEntradaUpdateDto dto = new ComentarioEntradaUpdateDto(1, "Editado", "Actualizado", 1, 1);
        when(comentarioMapper.toEntityUpdate(dto)).thenReturn(comentario);
        when(usuarioService.findById(1)).thenReturn(usuario);
        when(rutaService.findById(1)).thenReturn(ruta);
        when(comentarioService.update(comentario)).thenThrow(new RuntimeException("Error"));

        ResponseEntity<?> response = controller.editarComentario(dto);
        assertEquals(400, response.getStatusCodeValue());
        assertTrue(response.getBody().toString().contains("Error"));
    }

    @Test
    void testEliminarComentario_Success() {
        when(comentarioService.findById(1)).thenReturn(comentario);
        when(comentarioService.deleteById(1)).thenReturn(true);

        ResponseEntity<?> response = controller.eliminarComentario(1);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testEliminarComentario_NotFound() {
        when(comentarioService.findById(1)).thenReturn(null);

        ResponseEntity<?> response = controller.eliminarComentario(1);
        assertEquals(404, response.getStatusCodeValue());
    }
}
