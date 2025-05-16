package es.iespuertodelacruz.mp.canarytrails.controller.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import es.iespuertodelacruz.mp.canarytrails.config.TestSecurityConfig;
import es.iespuertodelacruz.mp.canarytrails.controller.v3.FaunaControllerV3;
import es.iespuertodelacruz.mp.canarytrails.dto.fauna.FaunaEntradaCreateDto;
import es.iespuertodelacruz.mp.canarytrails.dto.fauna.FaunaEntradaUpdateDto;
import es.iespuertodelacruz.mp.canarytrails.dto.fauna.FaunaSalidaDto;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FaunaControllerV3.class)
@Import(TestSecurityConfig.class)
public class FaunaControllerV3Test {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    FaunaService faunaService;

    @MockBean
    FaunaMapper faunaMapper;

    @MockBean
    UsuarioService usuarioService;

    @MockBean
    RutaService rutaService;

    @MockBean
    FotoManagementService fotoManagementService;

    private List<Ruta> listaRutas;
    private Usuario usuario;
    private Fauna fauna;
    private FaunaSalidaDto faunaSalidaDto;
    private FaunaEntradaUpdateDto faunaEntradaUpdateDto;

    @BeforeEach
    public void beforeEach() {
        Ruta ruta1 = new Ruta();
        ruta1.setId(1);
        ruta1.setNombre("Ruta de los volcanes");

        Ruta ruta2 = new Ruta();
        ruta2.setId(2);
        ruta2.setNombre("Sendero de los bosques");

        listaRutas = new ArrayList<>();
        listaRutas.add(ruta1);
        listaRutas.add(ruta2);

        usuario = new Usuario();
        usuario.setId(1);
        usuario.setNombre("Admin");

        fauna = new Fauna();
        fauna.setId(1);
        fauna.setNombre("Lagarto Gigante");

        faunaSalidaDto = new FaunaSalidaDto(1, "Lagarto Gigante", "Reptil endémico de Canarias",
                true, null, null, null
        );

        faunaEntradaUpdateDto = new FaunaEntradaUpdateDto(1, "Lagarto Gigante", "Reptil",
                true, 1, List.of(1)
        );

    }

    @Test
    public void findAllFaunasTest() throws Exception {
        when(faunaService.findAll()).thenReturn(List.of(fauna));
        when(faunaMapper.toDto(fauna)).thenReturn(faunaSalidaDto);

        mockMvc.perform(get("/api/v3/faunas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void findFaunaByIdTest_NotFound() throws Exception {
        when(faunaService.findById(99)).thenReturn(null);

        mockMvc.perform(get("/api/v3/faunas/99"))
                .andExpect(status().isNotFound());
    }


    @Test
    public void updateFaunaTest() throws Exception {
        when(faunaMapper.toEntityUpdate(faunaEntradaUpdateDto)).thenReturn(fauna);
        when(usuarioService.findById(1)).thenReturn(usuario);
        when(rutaService.findById(1)).thenReturn(listaRutas.get(0));
        when(faunaService.update(fauna)).thenReturn(true);

        mockMvc.perform(put("/api/v3/faunas/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(faunaEntradaUpdateDto)))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }


    @Test
    public void uploadFileTest() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "lagarto.jpg", "image/jpeg", "data".getBytes());

        when(fotoManagementService.save(any(), any())).thenReturn("lagarto.jpg");
        when(faunaService.findById(1)).thenReturn(fauna);
        when(faunaService.update(any())).thenReturn(true);

        mockMvc.perform(multipart("/api/v3/faunas/upload/{id}", 1)
                        .file(file)
                        .param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(content().string("lagarto.jpg"));
    }

    @Test
    public void deleteFaunaTest() throws Exception {
        when(faunaService.findById(1)).thenReturn(fauna); // necesario
        when(faunaService.deleteById(1)).thenReturn(true);

        mockMvc.perform(delete("/api/v3/faunas/delete/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }


    @Test
    public void createFaunaTest() throws Exception {
        List<Integer> idRutas = listaRutas.stream()
                .map(Ruta::getId)
                .toList();

        FaunaEntradaCreateDto dto = new FaunaEntradaCreateDto("Lagarto Gigante", "Reptil endémico",
                true, usuario.getId(), idRutas);


        when(faunaMapper.toEntityCreate(any())).thenReturn(fauna);
        when(usuarioService.findById(1)).thenReturn(usuario);
        when(rutaService.findById(1)).thenReturn(listaRutas.get(0));
        when(faunaService.save(any())).thenReturn(fauna);
        when(faunaMapper.toDto(fauna)).thenReturn(faunaSalidaDto);

        mockMvc.perform(post("/api/v3/faunas/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(fauna.getId()))
                .andExpect(jsonPath("$.nombre").value(fauna.getNombre()));
    }


    @Test
    void shouldReturnFaunaById() throws Exception {
        Fauna fauna = new Fauna();
        fauna.setId(1);
        fauna.setNombre("Lagarto Gigante");
        fauna.setDescripcion("Reptil endémico");
        fauna.setAprobada(true);

        FaunaSalidaDto dto = new FaunaSalidaDto(1, "Lagarto Gigante", "Reptil endémico",
                true, null, null, null
        );

        //cuando el servicio busque la fauna por ID, devuelve fauna
        when(faunaService.findById(1)).thenReturn(fauna);
        when(faunaMapper.toDto(fauna)).thenReturn(dto);

        mockMvc.perform(get("/api/v3/faunas/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Lagarto Gigante"))
                .andExpect(jsonPath("$.descripcion").value("Reptil endémico"));
    }

    @Test
    void createFauna_shouldReturnBadRequestOnError() throws Exception {
        FaunaEntradaCreateDto dto = new FaunaEntradaCreateDto(
                "Lagarto", "desc", true, 1, List.of(1)
        );

        when(faunaMapper.toEntityCreate(any())).thenReturn(new Fauna());
        when(usuarioService.findById(1)).thenReturn(new Usuario());
        when(rutaService.findById(1)).thenReturn(new Ruta());
        when(faunaService.save(any())).thenThrow(new RuntimeException("Error al guardar"));

        mockMvc.perform(post("/api/v3/faunas/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Error al guardar"));
    }

    @Test
    void updateFauna_shouldReturnBadRequestOnError() throws Exception {
        FaunaEntradaUpdateDto dto = new FaunaEntradaUpdateDto(
                1, "Lagarto", "desc", true, 1, List.of(1)
        );

        when(faunaMapper.toEntityUpdate(any())).thenReturn(new Fauna());
        when(usuarioService.findById(1)).thenReturn(new Usuario());
        when(rutaService.findById(1)).thenReturn(new Ruta());
        when(faunaService.update(any())).thenThrow(new RuntimeException("Error al actualizar"));

        mockMvc.perform(put("/api/v3/faunas/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Error al actualizar"));
    }

    @Test
    void uploadFile_shouldReturnBadRequestWhenUploadFails() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "file", "fallo.jpg", "image/jpeg", "contenido".getBytes());

        when(fotoManagementService.save(any(), any())).thenThrow(new RuntimeException("hola soy un error simulado"));

        mockMvc.perform(multipart("/api/v3/faunas/upload/{id}", 1)
                        .file(file)
                        .param("id", "1"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("No se pudo subir el archivo: fallo.jpg")))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("hola soy un error simulado")));
    }


    @Test
    public void createFaunaTest_NotFoundUser() throws Exception {
        FaunaEntradaCreateDto dto = new FaunaEntradaCreateDto("Test Fauna", "Descripción", true, 999, List.of(1));

        when(faunaMapper.toEntityCreate(any())).thenReturn(new Fauna());
        when(usuarioService.findById(999)).thenReturn(null);

        mockMvc.perform(post("/api/v3/faunas/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateFaunaTest_NotFoundUser() throws Exception {
        FaunaEntradaUpdateDto dto = new FaunaEntradaUpdateDto(1, "Test Fauna", "Descripción", true, 999, List.of(1));

        when(faunaMapper.toEntityUpdate(any())).thenReturn(new Fauna());
        when(usuarioService.findById(999)).thenReturn(null);

        mockMvc.perform(put("/api/v3/faunas/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void uploadFileTest_NotFoundFauna() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "missing.jpg", "image/jpeg", "data".getBytes());

        when(fotoManagementService.save(any(), any())).thenReturn("missing.jpg");
        when(faunaService.findById(999)).thenReturn(null);

        mockMvc.perform(multipart("/api/v3/faunas/upload/999")
                        .file(file)
                        .param("id", "999"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteFaunaTest_NotFound() throws Exception {
        when(faunaService.findById(999)).thenReturn(null);

        mockMvc.perform(delete("/api/v3/faunas/delete/999"))
                .andExpect(status().isNotFound());
    }


}
