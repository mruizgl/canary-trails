package es.iespuertodelacruz.mp.canarytrails.entities;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FloraTest {

    @Test
    void equalsShouldReturnTrueForSameId() {
        Flora flora1 = new Flora();
        flora1.setId(1);
        Flora flora2 = new Flora();
        flora2.setId(1);

        assertEquals(flora1, flora2);
    }

    @Test
    void equalsShouldReturnFalseForDifferentId() {
        Flora flora1 = new Flora();
        flora1.setId(1);
        Flora flora2 = new Flora();
        flora2.setId(2);

        assertNotEquals(flora1, flora2);
    }

    @Test
    void equalsShouldReturnFalseForNull() {
        Flora flora = new Flora();
        flora.setId(1);

        assertNotEquals(flora, null);
    }

    @Test
    void hashCodeShouldBeConsistentWithEquals() {
        Flora flora1 = new Flora();
        flora1.setId(1);
        Flora flora2 = new Flora();
        flora2.setId(1);

        assertEquals(flora1.hashCode(), flora2.hashCode());
    }

    @Test
    void toStringShouldIncludeAllAttributes() {
        Flora flora = new Flora();
        flora.setId(1);
        flora.setNombre("Flora de prueba");
        flora.setEspecie("Especie de prueba");
        flora.setTipoHoja("Tipo de hoja");
        flora.setSalidaFlor("Primavera");
        flora.setCaidaFlor("Otoño");
        flora.setDescripcion("Descripción de prueba");
        flora.setAprobada(true);

        String result = flora.toString();

        assertTrue(result.contains("id=1"));
        assertTrue(result.contains("nombre='Flora de prueba'"));
        assertTrue(result.contains("especie='Especie de prueba'"));
        assertTrue(result.contains("tipoHoja='Tipo de hoja'"));
        assertTrue(result.contains("salidaFlor='Primavera'"));
        assertTrue(result.contains("caidaFlor='Otoño'"));
        assertTrue(result.contains("descripcion='Descripción de prueba'"));
        assertTrue(result.contains("aprobada=true"));
    }

    @Test
    void gettersAndSettersShouldWorkCorrectly() {
        Flora flora = new Flora();
        flora.setId(1);
        flora.setNombre("Flora de prueba");
        flora.setEspecie("Especie de prueba");
        flora.setTipoHoja("Tipo de hoja");
        flora.setSalidaFlor("Primavera");
        flora.setCaidaFlor("Otoño");
        flora.setDescripcion("Descripción de prueba");
        flora.setAprobada(true);

        assertEquals(1, flora.getId());
        assertEquals("Flora de prueba", flora.getNombre());
        assertEquals("Especie de prueba", flora.getEspecie());
        assertEquals("Tipo de hoja", flora.getTipoHoja());
        assertEquals("Primavera", flora.getSalidaFlor());
        assertEquals("Otoño", flora.getCaidaFlor());
        assertEquals("Descripción de prueba", flora.getDescripcion());
        assertTrue(flora.getAprobada());
    }

    @Test
    void usuariosGetterAndSetterShouldHandleNull() {
        Flora flora = new Flora();
        flora.setUsuarios(null);

        assertNull(flora.getUsuarios());
    }

    @Test
    void usuariosGetterAndSetterShouldHandleEmptyList() {
        Flora flora = new Flora();
        flora.setUsuarios(List.of());

        assertTrue(flora.getUsuarios().isEmpty());
    }

    @Test
    void rutasGetterAndSetterShouldHandleNull() {
        Flora flora = new Flora();
        flora.setRutas(null);

        assertNull(flora.getRutas());
    }

    @Test
    void rutasGetterAndSetterShouldHandleEmptyList() {
        Flora flora = new Flora();
        flora.setRutas(List.of());

        assertTrue(flora.getRutas().isEmpty());
    }
}