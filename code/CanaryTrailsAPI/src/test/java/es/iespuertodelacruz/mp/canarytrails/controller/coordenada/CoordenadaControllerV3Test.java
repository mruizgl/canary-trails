package es.iespuertodelacruz.mp.canarytrails.controller.v3;

import es.iespuertodelacruz.mp.canarytrails.dto.coordenada.CoordenadaEntradaCreateDto;
import es.iespuertodelacruz.mp.canarytrails.dto.coordenada.CoordenadaEntradaUpdateDto;
import es.iespuertodelacruz.mp.canarytrails.dto.coordenada.CoordenadaSalidaDto;
import es.iespuertodelacruz.mp.canarytrails.entities.Coordenada;
import es.iespuertodelacruz.mp.canarytrails.mapper.CoordenadaMapper;
import es.iespuertodelacruz.mp.canarytrails.service.CoordenadaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CoordenadaControllerV3Test {

    @InjectMocks
    private CoordenadaControllerV3 controller;

    @Mock private CoordenadaService coordenadaService;
    @Mock private CoordenadaMapper coordenadaMapper;

    private Coordenada coordenada;

    @BeforeEach
    void setup() {
        coordenada = new Coordenada();
        coordenada.setId(1);
        coordenada.setLatitud(BigDecimal.valueOf(15));
        coordenada.setLongitud(BigDecimal.valueOf(15));
    }

    @Test
    void testGetAll() {
        when(coordenadaService.findAll()).thenReturn(List.of(coordenada));
        when(coordenadaMapper.toDTOList(List.of(coordenada))).thenReturn(List.of(
                new CoordenadaSalidaDto(1, BigDecimal.valueOf(15), BigDecimal.valueOf(15), null)
        ));

        ResponseEntity<?> response = controller.getAll();
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testGetById_Found() {
        when(coordenadaService.findById(1)).thenReturn(coordenada);
        when(coordenadaMapper.toDTO(coordenada)).thenReturn(new CoordenadaSalidaDto(1, BigDecimal.valueOf(15), BigDecimal.valueOf(15), null));

        ResponseEntity<?> response = controller.getById(1);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testGetById_NotFound() {
        when(coordenadaService.findById(1)).thenReturn(null);

        ResponseEntity<?> response = controller.getById(1);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testCreate_Success() {
        CoordenadaEntradaCreateDto dto = new CoordenadaEntradaCreateDto(BigDecimal.valueOf(15), BigDecimal.valueOf(15));
        when(coordenadaMapper.toEntityCreate(dto)).thenReturn(coordenada);
        when(coordenadaService.save(coordenada)).thenReturn(coordenada);
        when(coordenadaMapper.toDTO(coordenada)).thenReturn(new CoordenadaSalidaDto(1, BigDecimal.valueOf(15), BigDecimal.valueOf(15), null));

        ResponseEntity<?> response = controller.create(dto);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testCreate_Exception() {
        CoordenadaEntradaCreateDto dto = new CoordenadaEntradaCreateDto(BigDecimal.valueOf(15), BigDecimal.valueOf(15));
        when(coordenadaMapper.toEntityCreate(dto)).thenReturn(coordenada);
        when(coordenadaService.save(coordenada)).thenThrow(new RuntimeException("error"));

        ResponseEntity<?> response = controller.create(dto);
        assertEquals(400, response.getStatusCodeValue());
        assertTrue(response.getBody().toString().contains("error"));
    }

    @Test
    void testUpdate_Success() {
        CoordenadaEntradaUpdateDto dto = new CoordenadaEntradaUpdateDto(1, BigDecimal.valueOf(15), BigDecimal.valueOf(15));
        when(coordenadaMapper.toEntityUpdate(dto)).thenReturn(coordenada);
        when(coordenadaService.update(coordenada)).thenReturn(true);

        ResponseEntity<?> response = controller.update(dto);
        assertEquals(200, response.getStatusCodeValue());
        assertTrue((Boolean) response.getBody());
    }

    @Test
    void testUpdate_Exception() {
        CoordenadaEntradaUpdateDto dto = new CoordenadaEntradaUpdateDto(1, BigDecimal.valueOf(15), BigDecimal.valueOf(15));
        when(coordenadaMapper.toEntityUpdate(dto)).thenReturn(coordenada);
        when(coordenadaService.update(coordenada)).thenThrow(new Error("fallo"));

        ResponseEntity<?> response = controller.update(dto);
        assertEquals(400, response.getStatusCodeValue());
        assertTrue(response.getBody().toString().contains("fallo"));
    }

    @Test
    void testDelete_Success() {
        when(coordenadaService.findById(1)).thenReturn(coordenada);
        when(coordenadaService.deleteById(1)).thenReturn(true);

        ResponseEntity<?> response = controller.delete(1);
        assertEquals(200, response.getStatusCodeValue());
        assertTrue((Boolean) response.getBody());
    }

    @Test
    void testDelete_NotFound() {
        when(coordenadaService.findById(1)).thenReturn(null);

        ResponseEntity<?> response = controller.delete(1);
        assertEquals(404, response.getStatusCodeValue());
    }
}
