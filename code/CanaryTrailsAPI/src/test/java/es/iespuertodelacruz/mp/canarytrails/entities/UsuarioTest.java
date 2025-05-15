package es.iespuertodelacruz.mp.canarytrails.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UsuarioTest {

    /*@Test
    void equalsShouldReturnTrueForSameId() {
        Usuario usuario1 = new Usuario();
        usuario1.setId(1);
        Usuario usuario2 = new Usuario();
        usuario2.setId(1);

        assertEquals(usuario1, usuario2);
    }

    @Test
    void equalsShouldReturnFalseForDifferentId() {
        Usuario usuario1 = new Usuario();
        usuario1.setId(1);
        Usuario usuario2 = new Usuario();
        usuario2.setId(2);

        assertNotEquals(usuario1, usuario2);
    }

    @Test
    void equalsShouldReturnFalseForNull() {
        Usuario usuario = new Usuario();
        usuario.setId(1);

        assertNotEquals(usuario, null);
    }

    @Test
    void equalsShouldReturnFalseForDifferentClass() {
        Usuario usuario = new Usuario();
        usuario.setId(1);

        assertNotEquals(usuario, new Object());
    }

    @Test
    void hashCodeShouldBeConsistentWithEquals() {
        Usuario usuario1 = new Usuario();
        usuario1.setId(1);
        Usuario usuario2 = new Usuario();
        usuario2.setId(1);

        assertEquals(usuario1.hashCode(), usuario2.hashCode());
    }

    @Test
    void hashCodeShouldReturnDifferentValuesForDifferentIds() {
        Usuario usuario1 = new Usuario();
        usuario1.setId(1);
        Usuario usuario2 = new Usuario();
        usuario2.setId(2);

        assertNotEquals(usuario1.hashCode(), usuario2.hashCode());
    }

    @Test
    void toStringShouldIncludeAllAttributes() {
        Usuario usuario = new Usuario();
        usuario.setId(1);
        usuario.setNombre("John");
        usuario.setApellidos("Doe");
        usuario.setCorreo("john.doe@example.com");
        usuario.setPassword("password");
        usuario.setVerificado(true);
        usuario.setRol("ADMIN");

        String result = usuario.toString();

        assertTrue(result.contains("id=1"));
        assertTrue(result.contains("nombre='John'"));
        assertTrue(result.contains("apellidos='Doe'"));
        assertTrue(result.contains("correo='john.doe@example.com'"));
        assertTrue(result.contains("contraseña='password'"));
        assertTrue(result.contains("verificado=true"));
        assertTrue(result.contains("rol='ADMIN'"));
    }



    @Test
    void gettersAndSettersShouldWorkCorrectly() {
        Usuario usuario = new Usuario();
        usuario.setId(1);
        usuario.setNombre("John");
        usuario.setApellidos("Doe");
        usuario.setCorreo("john.doe@example.com");
        usuario.setPassword("password");
        usuario.setVerificado(true);
        usuario.setRol("ADMIN");

        assertEquals(1, usuario.getId());
        assertEquals("John", usuario.getNombre());
        assertEquals("Doe", usuario.getApellidos());
        assertEquals("john.doe@example.com", usuario.getCorreo());
        assertEquals("password", usuario.getPassword());
        assertTrue(usuario.getVerificado());
        assertEquals("ADMIN", usuario.getRol());
    }

    @Test
    void gettersAndSettersShouldHandleNullValues() {
        Usuario usuario = new Usuario();
        usuario.setId(null);
        usuario.setNombre(null);
        usuario.setApellidos(null);
        usuario.setCorreo(null);
        usuario.setPassword(null);
        usuario.setVerificado(null);
        usuario.setRol(null);

        assertNull(usuario.getId());
        assertNull(usuario.getNombre());
        assertNull(usuario.getApellidos());
        assertNull(usuario.getCorreo());
        assertNull(usuario.getPassword());
        assertNull(usuario.getVerificado());
        assertNull(usuario.getRol());
    }*/
