package es.iespuertodelacruz.mp.canarytrails.mapper;

import es.iespuertodelacruz.mp.canarytrails.dto.usuario.UsuarioEntradaCreateDto;
import es.iespuertodelacruz.mp.canarytrails.dto.usuario.UsuarioEntradaUpdateDto;
import es.iespuertodelacruz.mp.canarytrails.entities.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UsuarioMapperTest {

    private UsuarioMapperImpl mapper;

    @BeforeEach
    void setup() {
        mapper = new UsuarioMapperImpl();
    }

    @Test
    void toEntityCreate_nullDto_returnsNullTest() {
        assertNull(mapper.toEntityCreate(null));
    }

    @Test
    void toEntityCreate_validDto_mapsCorrectlyTest() {
        UsuarioEntradaCreateDto dto = new UsuarioEntradaCreateDto(
                "Pepe", "García", "pepe@email.com", "1234", true, "ADMIN"
        );

        Usuario usuario = mapper.toEntityCreate(dto);

        assertEquals("Pepe", usuario.getNombre());
        assertEquals("García", usuario.getApellidos());
        assertEquals("pepe@email.com", usuario.getCorreo());
        assertEquals("1234", usuario.getPassword());
        assertTrue(usuario.getVerificado());
        assertEquals("ADMIN", usuario.getRol());
    }

    @Test
    void toEntityUpdate_nullDto_returnsNullTest() {
        assertNull(mapper.toEntityUpdate(null));
    }

    @Test
    void toEntityUpdate_validDto_mapsCorrectlyTest() {
        UsuarioEntradaUpdateDto dto = new UsuarioEntradaUpdateDto(
                1, "Ana", "Suárez", "ana@email.com", "5678", true, "USER"
        );

        Usuario usuario = mapper.toEntityUpdate(dto);

        assertEquals(1, usuario.getId());
        assertEquals("Ana", usuario.getNombre());
        assertEquals("Suárez", usuario.getApellidos());
        assertEquals("ana@email.com", usuario.getCorreo());
        assertEquals("5678", usuario.getPassword());
        assertTrue(usuario.getVerificado());
        assertEquals("USER", usuario.getRol());
    }
}
