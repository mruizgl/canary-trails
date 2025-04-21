package es.iespuertodelacruz.mp.canarytrails.entities;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MunicipioTest {

    @Test
    void equalsShouldReturnTrueForSameId() {
        Municipio municipio1 = new Municipio();
        municipio1.setId(1);
        Municipio municipio2 = new Municipio();
        municipio2.setId(1);

        assertEquals(municipio1, municipio2);
    }

    @Test
    void equalsShouldReturnFalseForDifferentId() {
        Municipio municipio1 = new Municipio();
        municipio1.setId(1);
        Municipio municipio2 = new Municipio();
        municipio2.setId(2);

        assertNotEquals(municipio1, municipio2);
    }

    @Test
    void equalsShouldReturnFalseForNull() {
        Municipio municipio = new Municipio();
        municipio.setId(1);

        assertNotEquals(municipio, null);
    }

    @Test
    void hashCodeShouldBeConsistentWithEquals() {
        Municipio municipio1 = new Municipio();
        municipio1.setId(1);
        Municipio municipio2 = new Municipio();
        municipio2.setId(1);

        assertEquals(municipio1.hashCode(), municipio2.hashCode());
    }

    @Test
    void toStringShouldIncludeAllAttributes() {
        Municipio municipio = new Municipio();
        municipio.setId(1);
        municipio.setNombre("Municipio de prueba");
        municipio.setAltitudMedia(500);
        municipio.setLatitudGeografica(new BigDecimal("28.123456"));
        municipio.setLongitudGeografica(new BigDecimal("-16.654321"));
        municipio.setZona(new Zona());
        municipio.setRutas(List.of());

        String result = municipio.toString();

        assertTrue(result.contains("id=1"));
        assertTrue(result.contains("nombre='Municipio de prueba'"));
        assertTrue(result.contains("altitudMedia=500"));
        assertTrue(result.contains("latitudGeografica=28.123456"));
        assertTrue(result.contains("longitudGeografica=-16.654321"));
        assertTrue(result.contains("zona="));
        assertTrue(result.contains("rutas=[]"));
    }

    @Test
    void gettersAndSettersShouldWorkCorrectly() {
        Municipio municipio = new Municipio();
        municipio.setId(1);
        municipio.setNombre("Municipio de prueba");
        municipio.setAltitudMedia(500);
        municipio.setLatitudGeografica(new BigDecimal("28.123456"));
        municipio.setLongitudGeografica(new BigDecimal("-16.654321"));
        Zona zona = new Zona();
        municipio.setZona(zona);
        municipio.setRutas(List.of());

        assertEquals(1, municipio.getId());
        assertEquals("Municipio de prueba", municipio.getNombre());
        assertEquals(500, municipio.getAltitudMedia());
        assertEquals(new BigDecimal("28.123456"), municipio.getLatitudGeografica());
        assertEquals(new BigDecimal("-16.654321"), municipio.getLongitudGeografica());
        assertEquals(zona, municipio.getZona());
        assertTrue(municipio.getRutas().isEmpty());
    }

    @Test
    void rutasGetterAndSetterShouldHandleNull() {
        Municipio municipio = new Municipio();
        municipio.setRutas(null);

        assertNull(municipio.getRutas());
    }

    @Test
    void rutasGetterAndSetterShouldHandleEmptyList() {
        Municipio municipio = new Municipio();
        municipio.setRutas(List.of());

        assertTrue(municipio.getRutas().isEmpty());
    }
}
