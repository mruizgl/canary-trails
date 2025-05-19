package es.iespuertodelacruz.mp.canarytrails.dto.comentario;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ComentarioSalidaDtoV1Test {

    @Test
    void testCreacionYLectura() {
        ComentarioSalidaDtoV1 dto = new ComentarioSalidaDtoV1(1, "Buen sitio", "Una ruta espectacular", "Lorena");

        assertEquals(1, dto.id());
        assertEquals("Buen sitio", dto.titulo());
        assertEquals("Una ruta espectacular", dto.descripcion());
        assertEquals("Lorena", dto.nombreUsuario());
    }

    @Test
    void testValoresNulosPermitidos() {
        ComentarioSalidaDtoV1 dto = new ComentarioSalidaDtoV1(null, null, null, null);

        assertNull(dto.id());
        assertNull(dto.titulo());
        assertNull(dto.descripcion());
        assertNull(dto.nombreUsuario());
    }

    @Test
    void testEqualsYHashCode() {
        ComentarioSalidaDtoV1 dto1 = new ComentarioSalidaDtoV1(1, "Título", "Desc", "Usuario");
        ComentarioSalidaDtoV1 dto2 = new ComentarioSalidaDtoV1(1, "Título", "Desc", "Usuario");

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    void testToString() {
        ComentarioSalidaDtoV1 dto = new ComentarioSalidaDtoV1(1, "Titulo", "Texto", "Autor");
        String result = dto.toString();
        assertTrue(result.contains("ComentarioSalidaDtoV1"));
        assertTrue(result.contains("1"));
        assertTrue(result.contains("Titulo"));
    }
}
