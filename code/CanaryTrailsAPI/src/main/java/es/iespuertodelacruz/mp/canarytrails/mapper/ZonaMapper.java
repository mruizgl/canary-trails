package es.iespuertodelacruz.mp.canarytrails.mapper;

import es.iespuertodelacruz.mp.canarytrails.dto.zona.ZonaSalidaDto;
import es.iespuertodelacruz.mp.canarytrails.entities.Zona;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ZonaMapper {
    ZonaMapper INSTANCE = Mappers.getMapper(ZonaMapper.class);

    ZonaSalidaDto toDTO(Zona zona);
    Zona toEntity(ZonaSalidaDto dto);
}