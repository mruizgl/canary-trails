package es.iespuertodelacruz.mp.canarytrails.mapper;

import es.iespuertodelacruz.mp.canarytrails.dto.CoordenadaDTO;
import es.iespuertodelacruz.mp.canarytrails.entities.Coordenada;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CoordenadaMapper {
    CoordenadaDTO toDTO(Coordenada coordenada);
    Coordenada toEntity(CoordenadaDTO dto);
    List<CoordenadaDTO> toDTOList(List<Coordenada> coordenadas);
    List<Coordenada> toEntityList(List<CoordenadaDTO> coordenadasDto);
}
