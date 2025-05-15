package es.iespuertodelacruz.mp.canarytrails.mapper;


import es.iespuertodelacruz.mp.canarytrails.dto.municipio.MunicipioEntradaCreateDto;
import es.iespuertodelacruz.mp.canarytrails.dto.municipio.MunicipioSalidaDto;
import es.iespuertodelacruz.mp.canarytrails.entities.Municipio;
import es.iespuertodelacruz.mp.canarytrails.dto.municipio.MunicipioEntradaUpdateDto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring",
        uses = {
                RelacionesMapper.class
        }
)
public interface MunicipioMapper {

    MunicipioSalidaDto toDto(Municipio municipio);

    @Mapping(target = "rutas", ignore = true)
    @Mapping(target = "zona", ignore = true)
    Municipio toEntityCreate(MunicipioEntradaCreateDto dto);

    @Mapping(target = "rutas", ignore = true)
    @Mapping(target = "zona", ignore = true)
    Municipio toEntityUpdate(MunicipioEntradaUpdateDto dto);
}
