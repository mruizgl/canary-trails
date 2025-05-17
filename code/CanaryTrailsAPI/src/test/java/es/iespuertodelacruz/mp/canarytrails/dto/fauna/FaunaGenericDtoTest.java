package es.iespuertodelacruz.mp.canarytrails.dto.fauna;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class FaunaGenericDtoTest {

    private static String MESSAGE_ERROR = "No se ha obtenido el resultado esperado";
    @Test
    void faunaEntradaCreateDtoTest() {
        FaunaEntradaCreateDto dto = new FaunaEntradaCreateDto(
                "nombre", "descripcion", true, 1, List.of(1)
        );

        Assertions.assertEquals("nombre", dto.nombre(), MESSAGE_ERROR);
        Assertions.assertEquals("descripcion", dto.descripcion(), MESSAGE_ERROR);
        Assertions.assertTrue(dto.aprobada(), MESSAGE_ERROR);
        Assertions.assertEquals(1, dto.usuario(), MESSAGE_ERROR);
        Assertions.assertEquals(List.of(1), dto.rutas(), MESSAGE_ERROR);
    }

    @Test
    void faunaEntradaUpdateDtoTest() {
        FaunaEntradaUpdateDto dto = new FaunaEntradaUpdateDto(
                1, "nombre", "descripcion", true, 1, List.of(1)
        );

        Assertions.assertEquals(1, dto.id(), MESSAGE_ERROR);
        Assertions.assertEquals("nombre", dto.nombre(), MESSAGE_ERROR);
        Assertions.assertEquals("descripcion", dto.descripcion(), MESSAGE_ERROR);
        Assertions.assertTrue(dto.aprobada(), MESSAGE_ERROR);
        Assertions.assertEquals(1, dto.usuario(), MESSAGE_ERROR);
        Assertions.assertEquals(List.of(1), dto.rutas(), MESSAGE_ERROR);
    }

    @Test
    void faunaSalidaDtoTest() {
        UsuarioSalidaFaunaDto usuarioDto = new UsuarioSalidaFaunaDto(1, "Nombre de Usuario", null, null,
                 true, null);
        RutaSalidaFaunaDto rutaDto = new RutaSalidaFaunaDto(1, "Ruta de prueba", null, 10L,
                0f, 0f, true);
        List<RutaSalidaFaunaDto> rutas = List.of(rutaDto);

        FaunaSalidaDto dto = new FaunaSalidaDto(1, "Lagarto Gigante", "Reptil endémico",
                true, usuarioDto, rutas, "foto.png");

        Assertions.assertEquals(1, dto.id(), MESSAGE_ERROR);
        Assertions.assertEquals("Lagarto Gigante", dto.nombre(), MESSAGE_ERROR);
        Assertions.assertEquals("Reptil endémico", dto.descripcion(), MESSAGE_ERROR);
        Assertions.assertTrue(dto.aprobada(), MESSAGE_ERROR);
        Assertions.assertEquals("foto.png", dto.foto(), MESSAGE_ERROR);
        Assertions.assertEquals(usuarioDto, dto.usuario(), MESSAGE_ERROR);
        Assertions.assertEquals(rutas, dto.rutas(), MESSAGE_ERROR);
    }


    @Test
    void usuarioSalidaFaunaDtoTest() {
        UsuarioSalidaFaunaDto dto = new UsuarioSalidaFaunaDto(
                1, "nombre", "correo", "pass", true, "admin");

        Assertions.assertEquals(1, dto.id(), MESSAGE_ERROR);
        Assertions.assertEquals("nombre", dto.nombre(), MESSAGE_ERROR);

        Assertions.assertEquals("correo", dto.correo(), MESSAGE_ERROR);
        Assertions.assertEquals("pass", dto.password(), MESSAGE_ERROR);
        Assertions.assertTrue(dto.verificado(), MESSAGE_ERROR);
        Assertions.assertEquals("admin", dto.rol(), MESSAGE_ERROR);
    }

    @Test
    void rutaSalidaFaunaDtoTest() {
        RutaSalidaFaunaDto dto = new RutaSalidaFaunaDto(1, "ruta", "dificultad", 100L,
                50f, 1f, true);

        Assertions.assertEquals(1, dto.id(), MESSAGE_ERROR);
        Assertions.assertEquals("ruta", dto.nombre(), MESSAGE_ERROR);
        Assertions.assertEquals("dificultad", dto.dificultad(), MESSAGE_ERROR);
        Assertions.assertEquals(100L, dto.tiempoDuracion(), MESSAGE_ERROR);
        Assertions.assertEquals(50f, dto.distanciaMetros(), MESSAGE_ERROR);
        Assertions.assertEquals(50f, dto.distanciaMetros(), MESSAGE_ERROR);
        Assertions.assertTrue(dto.aprobada(), MESSAGE_ERROR);
    }
}
