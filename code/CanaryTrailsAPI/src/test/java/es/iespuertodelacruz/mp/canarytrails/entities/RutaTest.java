package es.iespuertodelacruz.mp.canarytrails.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RutaTest {

    /*@Test
    void equalsShouldReturnTrueForSameId() {
        Ruta ruta1 = new Ruta();
        ruta1.setId(1);
        Ruta ruta2 = new Ruta();
        ruta2.setId(1);

        assertEquals(ruta1, ruta2);
    }

    @Test
    void equalsShouldReturnFalseForDifferentId() {
        Ruta ruta1 = new Ruta();
        ruta1.setId(1);
        Ruta ruta2 = new Ruta();
        ruta2.setId(2);

        assertNotEquals(ruta1, ruta2);
    }

    @Test
    void equalsShouldReturnFalseForNull() {
        Ruta ruta = new Ruta();
        ruta.setId(1);

        assertNotEquals(ruta, null);
    }

    @Test
    void hashCodeShouldBeConsistentWithEquals() {
        Ruta ruta1 = new Ruta();
        ruta1.setId(1);
        Ruta ruta2 = new Ruta();
        ruta2.setId(1);

        assertEquals(ruta1.hashCode(), ruta2.hashCode());
    }

    @Test
    void hashCodeShouldReturnDifferentValuesForDifferentIds() {
        Ruta ruta1 = new Ruta();
        ruta1.setId(1);
        Ruta ruta2 = new Ruta();
        ruta2.setId(2);

        assertNotEquals(ruta1.hashCode(), ruta2.hashCode());
    }

    @Test
    void toStringShouldIncludeAllAttributes() {
        Ruta ruta = new Ruta();
        ruta.setId(1);
        ruta.setNombre("Ruta de prueba");
        ruta.setDificultad("Media");
        ruta.setTiempoDuracion(120L);
        ruta.setDistanciaMetros(5000.0f);
        ruta.setDesnivel(300.0f);
        ruta.setAprobada(true);

        String result = ruta.toString();

        assertTrue(result.contains("id=1"));
        assertTrue(result.contains("dificultad=Media"));
        assertTrue(result.contains("tiempoDuracion=120"));
        assertTrue(result.contains("distanciaMetros=5000.0"));
        assertTrue(result.contains("desnivel=300.0"));
        assertTrue(result.contains("aprobada=true"));
    }

    @Test
    void toStringShouldHandleNullAttributes() {
        Ruta ruta = new Ruta();
        ruta.setId(null);
        ruta.setNombre(null);
        ruta.setDificultad(null);
        ruta.setTiempoDuracion(null);
        ruta.setDistanciaMetros(null);
        ruta.setDesnivel(null);
        ruta.setAprobada(null);

        String result = ruta.toString();

        assertTrue(result.contains("id=null"));
        assertTrue(result.contains("nombre=null"));
        assertTrue(result.contains("dificultad=null"));
        assertTrue(result.contains("tiempoDuracion=null"));
        assertTrue(result.contains("distanciaMetros=null"));
        assertTrue(result.contains("desnivel=null"));
        assertTrue(result.contains("aprobada=null"));
    }

    @Test
    void gettersAndSettersShouldWorkCorrectly() {
        Ruta ruta = new Ruta();
        ruta.setId(1);
        ruta.setNombre("Ruta de prueba");
        ruta.setDificultad("Media");
        ruta.setTiempoDuracion(120L);
        ruta.setDistanciaMetros(5000.0f);
        ruta.setDesnivel(300.0f);
        ruta.setAprobada(true);

        assertEquals(1, ruta.getId());
        assertEquals("Ruta de prueba", ruta.getNombre());
        assertEquals("Media", ruta.getDificultad());
        assertEquals(120L, ruta.getTiempoDuracion());
        assertEquals(5000.0f, ruta.getDistanciaMetros());
        assertEquals(300.0f, ruta.getDesnivel());
        assertTrue(ruta.getAprobada());
    }

    @Test
    void gettersAndSettersShouldHandleNullValues() {
        Ruta ruta = new Ruta();
        ruta.setId(null);
        ruta.setNombre(null);
        ruta.setDificultad(null);
        ruta.setTiempoDuracion(null);
        ruta.setDistanciaMetros(null);
        ruta.setDesnivel(null);
        ruta.setAprobada(null);

        assertNull(ruta.getId());
        assertNull(ruta.getNombre());
        assertNull(ruta.getDificultad());
        assertNull(ruta.getTiempoDuracion());
        assertNull(ruta.getDistanciaMetros());
        assertNull(ruta.getDesnivel());
        assertNull(ruta.getAprobada());
    }*/
}
