package es.iespuertodelacruz.mp.canarytrails.repository;

import es.iespuertodelacruz.mp.canarytrails.entities.Zona;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

@ActiveProfiles("test")
@DataJpaTest
public class ZonaRepositoryTest {

   /* @Autowired
    private ZonaRepository zonaRepository;

    @Test
    void testGuardarYBuscarZona() {
        Zona zona = new Zona();
        zona.setNombre("Norte");
        zona = zonaRepository.save(zona);

        Optional<Zona> encontrada = zonaRepository.findById(zona.getId());

        assertTrue(encontrada.isPresent());
        assertEquals("Norte", encontrada.get().getNombre());
    }*/
}
