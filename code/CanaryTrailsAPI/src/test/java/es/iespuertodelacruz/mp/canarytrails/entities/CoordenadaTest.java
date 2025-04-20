package es.iespuertodelacruz.mp.canarytrails.entities;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CoordenadaTest {

    @Test
    void equalsShouldReturnTrueForSameId() {
        Coordenada coordenada1 = new Coordenada();
        coordenada1.setId(1);
        Coordenada coordenada2 = new Coordenada();
        coordenada2.setId(1);

        assertEquals(coordenada1, coordenada2);
    }

    @Test
    void equalsShouldReturnFalseForDifferentId() {
        Coordenada coordenada1 = new Coordenada();
        coordenada1.setId(1);
        Coordenada coordenada2 = new Coordenada();
        coordenada2.setId(2);

        assertNotEquals(coordenada1, coordenada2);
    }

    @Test
    void equalsShouldReturnFalseForNull() {
        Coordenada coordenada = new Coordenada();
        coordenada.setId(1);

        assertNotEquals(coordenada, null);
    }

    @Test
    void hashCodeShouldBeConsistentWithEquals() {
        Coordenada coordenada1 = new Coordenada();
        coordenada1.setId(1);
        Coordenada coordenada2 = new Coordenada();
        coordenada2.setId(1);

        assertEquals(coordenada1.hashCode(), coordenada2.hashCode());
    }

    @Test
    void toStringShouldIncludeAllAttributes() {
        Coordenada coordenada = new Coordenada();
        coordenada.setId(1);
        coordenada.setLatitud(new BigDecimal("28.123456"));
        coordenada.setLongitud(new BigDecimal("-16.654321"));

        String result = coordenada.toString();

        assertTrue(result.contains("id=1"));
        assertTrue(result.contains("latitud=28.123456"));
        assertTrue(result.contains("longitud=-16.654321"));
    }

    @Test
    void gettersAndSettersShouldWorkCorrectly() {
        Coordenada coordenada = new Coordenada();
        coordenada.setId(1);
        coordenada.setLatitud(new BigDecimal("28.123456"));
        coordenada.setLongitud(new BigDecimal("-16.654321"));

        assertEquals(1, coordenada.getId());
        assertEquals(new BigDecimal("28.123456"), coordenada.getLatitud());
        assertEquals(new BigDecimal("-16.654321"), coordenada.getLongitud());
    }

    @Test
    void rutasGetterAndSetterShouldHandleNull() {
        Coordenada coordenada = new Coordenada();
        coordenada.setRutas(null);

        assertNull(coordenada.getRutas());
    }

    @Test
    void rutasGetterAndSetterShouldHandleEmptyList() {
        Coordenada coordenada = new Coordenada();
        coordenada.setRutas(List.of());

        assertTrue(coordenada.getRutas().isEmpty());
    }
}
