package es.iespuertodelacruz.mp.canarytrails.controller.flora;

import com.fasterxml.jackson.databind.ObjectMapper;
import es.iespuertodelacruz.mp.canarytrails.config.TestSecurityConfig;
import es.iespuertodelacruz.mp.canarytrails.controller.v2.FloraControllerV2;
import es.iespuertodelacruz.mp.canarytrails.controller.v3.FloraControllerV3;
import es.iespuertodelacruz.mp.canarytrails.dto.flora.*;
import es.iespuertodelacruz.mp.canarytrails.entities.Comentario;
import es.iespuertodelacruz.mp.canarytrails.entities.Flora;
import es.iespuertodelacruz.mp.canarytrails.entities.Ruta;
import es.iespuertodelacruz.mp.canarytrails.entities.Usuario;
import es.iespuertodelacruz.mp.canarytrails.mapper.FloraMapper;
import es.iespuertodelacruz.mp.canarytrails.mapper.RelacionesMapper;
import es.iespuertodelacruz.mp.canarytrails.mapper.RutaMapper;
import es.iespuertodelacruz.mp.canarytrails.security.JwtFilter;
import es.iespuertodelacruz.mp.canarytrails.service.FloraService;
import es.iespuertodelacruz.mp.canarytrails.service.FotoManagementService;
import es.iespuertodelacruz.mp.canarytrails.service.RutaService;
import es.iespuertodelacruz.mp.canarytrails.service.UsuarioService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(controllers = FloraControllerV2.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtFilter.class) //excluyi jwtfilter
})
@Import(TestSecurityConfig.class)
public class FloraControllerV2Test {

    FloraControllerV2 floraControllerV2 = new FloraControllerV2();

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
        usuarioSalidaFloraDto = new UsuarioSalidaFloraDto(1, "Nombre",
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
        //El usuario no puede tener null

        flora = new Flora();
        flora.setId(1);
        flora.setNombre("Rosa canaria");

        floraSalidaDto = new FloraSalidaDto(1, "Rosa canaria", "Rosa",
                "tipoHoja", "Invierno", null, null, true, usuarioSalidaFloraDto, listaRutas, "foto"
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

        mockMvc.perform(get("/api/v2/floras"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Rosa canaria"));
    }

    @Test
    void shouldReturnFloraById() throws Exception {

        FloraSalidaDto dto = new FloraSalidaDto(1, "Rosa canaria", "Flor endémica",
                "tipohoja", "salidaflor", "caidaflor", "descripcion", true,
                usuarioSalidaFloraDto, listaRutas, "foto");

        when(floraService.findById(1)).thenReturn(flora);
        when(floraMapper.toDTO(flora)).thenReturn(dto);

        mockMvc.perform(get("/api/v2/floras/1")
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

        mockMvc.perform(get("/api/v2/floras/99"))
                .andExpect(status().isNotFound());
    }

    /*@Test
    void updateFlora_shouldReturnBadRequestOnError() throws Exception {
        Ruta ruta = new Ruta();
        ruta.setId(1);
        ruta.setNombre("Ruta de los volcanes");

        when(floraMapper.toEntityUpdate(any())).thenReturn(new Flora());
        when(usuarioService.findById(1)).thenReturn(new Usuario());
        when(rutaService.findById(1)).thenReturn(new Ruta());
        when(floraService.update(any())).thenThrow(new RuntimeException("Error al actualizar"));

        mockMvc.perform(put("/api/v2/floras/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(floraEntradaUpdateDto)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Error al actualizar"));
    }*/



    /*@Test
    public void uploadFileTest() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "rosa.jpg", "image/jpeg", "data".getBytes());

        when(fotoManagementService.save(any(), any())).thenReturn("rosa.jpg");
        when(floraService.findById(1)).thenReturn(flora);
        when(floraService.update(any())).thenReturn(true);

        mockMvc.perform(multipart("/api/v2/floras/upload/{id}", 1)
                        .file(file)
                        .param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(content().string("rosa.jpg"));
    }*/

    @Test
    void uploadFile_shouldReturnBadRequestWhenUploadFails() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "file", "fallo.jpg", "image/jpeg", "contenido".getBytes());

        when(fotoManagementService.save(any(), any())).thenThrow(new RuntimeException("hola soy un error simulado"));

        mockMvc.perform(multipart("/api/v2/floras/upload/{id}", 1)
                        .file(file)
                        .param("id", "1"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("No se pudo subir el archivo: fallo.jpg")))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("hola soy un error simulado")));
    }


    /*@Test
    public void deleteFloraTest() throws Exception {
        when(floraService.findById(1)).thenReturn(flora);
        when(floraService.deleteById(1)).thenReturn(true);
        //When usuario find by id, return el usuario creador de la flora

        mockMvc.perform(delete("/api/v2/floras/delete/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }*/


    @Test
    public void createFloraTest() throws Exception {
        List<Integer> idRutas = List.of(1, 2);

        FloraEntradaCreateDto dto = new FloraEntradaCreateDto("Rosa canaria", "Flor endémica",
                "tipohojacreate", "salidaflorcreate", "Invierno", "caidaFlor", true,
                usuario.getId(), idRutas);

        when(floraMapper.toEntityCreate(any())).thenReturn(flora);
        when(rutaService.findById(1)).thenReturn(ruta1);
        when(rutaService.findById(2)).thenReturn(ruta2);
        when(floraService.save(any())).thenReturn(flora);
        when(floraMapper.toDTO(flora)).thenReturn(floraSalidaDto);

        mockMvc.perform(post("/api/v2/floras/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(floraSalidaDto.id()))
                .andExpect(jsonPath("$.nombre").value(floraSalidaDto.nombre()));
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

        mockMvc.perform(post("/api/v2/floras/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Error al guardar"));
    }


    @Test
    public void testEsPropietario_True() {

        //simulamso usuario autenticado
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("Juan", null)
        );

        Usuario usuario = new Usuario();
        usuario.setNombre("Juan");

        flora = new Flora();
        flora.setUsuario(usuario);


        Assertions.assertTrue(floraControllerV2.esPropietario(flora));
    }

    @Test
    public void testEsPropietario_False() {
        //simulamos que maria esta autenticada
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("Maria", null)
        );

        Usuario usuario = new Usuario();
        usuario.setNombre("Juan");

        flora = new Flora();
        flora.setUsuario(usuario);
        //ponemos el comentario a nombre de juan, asi que no es el propietario de la persona autenticada.. debe ser false
        Assertions.assertFalse(floraControllerV2.esPropietario(flora));
    }
}
