package es.iespuertodelacruz.mp.canarytrails.entities;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FaunaTest {

    @Test
    void equalsShouldReturnTrueForSameId() {
        Fauna fauna1 = new Fauna();
        fauna1.setId(1);
        Fauna fauna2 = new Fauna();
        fauna2.setId(1);

        assertEquals(fauna1, fauna2);
    }

    @Test
    void equalsShouldReturnFalseForDifferentId() {
        Fauna fauna1 = new Fauna();
        fauna1.setId(1);
        Fauna fauna2 = new Fauna();
        fauna2.setId(2);

        assertNotEquals(fauna1, fauna2);
    }

    @Test
    void equalsShouldReturnFalseForNull() {
        Fauna fauna = new Fauna();
        fauna.setId(1);

        assertNotEquals(fauna, null);
    }

    @Test
    void hashCodeShouldBeConsistentWithEquals() {
        Fauna fauna1 = new Fauna();
        fauna1.setId(1);
        Fauna fauna2 = new Fauna();
        fauna2.setId(1);

        assertEquals(fauna1.hashCode(), fauna2.hashCode());
    }

    @Test
    void toStringShouldIncludeAllAttributes() {
        Fauna fauna = new Fauna();
        fauna.setId(1);
        fauna.setNombre("Fauna de prueba");
        fauna.setDescripcion("Descripción de prueba");
        fauna.setAprobada(true);

        String result = fauna.toString();

        assertTrue(result.contains("id=1"));
        assertTrue(result.contains("nombre='Fauna de prueba'"));
        assertTrue(result.contains("descripcion='Descripción de prueba'"));
        assertTrue(result.contains("aprobada=true"));
    }

    @Test
    void gettersAndSettersShouldWorkCorrectly() {
        Fauna fauna = new Fauna();
        fauna.setId(1);
        fauna.setNombre("Fauna de prueba");
        fauna.setDescripcion("Descripción de prueba");
        fauna.setAprobada(true);

        assertEquals(1, fauna.getId());
        assertEquals("Fauna de prueba", fauna.getNombre());
        assertEquals("Descripción de prueba", fauna.getDescripcion());
        assertTrue(fauna.getAprobada());
    }



    @Test
    void rutasGetterAndSetterShouldHandleNull() {
        Fauna fauna = new Fauna();
        fauna.setRutas(null);

        assertNull(fauna.getRutas());
    }

    @Test
    void rutasGetterAndSetterShouldHandleEmptyList() {
        Fauna fauna = new Fauna();
        fauna.setRutas(List.of());

        assertTrue(fauna.getRutas().isEmpty());
    }
}
