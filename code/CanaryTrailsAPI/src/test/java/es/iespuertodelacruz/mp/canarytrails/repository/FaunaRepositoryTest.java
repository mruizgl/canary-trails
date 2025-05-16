package es.iespuertodelacruz.mp.canarytrails.repository;

import es.iespuertodelacruz.mp.canarytrails.entities.Fauna;
import es.iespuertodelacruz.mp.canarytrails.entities.Ruta;
import es.iespuertodelacruz.mp.canarytrails.entities.Usuario;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@DataJpaTest
public class FaunaRepositoryTest {

    @Autowired
    private FaunaRepository faunaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RutaRepository rutaRepository;

    @BeforeEach
    void setUp() {
        //crreo usuario
        Usuario usuario = new Usuario();
        usuario.setNombre("Juan");
        usuario.setApellidos("Pérez");
        usuario.setCorreo("juan@example.com");
        usuario.setPassword("1234");
        usuario.setRol("usuario");
        usuario.setVerificado(true);
        usuario = usuarioRepository.save(usuario); // Guardar y recuperar con ID asignado

        //creo fauna y le asigno el mismo usuario
        Fauna fauna = new Fauna();
        fauna.setNombre("Lagarto Gigante");
        fauna.setDescripcion("Especie endémica de Canarias");
        fauna.setAprobada(true);
        fauna.setUsuario(usuario); // 👈 obligatorio
        fauna = faunaRepository.save(fauna);

        // Creo ruta asigno usuario
        Ruta ruta = new Ruta();
        ruta.setNombre("Ruta del Teide");
        ruta.setDificultad("Alta");
        ruta.setTiempoDuracion(180L);
        ruta.setDistanciaMetros(5000f);
        ruta.setDesnivel(800f);
        ruta.setAprobada(true);
        ruta.setUsuario(usuario);
        ruta = rutaRepository.save(ruta);

        //relacion fauna con ruta
        faunaRepository.addRutaFaunaRelation(fauna.getId(), ruta.getId());
    }


    @Test
    void addRutaFaunaRelationTest() {
        Fauna fauna = faunaRepository.findAll().get(0);
        Ruta ruta = rutaRepository.findAll().get(0);

        assertDoesNotThrow(() -> faunaRepository.addRutaFaunaRelation(fauna.getId(), ruta.getId()));
    }

    @Test
    void deleteRutaFaunaRelationTest() {
        Fauna fauna = faunaRepository.findAll().get(0);

        int deletedCount = faunaRepository.deleteRutaFaunaRelation(fauna.getId());
        assertTrue(deletedCount >= 0);
    }

    @Test
    @Transactional
    void deleteFaunaByIdTest() {
        Fauna fauna = faunaRepository.findAll().get(0);

        // elimino la relacinn primero.
        faunaRepository.deleteRutaFaunaRelation(fauna.getId());

        // elimino fauna
        int deleted = faunaRepository.deleteFaunaById(fauna.getId());

        assertEquals(1, deleted);
    }

}

