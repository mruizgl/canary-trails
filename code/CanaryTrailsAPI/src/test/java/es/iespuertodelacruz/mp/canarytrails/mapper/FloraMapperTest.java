package es.iespuertodelacruz.mp.canarytrails.mapper;

import es.iespuertodelacruz.mp.canarytrails.dto.FloraDTO;
import es.iespuertodelacruz.mp.canarytrails.entities.Flora;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FloraMapperTest {

    private final FloraMapper mapper = Mappers.getMapper(FloraMapper.class);

    @Test
    void testEntityToDto() {
        Flora flora = new Flora();
        flora.setId(1);
        flora.setNombre("Tajinaste");
        flora.setEspecie("Echium wildpretii");

        FloraDTO dto = mapper.toDTO(flora);
        assertEquals("Tajinaste", dto.getNombre());
        assertEquals("Echium wildpretii", dto.getEspecie());
    }

    @Test
    void testDtoToEntity() {
        FloraDTO dto = new FloraDTO(1, "Tajinaste", "Echium wildpretii", "Lanceolada", "Primavera", "Verano", "Endémica", true);
        Flora flora = mapper.toEntity(dto);
        assertEquals("Tajinaste", flora.getNombre());
    }

    @Test
    void testToDTOList() {
        Flora flora = new Flora();
        flora.setNombre("Test");
        List<FloraDTO> dtos = mapper.toDTOList(Collections.singletonList(flora));
        assertEquals(1, dtos.size());
        assertEquals("Test", dtos.get(0).getNombre());
    }

    @Test
    void testToEntityList() {
        FloraDTO dto = new FloraDTO(1, "Pino Canario", "Pinus canariensis", "Aguja", "Primavera", "Otoño", "Árbol endémico", true);
        List<Flora> entities = mapper.toEntityList(Collections.singletonList(dto));
        assertEquals(1, entities.size());
        assertEquals("Pino Canario", entities.get(0).getNombre());
    }
}
