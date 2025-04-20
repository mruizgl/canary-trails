package es.iespuertodelacruz.mp.canarytrails.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ComentarioTest {

    @Test
    void equalsShouldReturnTrueForSameId() {
        Comentario comentario1 = new Comentario();
        comentario1.setId(1);
        Comentario comentario2 = new Comentario();
        comentario2.setId(1);

        assertEquals(comentario1, comentario2);
    }

    @Test
    void equalsShouldReturnFalseForDifferentId() {
        Comentario comentario1 = new Comentario();
        comentario1.setId(1);
        Comentario comentario2 = new Comentario();
        comentario2.setId(2);

        assertNotEquals(comentario1, comentario2);
    }

    @Test
    void equalsShouldReturnFalseForNull() {
        Comentario comentario = new Comentario();
        comentario.setId(1);

        assertNotEquals(comentario, null);
    }

    @Test
    void hashCodeShouldBeConsistentWithEquals() {
        Comentario comentario1 = new Comentario();
        comentario1.setId(1);
        Comentario comentario2 = new Comentario();
        comentario2.setId(1);

        assertEquals(comentario1.hashCode(), comentario2.hashCode());
    }

    @Test
    void toStringShouldIncludeAllAttributes() {
        Comentario comentario = new Comentario();
        comentario.setId(1);
        comentario.setTitulo("Titulo");
        comentario.setDescripcion("Descripcion");

        String result = comentario.toString();

        assertTrue(result.contains("id=1"));
        assertTrue(result.contains("titulo='Titulo'"));
        assertTrue(result.contains("descripcion='Descripcion'"));
    }
}
