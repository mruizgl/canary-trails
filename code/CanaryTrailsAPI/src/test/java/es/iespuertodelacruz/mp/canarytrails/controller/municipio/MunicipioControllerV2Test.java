package es.iespuertodelacruz.mp.canarytrails.controller.v2;

import es.iespuertodelacruz.mp.canarytrails.dto.municipio.MunicipioSalidaDto;
import es.iespuertodelacruz.mp.canarytrails.entities.Municipio;
import es.iespuertodelacruz.mp.canarytrails.mapper.MunicipioMapper;
import es.iespuertodelacruz.mp.canarytrails.service.MunicipioService;
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
public class MunicipioControllerV2Test {

    @InjectMocks
    private MunicipioControllerV2 controller;

    @Mock
    private MunicipioService municipioService;

    @Mock
    private MunicipioMapper municipioMapper;

    private Municipio municipio;

    @BeforeEach
    public void setUp() {
        municipio = new Municipio();
        municipio.setId(1);
        municipio.setNombre("Tegueste");
    }

    @Test
    public void testGetAll_ReturnsMunicipios() {
        when(municipioService.findAll()).thenReturn(List.of(municipio));
        when(municipioMapper.toDto(municipio)).thenReturn(new MunicipioSalidaDto(1, "Tegueste", 500, 28.5f, -16.3f, null, null));

        ResponseEntity<?> response = controller.getAll();
        assertEquals(200, response.getStatusCodeValue());

        List<MunicipioSalidaDto> result = (List<MunicipioSalidaDto>) response.getBody();
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Tegueste", result.get(0).nombre());
    }

    @Test
    public void testGetById_Found() {
        when(municipioService.findById(1)).thenReturn(municipio);
        when(municipioMapper.toDto(municipio)).thenReturn(new MunicipioSalidaDto(1, "Tegueste", 500, 28.5f, -16.3f, null, null));

        ResponseEntity<?> response = controller.getById(1);
        assertEquals(200, response.getStatusCodeValue());

        MunicipioSalidaDto dto = (MunicipioSalidaDto) response.getBody();
        assertNotNull(dto);
        assertEquals("Tegueste", dto.nombre());
    }

    @Test
    public void testGetById_NotFound() {
        when(municipioService.findById(999)).thenReturn(null);

        ResponseEntity<?> response = controller.getById(999);
        assertEquals(404, response.getStatusCodeValue());
    }
}
