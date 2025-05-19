package es.iespuertodelacruz.mp.canarytrails.controller.v3;

import es.iespuertodelacruz.mp.canarytrails.dto.zona.ZonaEntradaCreateDto;
import es.iespuertodelacruz.mp.canarytrails.dto.zona.ZonaEntradaUpdateDto;
import es.iespuertodelacruz.mp.canarytrails.dto.zona.ZonaSalidaDto;
import es.iespuertodelacruz.mp.canarytrails.entities.Municipio;
import es.iespuertodelacruz.mp.canarytrails.entities.Zona;
import es.iespuertodelacruz.mp.canarytrails.mapper.ZonaMapper;
import es.iespuertodelacruz.mp.canarytrails.service.MunicipioService;
import es.iespuertodelacruz.mp.canarytrails.service.ZonaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ZonaControllerV3Test {

    @InjectMocks
    private ZonaControllerV3 controller;

    @Mock private ZonaService zonaService;
    @Mock private ZonaMapper zonaMapper;
    @Mock private MunicipioService municipioService;

    private Zona zona;
    private Municipio municipio;

    @BeforeEach
    void setUp() {
        zona = new Zona();
        zona.setId(1);
        zona.setNombre("Norte");

        municipio = new Municipio();
        municipio.setId(1);
        municipio.setNombre("La Orotava");
    }

    @Test
    void testGetAll() {
        when(zonaService.findAll()).thenReturn(List.of(zona));
        when(zonaMapper.toDTOList(List.of(zona))).thenReturn(List.of(new ZonaSalidaDto(1, "Norte", Collections.emptyList())));

        ResponseEntity<?> response = controller.getAll();
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testGetById_Found() {
        when(zonaService.findById(1)).thenReturn(zona);
        when(zonaMapper.toDTO(zona)).thenReturn(new ZonaSalidaDto(1, "Norte", Collections.emptyList()));

        ResponseEntity<?> response = controller.getById(1);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testGetById_NotFound() {
        when(zonaService.findById(1)).thenReturn(null);

        ResponseEntity<?> response = controller.getById(1);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testCreate_Success() {
        ZonaEntradaCreateDto dto = new ZonaEntradaCreateDto("Norte", List.of(1));
        when(zonaMapper.toEntityCreate(dto)).thenReturn(zona);
        when(municipioService.findById(1)).thenReturn(municipio);
        when(zonaService.save(zona)).thenReturn(zona);
        when(zonaMapper.toDTO(zona)).thenReturn(new ZonaSalidaDto(1, "Norte", Collections.emptyList()));

        ResponseEntity<?> response = controller.create(dto);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testCreate_ErrorEnSave() {
        ZonaEntradaCreateDto dto = new ZonaEntradaCreateDto("Norte", List.of(1));
        when(zonaMapper.toEntityCreate(dto)).thenReturn(zona);
        when(municipioService.findById(1)).thenReturn(municipio);
        when(zonaService.save(zona)).thenThrow(new RuntimeException("Error"));

        ResponseEntity<?> response = controller.create(dto);
        assertEquals(400, response.getStatusCodeValue());
        assertTrue(response.getBody().toString().contains("Error"));
    }

    @Test
    void testUpdate_Success() {
        ZonaEntradaUpdateDto dto = new ZonaEntradaUpdateDto(1, "Norte", List.of(1));
        when(zonaMapper.toEntityUpdate(dto)).thenReturn(zona);
        when(municipioService.findById(1)).thenReturn(municipio);
        when(zonaService.update(zona)).thenReturn(true);

        ResponseEntity<?> response = controller.update(dto);
        assertEquals(200, response.getStatusCodeValue());
        assertTrue((Boolean) response.getBody());
    }

    @Test
    void testUpdate_MunicipioNotFound() {
        ZonaEntradaUpdateDto dto = new ZonaEntradaUpdateDto(1, "Norte", List.of(1));
        when(zonaMapper.toEntityUpdate(dto)).thenReturn(zona);
        when(municipioService.findById(1)).thenReturn(null);

        ResponseEntity<?> response = controller.update(dto);
        assertEquals(400, response.getStatusCodeValue());
        assertTrue(response.getBody().toString().contains("No se puede dejar un Municipio sin Zona"));
    }

    @Test
    void testUpdate_RuntimeException() {
        ZonaEntradaUpdateDto dto = new ZonaEntradaUpdateDto(1, "Norte", List.of(1));
        when(zonaMapper.toEntityUpdate(dto)).thenReturn(zona);
        when(municipioService.findById(1)).thenReturn(municipio);
        when(zonaService.update(zona)).thenThrow(new RuntimeException("Error"));

        ResponseEntity<?> response = controller.update(dto);
        assertEquals(400, response.getStatusCodeValue());
        assertTrue(response.getBody().toString().contains("Error"));
    }

    @Test
    void testDelete_Success() {
        when(zonaService.findById(1)).thenReturn(zona);
        when(zonaService.deleteById(1)).thenReturn(true);

        ResponseEntity<?> response = controller.delete(1);
        assertEquals(200, response.getStatusCodeValue());
        assertTrue((Boolean) response.getBody());
    }

    @Test
    void testDelete_NotFound() {
        when(zonaService.findById(1)).thenReturn(null);

        ResponseEntity<?> response = controller.delete(1);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testDelete_Exception() {
        when(zonaService.findById(1)).thenReturn(zona);
        when(zonaService.deleteById(1)).thenThrow(new RuntimeException("Error"));

        ResponseEntity<?> response = controller.delete(1);
        assertEquals(400, response.getStatusCodeValue());
        assertTrue(response.getBody().toString().contains("Error"));
    }
}
