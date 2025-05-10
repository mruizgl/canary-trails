package es.iespuertodelacruz.mp.canarytrails.mapper;


import es.iespuertodelacruz.mp.canarytrails.dto.municipio.MunicipioEntradaDto;
import es.iespuertodelacruz.mp.canarytrails.dto.municipio.MunicipioSalidaDto;
import es.iespuertodelacruz.mp.canarytrails.entities.Municipio;
import es.iespuertodelacruz.mp.canarytrails.dto.municipio.MunicipioEdicionDto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { ZonaMapper.class })
public interface MunicipioMapper {

    @Mapping(source = "zona", target = "zona")
    MunicipioSalidaDto toDto(Municipio municipio);

    @Mapping(target = "zona", ignore = true)
    @Mapping(target = "rutas", ignore = true)
    Municipio toEntity(MunicipioEntradaDto dto);

    @Mapping(target = "zona", ignore = true)
    @Mapping(target = "rutas", ignore = true)
    Municipio toEntity(MunicipioEdicionDto dto); // ✅ necesario para el update
}
