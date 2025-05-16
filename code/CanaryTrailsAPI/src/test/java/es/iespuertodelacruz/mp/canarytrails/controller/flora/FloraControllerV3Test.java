package es.iespuertodelacruz.mp.canarytrails.controller.flora;

import com.fasterxml.jackson.databind.ObjectMapper;
import es.iespuertodelacruz.mp.canarytrails.config.TestSecurityConfig;
import es.iespuertodelacruz.mp.canarytrails.controller.v3.FloraControllerV3;
import es.iespuertodelacruz.mp.canarytrails.dto.flora.*;
import es.iespuertodelacruz.mp.canarytrails.entities.Flora;
import es.iespuertodelacruz.mp.canarytrails.entities.Ruta;
import es.iespuertodelacruz.mp.canarytrails.entities.Usuario;
import es.iespuertodelacruz.mp.canarytrails.mapper.FloraMapper;
import es.iespuertodelacruz.mp.canarytrails.mapper.RelacionesMapper;
import es.iespuertodelacruz.mp.canarytrails.mapper.RutaMapper;
import es.iespuertodelacruz.mp.canarytrails.service.*;
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
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(FloraControllerV3.class)
@Import(TestSecurityConfig.class)
public class FloraControllerV3Test {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    FloraService floraService;

    @MockBean
    FloraMapper floraMapper;

    @MockBean
    UsuarioService usuarioService;

    @MockBean
    RutaService rutaService;

    @MockBean
    FotoManagementService fotoManagementService;

    @MockBean
    RutaMapper rutaMapper;

    @MockBean
    RelacionesMapper resMapper;

    private List<RutaSalidaFloraDto> listaRutas;
    private Usuario usuario;
    private Flora flora;
    private FloraSalidaDto floraSalidaDto;
    private FloraEntradaUpdateDto floraEntradaUpdateDto;
    private UsuarioSalidaFloraDto usuarioSalidaFloraDto;
    private Ruta ruta1;
    private Ruta ruta2;

    @BeforeEach
    public void beforeEach() {
        usuarioSalidaFloraDto = new UsuarioSalidaFloraDto(1, "Nombre", "Apellidos",
                "email","password", true, "ADMIN");


        ruta1 = new Ruta();
        ruta1.setId(1);
        ruta1.setNombre("Ruta de los volcanes");

        ruta2 = new Ruta();
        ruta2.setId(2);
        ruta2.setNombre("Sendero de los bosques");

        listaRutas = new ArrayList<>();
        listaRutas.add(resMapper.toSalidaFloraDto(ruta1));
        listaRutas.add(resMapper.toSalidaFloraDto(ruta2));

        usuario = new Usuario();
        usuario.setId(1);
        usuario.setNombre("Admin");

        flora = new Flora();
        flora.setId(1);
        flora.setNombre("Rosa canaria");

         floraSalidaDto = new FloraSalidaDto(1, "Rosa canaria", "Rosa",
                "tipoHoja", "Invierno", null, null, true, usuarioSalidaFloraDto, listaRutas
        );

        floraEntradaUpdateDto = new FloraEntradaUpdateDto(1, "Lagarto Gigante", "Reptil",
                "tipohojaupdate", "salidaflorupdate", "caidaflorupdate",
                 "descripcionupdate", true, 1, List.of(1)
        );

    }

    @Test
    public void findAllFlorasTest() throws Exception {
        when(floraService.findAll()).thenReturn(List.of(flora));
        when(floraMapper.toDTOList(anyList())).thenReturn(List.of(floraSalidaDto));

        mockMvc.perform(get("/api/v3/floras"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Rosa canaria"));
    }

    @Test
    void shouldReturnFloraById() throws Exception {

        FloraSalidaDto dto = new FloraSalidaDto(1, "Rosa canaria", "Flor endémica",
                "tipohoja", "salidaflor", "caidaflor", "descripcion", true,
                usuarioSalidaFloraDto, listaRutas);

        when(floraService.findById(1)).thenReturn(flora);
        when(floraMapper.toDTO(flora)).thenReturn(dto);

        mockMvc.perform(get("/api/v3/floras/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Rosa canaria"))
                .andExpect(jsonPath("$.especie").value("Flor endémica"))
                .andExpect(jsonPath("$.tipoHoja").value("tipohoja"))
                .andExpect(jsonPath("$.salidaFlor").value("salidaflor"))
                .andExpect(jsonPath("$.caidaFlor").value("caidaflor"))
                .andExpect(jsonPath("$.descripcion").value("descripcion"));
    }


    @Test
    public void findFloraByIdTest_NotFound() throws Exception {
        when(floraService.findById(99)).thenReturn(null);

        mockMvc.perform(get("/api/v3/floras/99"))
                .andExpect(status().isNotFound());
    }


    @Test
    public void updateFloraTest() throws Exception {
        Ruta ruta = new Ruta();
        ruta.setId(1);
        ruta.setNombre("Ruta de los volcanes");

        when(floraMapper.toEntityUpdate(floraEntradaUpdateDto)).thenReturn(flora);
        when(usuarioService.findById(1)).thenReturn(usuario);
        when(rutaService.findById(1)).thenReturn(ruta);
        when(floraService.update(flora)).thenReturn(true);

        mockMvc.perform(put("/api/v3/floras/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(floraEntradaUpdateDto)))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    void updateFlora_shouldReturnBadRequestOnError() throws Exception {
        Ruta ruta = new Ruta();
        ruta.setId(1);
        ruta.setNombre("Ruta de los volcanes");

        when(floraMapper.toEntityUpdate(any())).thenReturn(new Flora());
        when(usuarioService.findById(1)).thenReturn(new Usuario());
        when(rutaService.findById(1)).thenReturn(new Ruta());
        when(floraService.update(any())).thenThrow(new RuntimeException("Error al actualizar"));

        mockMvc.perform(put("/api/v3/floras/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(floraEntradaUpdateDto)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Error al actualizar"));
    }



    @Test
    public void uploadFileTest() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "rosa.jpg", "image/jpeg", "data".getBytes());

        when(fotoManagementService.save(any(), any())).thenReturn("rosa.jpg");
        when(floraService.findById(1)).thenReturn(flora);
        when(floraService.update(any())).thenReturn(true);

        mockMvc.perform(multipart("/api/v3/floras/upload/{id}", 1)
                        .file(file)
                        .param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(content().string("rosa.jpg"));
    }

    @Test
    void uploadFile_shouldReturnBadRequestWhenUploadFails() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "file", "fallo.jpg", "image/jpeg", "contenido".getBytes());

        when(fotoManagementService.save(any(), any())).thenThrow(new RuntimeException("hola soy un error simulado"));

        mockMvc.perform(multipart("/api/v3/floras/upload/{id}", 1)
                        .file(file)
                        .param("id", "1"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("No se pudo subir el archivo: fallo.jpg")))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("hola soy un error simulado")));
    }


    @Test
    public void deleteFloraTest() throws Exception {
        when(floraService.findById(1)).thenReturn(new Flora());
        when(floraService.deleteById(1)).thenReturn(true);

        mockMvc.perform(delete("/api/v3/floras/delete/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    public void createFloraTest() throws Exception {
        List<Integer> idRutas = List.of(1);

        FloraEntradaCreateDto dto = new FloraEntradaCreateDto(
                "Rosa canaria", "Descripción", "tipohoja", "Primavera", "Invierno",
                "tipohojacreate", true, usuario.getId(), idRutas
        );

        Flora mockFlora = new Flora();
        mockFlora.setId(1);
        mockFlora.setNombre("Rosa canaria");

        FloraSalidaDto salidaDto = new FloraSalidaDto(1, "Rosa canaria", "Descripción",
                "tipohoja", "Primavera", "Invierno", "tipohojacreate", true, null, null);

        when(floraMapper.toEntityCreate(any())).thenReturn(mockFlora);
        when(usuarioService.findById(1)).thenReturn(new Usuario());
        when(rutaService.findById(1)).thenReturn(new Ruta());
        when(floraService.save(any())).thenReturn(mockFlora);
        when(floraMapper.toDTO(mockFlora)).thenReturn(salidaDto);

        mockMvc.perform(post("/api/v3/floras/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Rosa canaria"));
    }


    @Test
    void createFlora_shouldReturnBadRequestOnError() throws Exception {
        List<Integer> idRutas = List.of(1, 2);
        FloraEntradaCreateDto dto = new FloraEntradaCreateDto("Rosa canaria", "Flor endémica",
                "tipohojacreate", "salidaflorcreate", "Invierno", "caidaFlor", true,
                usuario.getId(), idRutas);

        when(floraMapper.toEntityCreate(any())).thenReturn(new Flora());
        when(usuarioService.findById(1)).thenReturn(new Usuario());
        when(rutaService.findById(1)).thenReturn(new Ruta());
        when(floraService.save(any())).thenThrow(new RuntimeException("Error al guardar"));

        mockMvc.perform(post("/api/v3/floras/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Error al guardar"));
    }


}
