package es.iespuertodelacruz.mp.canarytrails.dto.usuario;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioSalidaDtoV2Test {

    @Test
    void testUsuarioSalidaDtoV2_CreacionYLectura() {
        FaunaSalidaUsuarioDto fauna = new FaunaSalidaUsuarioDto(1, "Lagarto Gigante", "Descripción", true);
        FloraSalidaUsuarioDto flora = new FloraSalidaUsuarioDto(2, "Drago", "Dracaena", "lanceolada", "Primavera", "Verano", "Descripción", true);
        RutaSalidaUsuarioDto ruta = new RutaSalidaUsuarioDto(3, "Ruta Norte", "media", 120, 6.0f, 300f, true);

        UsuarioSalidaDtoV2 dto = new UsuarioSalidaDtoV2(
                10,
                "Melissa",
                "Ruiz",
                true,
                "ROLE_ADMIN",
                List.of(fauna),
                List.of(flora),
                List.of(ruta),
                "foto.png"
        );

        assertEquals(10, dto.id());
        assertEquals("Melissa", dto.nombre());
        assertEquals("Ruiz", dto.apellidos());
        assertTrue(dto.verificado());
        assertEquals("ROLE_ADMIN", dto.rol());
        assertEquals(1, dto.faunas().size());
        assertEquals(1, dto.floras().size());
        assertEquals(1, dto.rutas().size());
        assertEquals("foto.png", dto.foto());
    }

    @Test
    void testUsuarioSalidaDtoV2_ListasVacias() {
        UsuarioSalidaDtoV2 dto = new UsuarioSalidaDtoV2(
                1, "Test", "User", false, "ROLE_USER",
                List.of(), List.of(), List.of(), null
        );

        assertNotNull(dto.faunas());
        assertNotNull(dto.floras());
        assertNotNull(dto.rutas());
        assertNull(dto.foto());
        assertEquals(0, dto.faunas().size());
    }

    @Test
    void testUsuarioSalidaDtoV2_ListasNulas_Permitidas() {
        UsuarioSalidaDtoV2 dto = new UsuarioSalidaDtoV2(
                1, "Test", "User", false, "ROLE_USER",
                null, null, null, "foto.jpg"
        );

        assertNull(dto.faunas());
        assertNull(dto.floras());
        assertNull(dto.rutas());
        assertEquals("foto.jpg", dto.foto());
    }
}
