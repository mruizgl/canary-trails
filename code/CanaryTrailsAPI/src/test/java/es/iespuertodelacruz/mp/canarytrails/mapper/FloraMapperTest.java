package es.iespuertodelacruz.mp.canarytrails.mapper;

import es.iespuertodelacruz.mp.canarytrails.dto.flora.*;
import es.iespuertodelacruz.mp.canarytrails.dto.ruta.FloraSalidaRutaDto;
import es.iespuertodelacruz.mp.canarytrails.dto.usuario.FloraSalidaUsuarioDto;
import es.iespuertodelacruz.mp.canarytrails.entities.Flora;
import es.iespuertodelacruz.mp.canarytrails.entities.Ruta;
import es.iespuertodelacruz.mp.canarytrails.entities.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class FloraMapperTest {

    @Autowired
    FloraMapper floraMapper;

    private FloraMapperImpl mapper;
    private RelacionesMapper relacionesMapper;

    @BeforeEach
    void setUp() {
        mapper = new FloraMapperImpl();
        relacionesMapper = new RelacionesMapperImpl();
    }

    @Test
    void toDTO_nullFlora_returnsNullTest() {
        assertNull(mapper.toDTO(null));
    }


    @Test
    void toEntityCreate_nullDto_returnsNullTest() {
        assertNull(mapper.toEntityCreate(null));
    }

    @Test
    void toEntityCreate_validDto_mapsCorrectlyTest() {
        FloraEntradaCreateDto dto = new FloraEntradaCreateDto("Cardón", "Euphorbia", "Carnosa", "Primavera", "Verano", "Endemismo", true, 1, List.of());
        Flora flora = mapper.toEntityCreate(dto);
        assertEquals("Cardón", flora.getNombre());
    }

    @Test
    void toEntityUpdate_nullDto_returnsNullTest() {
        assertNull(mapper.toEntityUpdate(null));
    }

    @Test
    void toEntityUpdate_validDto_mapsCorrectlyTest() {
        FloraEntradaUpdateDto dto = new FloraEntradaUpdateDto(1, "Cardón", "Euphorbia", "Carnosa", "Primavera", "Verano", "Endemismo", true, 1, List.of());
        Flora flora = mapper.toEntityUpdate(dto);
        assertEquals(1, flora.getId());
        assertTrue(flora.getAprobada());
    }

    @Test
    void toDTOList_nullList_returnsNullTest() {
        assertNull(mapper.toDTOList(null));
    }


    @Test
    void toSalidaRutaDto_validFlora_mapsCorrectlyTest() {
        Flora flora = new Flora();
        flora.setId(1);
        flora.setNombre("Cardón");
        flora.setEspecie("Euphorbia");
        flora.setTipoHoja("Carnosa");
        flora.setSalidaFlor("Primavera");
        flora.setCaidaFlor("Verano");
        flora.setDescripcion("Endemismo");
        flora.setAprobada(true);

        FloraSalidaRutaDto dto = mapper.toSalidaRutaDto(flora);
        assertEquals("Cardón", dto.nombre());
    }

    @Test
    void rutaListToRutaSalidaFloraDtoList_null_returnsNullTest() {
        assertNull(mapper.rutaListToRutaSalidaFloraDtoList(null));
    }

    @Test
    void toSalidaUsuarioDto_validFlora_mapsCorrectlyTest() {
        FloraMapperImpl mapper = new FloraMapperImpl();

        Flora flora = new Flora();
        flora.setId(1);
        flora.setNombre("Lavanda");
        flora.setEspecie("Lavandula");
        flora.setTipoHoja("Lineal");
        flora.setSalidaFlor("Primavera");
        flora.setCaidaFlor("Verano");
        flora.setDescripcion("Aroma agradable");
        flora.setAprobada(true);

        FloraSalidaUsuarioDto dto = mapper.toSalidaUsuarioDto(flora);

        assertEquals(1, dto.id());
        assertEquals("Lavanda", dto.nombre());
        assertEquals("Lavandula", dto.especie());
        assertEquals("Lineal", dto.tipoHoja());
        assertEquals("Primavera", dto.salidaFlor());
        assertEquals("Verano", dto.caidaFlor());
        assertEquals("Aroma agradable", dto.descripcion());
        assertTrue(dto.aprobada());
    }

    @Test
    void toSalidaUsuarioDto_nullFlora_returnsNullTest() {
        FloraMapperImpl mapper = new FloraMapperImpl();
        assertNull(mapper.toSalidaUsuarioDto(null));
    }

    @Test
    void toDTO_validFlora_mapsCorrectlyTest() {
        Usuario usuario = new Usuario();
        usuario.setId(1);
        usuario.setNombre("María");

        Ruta ruta = new Ruta();
        ruta.setId(2);
        ruta.setNombre("Monteverde");

        Flora flora = new Flora();
        flora.setId(10);
        flora.setNombre("Cardón");
        flora.setEspecie("Euphorbia canariensis");
        flora.setTipoHoja("Carnosa");
        flora.setSalidaFlor("Primavera");
        flora.setCaidaFlor("Verano");
        flora.setDescripcion("Planta endémica de Canarias");
        flora.setAprobada(true);
        flora.setUsuario(usuario);
        flora.setRutas(List.of(ruta));

        FloraSalidaDto dto = floraMapper.toDTO(flora);

        assertNotNull(dto);
        assertEquals("Cardón", dto.nombre());
        assertEquals("Euphorbia canariensis", dto.especie());
        assertEquals("Carnosa", dto.tipoHoja());
        assertEquals("Primavera", dto.salidaFlor());
        assertEquals("Verano", dto.caidaFlor());
        assertEquals("Planta endémica de Canarias", dto.descripcion());
        assertTrue(dto.aprobada());
        assertNotNull(dto.usuario());
        assertEquals("María", dto.usuario().nombre());
        assertEquals(1, dto.rutas().size());
        assertEquals("Monteverde", dto.rutas().get(0).nombre());
    }

    @Test
    void toDTOList_validList_mapsCorrectlyTest() {
        Usuario usuario = new Usuario();
        usuario.setId(1);
        usuario.setNombre("Ana");

        Ruta ruta = new Ruta();
        ruta.setId(2);
        ruta.setNombre("Bosque Nuboso");

        Flora flora1 = new Flora();
        flora1.setId(10);
        flora1.setNombre("Cardón");
        flora1.setEspecie("Euphorbia");
        flora1.setTipoHoja("Carnosa");
        flora1.setSalidaFlor("Primavera");
        flora1.setCaidaFlor("Verano");
        flora1.setDescripcion("Planta canaria");
        flora1.setAprobada(true);
        flora1.setUsuario(usuario);
        flora1.setRutas(List.of(ruta));

        Flora flora2 = new Flora();
        flora2.setId(11);
        flora2.setNombre("Tajinaste");
        flora2.setEspecie("Echium wildpretii");
        flora2.setTipoHoja("Alargada");
        flora2.setSalidaFlor("Primavera");
        flora2.setCaidaFlor("Verano");
        flora2.setDescripcion("Flor del Teide");
        flora2.setAprobada(true);
        flora2.setUsuario(usuario);
        flora2.setRutas(List.of(ruta));

        List<FloraSalidaDto> result = floraMapper.toDTOList(List.of(flora1, flora2));

        assertEquals(2, result.size());
        assertEquals("Cardón", result.get(0).nombre());
        assertEquals("Tajinaste", result.get(1).nombre());
    }


}