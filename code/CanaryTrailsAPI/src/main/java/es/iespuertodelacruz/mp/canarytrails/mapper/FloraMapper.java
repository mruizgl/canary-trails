package es.iespuertodelacruz.mp.canarytrails.mapper;

import es.iespuertodelacruz.mp.canarytrails.dto.FloraDTO;
import es.iespuertodelacruz.mp.canarytrails.entities.Flora;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FloraMapper {
    FloraMapper INSTANCE = Mappers.getMapper(FloraMapper.class);

    FloraDTO toDTO(Flora flora);
    Flora toEntity(FloraDTO dto);
    List<FloraDTO> toDTOList(List<Flora> floraList);
    List<Flora> toEntityList(List<FloraDTO> floraDTOList);
}
