package es.iespuertodelacruz.mp.canarytrails.repository;


import es.iespuertodelacruz.mp.canarytrails.entities.Flora;
import es.iespuertodelacruz.mp.canarytrails.entities.Ruta;
import es.iespuertodelacruz.mp.canarytrails.entities.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@DataJpaTest
public class FloraRepositoryTest {

    @Autowired
    private FloraRepository floraRepository;

    @Autowired
    private RutaRepository rutaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private Flora flora;
    private Ruta ruta;

    @BeforeEach
    void beforeEach() {
        Usuario usuario = new Usuario();
        usuario.setNombre("Test");
        usuario.setCorreo("test@example.com");
        usuario.setPassword("pass");
        usuario.setRol("USER");
        usuario = usuarioRepository.save(usuario);

        flora = new Flora();
        flora.setNombre("Flora test");
        flora.setEspecie("Especie test");
        flora.setTipoHoja("Hoja ancha");
        flora.setSalidaFlor("Primavera");
        flora.setCaidaFlor("Invierno");
        flora.setDescripcion("Una flora de prueba");
        flora.setUsuario(usuario);
        flora = floraRepository.save(flora);

        ruta = new Ruta();
        ruta.setNombre("Ruta test");
        ruta.setDificultad("Media");
        ruta.setDistanciaMetros(5000f);
        ruta.setDesnivel(300f);
        ruta.setTiempoDuracion(120L);
        ruta.setUsuario(usuario);
        ruta = rutaRepository.save(ruta);
    }

    @Test
    @Transactional
    void addAndDeleteRutaFloraRelationTest() {
        floraRepository.addRutaFloraRelation(flora.getId(), ruta.getId());

        int deleted = floraRepository.deleteRutaFloraRelation(flora.getId());
        assertThat(deleted).isEqualTo(1);
    }

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    @Transactional
    void deleteFloraByIdTest() {
        Integer id = flora.getId();

        int deleted = floraRepository.deleteFloraById(id);
        assertThat(deleted).isEqualTo(1);

        entityManager.flush();
        entityManager.clear();

        boolean exists = floraRepository.findById(id).isPresent();
        assertThat(exists).isFalse();
    }

}
