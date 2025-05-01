package es.iespuertodelacruz.mp.canarytrails.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import es.iespuertodelacruz.mp.canarytrails.config.TestSecurityConfig;
import es.iespuertodelacruz.mp.canarytrails.dto.ZonaDTO;
import es.iespuertodelacruz.mp.canarytrails.entities.Zona;
import es.iespuertodelacruz.mp.canarytrails.mapper.ZonaMapper;
import es.iespuertodelacruz.mp.canarytrails.service.ZonaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Import(TestSecurityConfig.class)
@WebMvcTest(ZonaController.class)
class ZonaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ZonaService zonaService;

    @MockBean
    private ZonaMapper zonaMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllZonas() throws Exception {
        Zona zona = new Zona(); zona.setId(1); zona.setNombre("Norte");
        ZonaDTO dto = new ZonaDTO(1, "Norte");

        when(zonaService.findAll()).thenReturn(List.of(zona));
        when(zonaMapper.toDTO(zona)).thenReturn(dto);

        mockMvc.perform(get("/api/zonas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Norte"));
    }

    @Test
    void testGetByIdSuccess() throws Exception {
        Zona zona = new Zona(); zona.setId(2); zona.setNombre("Sur");
        ZonaDTO dto = new ZonaDTO(2, "Sur");

        when(zonaService.findById(2)).thenReturn(Optional.of(zona));
        when(zonaMapper.toDTO(zona)).thenReturn(dto);

        mockMvc.perform(get("/api/zonas/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.nombre").value("Sur"));
    }

    @Test
    void testGetByIdNotFound() throws Exception {
        when(zonaService.findById(99)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/zonas/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateZona() throws Exception {
        ZonaDTO input = new ZonaDTO(null, "Centro");
        Zona entity = new Zona(); entity.setNombre("Centro");
        Zona saved = new Zona(); saved.setId(3); saved.setNombre("Centro");
        ZonaDTO output = new ZonaDTO(3, "Centro");

        when(zonaMapper.toEntity(any(ZonaDTO.class))).thenReturn(entity);
        when(zonaService.save(any(Zona.class))).thenReturn(saved);
        when(zonaMapper.toDTO(saved)).thenReturn(output);

        mockMvc.perform(post("/api/zonas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.nombre").value("Centro"));
    }


    @Test
    void testDeleteZonaExists() throws Exception {
        Zona zona = new Zona(); zona.setId(4); zona.setNombre("Este");

        when(zonaService.findById(4)).thenReturn(Optional.of(zona));
        doNothing().when(zonaService).deleteById(4);

        mockMvc.perform(delete("/api/zonas/4"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteZonaNotFound() throws Exception {
        when(zonaService.findById(404)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/api/zonas/404"))
                .andExpect(status().isNotFound());
    }
}
