package es.iespuertodelacruz.mp.canarytrails.mapper;

import es.iespuertodelacruz.mp.canarytrails.dto.municipio.ZonaSalidaMunicipioDto;
import es.iespuertodelacruz.mp.canarytrails.dto.zona.ZonaEntradaCreateDto;
import es.iespuertodelacruz.mp.canarytrails.dto.zona.ZonaEntradaUpdateDto;
import es.iespuertodelacruz.mp.canarytrails.dto.zona.ZonaSalidaDto;
import es.iespuertodelacruz.mp.canarytrails.entities.Zona;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(
        componentModel = "spring",
        uses = {
                RelacionesMapper.class
        }
)
public interface ZonaMapper {
    //ZonaMapper INSTANCE = Mappers.getMapper(ZonaMapper.class);

    ZonaSalidaDto toDTO(Zona zona);

    @Mapping(target = "municipios", ignore = true)
    Zona toEntityCreate(ZonaEntradaCreateDto dto);
    @Mapping(target = "municipios", ignore = true)
    Zona toEntityUpdate(ZonaEntradaUpdateDto dto);

    List<ZonaSalidaDto> toDTOList(List<Zona> zonas);
}