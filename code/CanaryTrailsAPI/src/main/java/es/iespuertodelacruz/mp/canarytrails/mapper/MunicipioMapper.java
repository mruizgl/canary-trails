package es.iespuertodelacruz.mp.canarytrails.mapper;


import es.iespuertodelacruz.mp.canarytrails.dto.municipio.MunicipioEntradaDto;
import es.iespuertodelacruz.mp.canarytrails.dto.municipio.MunicipioSalidaDto;
import es.iespuertodelacruz.mp.canarytrails.entities.Municipio;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = { ZonaMapper.class })
public interface MunicipioMapper {

    @Mapping(source = "zona", target = "zona")
    MunicipioSalidaDto toDto(Municipio municipio);

    @Mapping(target = "zona", ignore = true)
    @Mapping(target = "rutas", ignore = true)
    Municipio toEntity(MunicipioEntradaDto dto);
}
