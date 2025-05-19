package es.iespuertodelacruz.mp.canarytrails.controller.v3;

import es.iespuertodelacruz.mp.canarytrails.dto.fauna.*;
import es.iespuertodelacruz.mp.canarytrails.entities.Fauna;
import es.iespuertodelacruz.mp.canarytrails.entities.Ruta;
import es.iespuertodelacruz.mp.canarytrails.entities.Usuario;
import es.iespuertodelacruz.mp.canarytrails.mapper.FaunaMapper;
import es.iespuertodelacruz.mp.canarytrails.service.FaunaService;
import es.iespuertodelacruz.mp.canarytrails.service.FotoManagementService;
import es.iespuertodelacruz.mp.canarytrails.service.RutaService;
import es.iespuertodelacruz.mp.canarytrails.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FaunaControllerV3Test {

    @InjectMocks
    private FaunaControllerV3 controller;

    @Mock
    private FaunaService faunaService;
    @Mock
    private FaunaMapper faunaMapper;
    @Mock
    private UsuarioService usuarioService;
    @Mock
    private RutaService rutaService;
    @Mock
    private FotoManagementService fotoManagementService;

    private Fauna fauna;
    private Usuario usuario;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        fauna = new Fauna();
        fauna.setId(1);
        fauna.setRutas(List.of());
        usuario = new Usuario();
        usuario.setId(1);
    }

    @Test
    public void testFindAllFaunas() {
        when(faunaService.findAll()).thenReturn(List.of(fauna));
        when(faunaMapper.toDto(any())).thenReturn(new FaunaSalidaDto(
                1, "nombre", "desc", true, null, null, "foto"
        ));

        ResponseEntity<?> response = controller.findAllFaunas();

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void testFindFaunaById_Found() {
        when(faunaService.findById(1)).thenReturn(fauna);
        when(faunaMapper.toDto(fauna)).thenReturn(new FaunaSalidaDto(
                1, "nombre", "desc", true, null, null, "foto"
        ));

        ResponseEntity<?> response = controller.findFaunaById(1);

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void testFindFaunaById_NotFound() {
        when(faunaService.findById(1)).thenReturn(null);

        ResponseEntity<?> response = controller.findFaunaById(1);

        assertEquals(404, response.getStatusCodeValue());
    }


    @Test
    public void testCreateFauna_UsuarioNotFound() {
        FaunaEntradaCreateDto dto = new FaunaEntradaCreateDto("nombre", "desc", true, 1, List.of(1));
        when(faunaMapper.toEntityCreate(dto)).thenReturn(fauna);
        when(usuarioService.findById(1)).thenReturn(null);

        ResponseEntity<?> response = controller.createFauna(dto);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    public void testCreateFauna_SaveFails() {
        FaunaEntradaCreateDto dto = new FaunaEntradaCreateDto("nombre", "desc", true, 1, List.of());
        when(faunaMapper.toEntityCreate(dto)).thenReturn(fauna);
        when(usuarioService.findById(1)).thenReturn(usuario);
        when(faunaService.save(any())).thenThrow(new RuntimeException("error"));

        ResponseEntity<?> response = controller.createFauna(dto);

        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    public void testUpdateFauna_Success() {
        FaunaEntradaUpdateDto dto = new FaunaEntradaUpdateDto(1, "nombre", "desc", true, 1, List.of());
        when(faunaMapper.toEntityUpdate(dto)).thenReturn(fauna);
        when(usuarioService.findById(1)).thenReturn(usuario);
        when(faunaService.update(any())).thenReturn(true);

        ResponseEntity<?> response = controller.updateFauna(dto);

        assertEquals(200, response.getStatusCodeValue());
        assertTrue((Boolean) response.getBody());
    }

    @Test
    public void testUpdateFauna_UsuarioNotFound() {
        FaunaEntradaUpdateDto dto = new FaunaEntradaUpdateDto(1, "nombre", "desc", true, 1, List.of());
        when(faunaMapper.toEntityUpdate(dto)).thenReturn(fauna);
        when(usuarioService.findById(1)).thenReturn(null);

        ResponseEntity<?> response = controller.updateFauna(dto);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    public void testUpdateFauna_Exception() {
        FaunaEntradaUpdateDto dto = new FaunaEntradaUpdateDto(1, "nombre", "desc", true, 1, List.of());
        when(faunaMapper.toEntityUpdate(dto)).thenReturn(fauna);
        when(usuarioService.findById(1)).thenReturn(usuario);
        when(faunaService.update(any())).thenThrow(new RuntimeException("error"));

        ResponseEntity<?> response = controller.updateFauna(dto);

        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    public void testUploadFile_Success() {
        MockMultipartFile file = new MockMultipartFile("file", "test.jpg", "image/jpeg", "contenido".getBytes());
        when(fotoManagementService.save(any(), eq("fauna"))).thenReturn("fauna/test.jpg");
        when(faunaService.findById(1)).thenReturn(fauna);
        when(faunaService.update(any())).thenReturn(true);

        ResponseEntity<?> response = controller.uploadFile(1, file);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("fauna/test.jpg", response.getBody());
    }

    @Test
    public void testUploadFile_FaunaNotFound() {
        MockMultipartFile file = new MockMultipartFile("file", "test.jpg", "image/jpeg", "contenido".getBytes());
        when(fotoManagementService.save(any(), eq("fauna"))).thenReturn("fauna/test.jpg");
        when(faunaService.findById(1)).thenReturn(null);

        ResponseEntity<?> response = controller.uploadFile(1, file);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    public void testUploadFile_Exception() {
        MockMultipartFile file = new MockMultipartFile("file", "test.jpg", "image/jpeg", "contenido".getBytes());
        when(fotoManagementService.save(any(), eq("fauna"))).thenThrow(new RuntimeException("error"));

        ResponseEntity<?> response = controller.uploadFile(1, file);

        assertEquals(400, response.getStatusCodeValue());
        assertTrue(response.getBody().toString().contains("No se pudo subir"));
    }

    @Test
    public void testDeleteFauna_Success() {
        when(faunaService.findById(1)).thenReturn(fauna);
        when(faunaService.deleteById(1)).thenReturn(true);

        ResponseEntity<?> response = controller.deleteFauna(1);

        assertEquals(200, response.getStatusCodeValue());
        assertTrue((Boolean) response.getBody());
    }

    @Test
    public void testDeleteFauna_NotFound() {
        when(faunaService.findById(1)).thenReturn(null);

        ResponseEntity<?> response = controller.deleteFauna(1);

        assertEquals(404, response.getStatusCodeValue());
    }
}
