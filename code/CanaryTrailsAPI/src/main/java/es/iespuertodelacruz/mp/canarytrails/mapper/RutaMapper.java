package es.iespuertodelacruz.mp.canarytrails.mapper;

import es.iespuertodelacruz.mp.canarytrails.dto.ruta.RutaEntradaCreateDto;
import es.iespuertodelacruz.mp.canarytrails.dto.ruta.RutaEntradaUpdateDto;
import es.iespuertodelacruz.mp.canarytrails.dto.ruta.RutaSalidaDto;
import es.iespuertodelacruz.mp.canarytrails.entities.Ruta;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring",
        uses = {
            RelacionesMapper.class
        }
)
public interface RutaMapper {

    RutaSalidaDto toDto(Ruta entity);

    @Mapping(target = "usuario", ignore = true)
    @Mapping(target = "municipios", ignore = true)
    @Mapping(target = "faunas", ignore = true)
    @Mapping(target = "floras", ignore = true)
    @Mapping(target = "coordenadas", ignore = true)
    Ruta toEntityCreate(RutaEntradaCreateDto dto);

    @Mapping(target = "usuario", ignore = true)
    @Mapping(target = "municipios", ignore = true)
    @Mapping(target = "faunas", ignore = true)
    @Mapping(target = "floras", ignore = true)
    @Mapping(target = "coordenadas", ignore = true)
    @Mapping(target = "comentarios", ignore = true)
    Ruta toEntityUpdate(RutaEntradaUpdateDto dto);

}
