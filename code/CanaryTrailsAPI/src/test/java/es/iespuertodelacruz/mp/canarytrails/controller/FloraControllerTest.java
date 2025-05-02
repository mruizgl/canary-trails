package es.iespuertodelacruz.mp.canarytrails.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import es.iespuertodelacruz.mp.canarytrails.dto.FloraDTO;
import es.iespuertodelacruz.mp.canarytrails.entities.Flora;
import es.iespuertodelacruz.mp.canarytrails.mapper.FloraMapper;
import es.iespuertodelacruz.mp.canarytrails.service.FloraService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FloraController.class)
@AutoConfigureMockMvc(addFilters = false)
class FloraControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FloraService floraService;

    @MockBean
    private FloraMapper floraMapper;

    private Flora flora;
    private FloraDTO floraDTO;

    @BeforeEach
    void setup() {
        flora = new Flora();
        flora.setId(1);
        flora.setNombre("Tajinaste");

        floraDTO = new FloraDTO(1, "Tajinaste", "Echium wildpretii", "Lanceolada", "Primavera", "Verano", "Endémica", true);
    }

    @Test
    void testGetAll() throws Exception {
        when(floraService.findAll()).thenReturn(Collections.singletonList(flora));
        when(floraMapper.toDTOList(any())).thenReturn(Collections.singletonList(floraDTO));

        mockMvc.perform(get("/flora"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Tajinaste"));
    }

    @Test
    void testGetById() throws Exception {
        when(floraService.findById(1)).thenReturn(Optional.of(flora));
        when(floraMapper.toDTO(flora)).thenReturn(floraDTO);

        mockMvc.perform(get("/flora/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Tajinaste"));
    }

    @Test
    void testCreate() throws Exception {
        when(floraMapper.toEntity(any())).thenReturn(flora);
        when(floraService.save(flora)).thenReturn(flora);
        when(floraMapper.toDTO(flora)).thenReturn(floraDTO);

        mockMvc.perform(post("/flora")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(floraDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Tajinaste"));
    }
}
