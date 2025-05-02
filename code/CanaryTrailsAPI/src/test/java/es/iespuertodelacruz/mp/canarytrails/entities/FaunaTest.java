package es.iespuertodelacruz.mp.canarytrails.entities;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;


class FaunaTest {

    private Fauna fauna;
    private Usuario usuario;
    private Ruta ruta;

    @BeforeEach
    void setUp() {
        fauna = new Fauna();
        fauna.setId(1);
        fauna.setNombre("Lagarto Gigante de El Hierro");
        fauna.setDescripcion("Especie endémica de El Hierro");
        fauna.setAprobada(true);

        usuario = new Usuario();
        usuario.setId(10);
        fauna.setUsuario(usuario);

        ruta = new Ruta();
        ruta.setId(5);
        fauna.setRutas(List.of(ruta));
    }

    @Test
    void testGetters() {
        assertEquals(1, fauna.getId());
        assertEquals("Lagarto Gigante de El Hierro", fauna.getNombre());
        assertEquals("Especie endémica de El Hierro", fauna.getDescripcion());
        assertTrue(fauna.getAprobada());
        assertEquals(usuario, fauna.getUsuario());
        assertEquals(1, fauna.getRutas().size());
    }

    @Test
    void testEqualsAndHashCode() {
        Fauna fauna2 = new Fauna();
        fauna2.setId(1);

        assertEquals(fauna, fauna2);
        assertEquals(fauna.hashCode(), fauna2.hashCode());

        fauna2.setId(2);
        assertNotEquals(fauna, fauna2);
    }

    @Test
    void testToString() {
        String output = fauna.toString();
        assertTrue(output.contains("Lagarto Gigante"));
        assertTrue(output.contains("usuario="));
        assertTrue(output.contains("rutas="));
    }
}
