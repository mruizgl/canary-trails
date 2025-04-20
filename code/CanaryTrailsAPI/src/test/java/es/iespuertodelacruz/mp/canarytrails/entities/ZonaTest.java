package es.iespuertodelacruz.mp.canarytrails.entities;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ZonaTest {
    @Test
    void equalsShouldReturnTrueForSameId() {
        Zona zona1 = new Zona();
        zona1.setId(1);
        Zona zona2 = new Zona();
        zona2.setId(1);

        assertEquals(zona1, zona2);
    }

    @Test
    void equalsShouldReturnFalseForDifferentId() {
        Zona zona1 = new Zona();
        zona1.setId(1);
        Zona zona2 = new Zona();
        zona2.setId(2);

        assertNotEquals(zona1, zona2);
    }

    @Test
    void equalsShouldReturnFalseForNull() {
        Zona zona = new Zona();
        zona.setId(1);

        assertNotEquals(zona, null);
    }

    @Test
    void hashCodeShouldBeConsistentWithEquals() {
        Zona zona1 = new Zona();
        zona1.setId(1);
        Zona zona2 = new Zona();
        zona2.setId(1);

        assertEquals(zona1.hashCode(), zona2.hashCode());
    }

    @Test
    void hashCodeShouldReturnDifferentValuesForDifferentIds() {
        Zona zona1 = new Zona();
        zona1.setId(1);
        Zona zona2 = new Zona();
        zona2.setId(2);

        assertNotEquals(zona1.hashCode(), zona2.hashCode());
    }

    @Test
    void toStringShouldIncludeAllAttributes() {
        Zona zona = new Zona();
        zona.setId(1);
        zona.setNombre("Zona de prueba");
        zona.setMunicipios(List.of());

        String result = zona.toString();

        assertTrue(result.contains("id=1"));
        assertTrue(result.contains("nombre='Zona de prueba'"));
        assertTrue(result.contains("municipios=[]"));
    }



    @Test
    void gettersAndSettersShouldWorkCorrectly() {
        Zona zona = new Zona();
        zona.setId(1);
        zona.setNombre("Zona de prueba");
        zona.setMunicipios(List.of());

        assertEquals(1, zona.getId());
        assertEquals("Zona de prueba", zona.getNombre());
        assertTrue(zona.getMunicipios().isEmpty());
    }

    @Test
    void gettersAndSettersShouldHandleNullValues() {
        Zona zona = new Zona();
        zona.setId(null);
        zona.setNombre(null);
        zona.setMunicipios(null);

        assertNull(zona.getId());
        assertNull(zona.getNombre());
        assertNull(zona.getMunicipios());
    }

    @Test
    void municipiosGetterAndSetterShouldHandleEmptyList() {
        Zona zona = new Zona();
        zona.setMunicipios(List.of());

        assertTrue(zona.getMunicipios().isEmpty());
    }

}
