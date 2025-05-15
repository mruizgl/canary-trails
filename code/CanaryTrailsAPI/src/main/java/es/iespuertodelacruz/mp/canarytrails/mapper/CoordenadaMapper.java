package es.iespuertodelacruz.mp.canarytrails.mapper;

import es.iespuertodelacruz.mp.canarytrails.dto.coordenada.CoordenadaEntradaCreateDto;
import es.iespuertodelacruz.mp.canarytrails.dto.coordenada.CoordenadaEntradaUpdateDto;
import es.iespuertodelacruz.mp.canarytrails.dto.coordenada.CoordenadaSalidaDto;
import es.iespuertodelacruz.mp.canarytrails.dto.ruta.CoordenadaSalidaRutaDto;
import es.iespuertodelacruz.mp.canarytrails.entities.Coordenada;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(
        componentModel = "spring",
        uses = {
                RelacionesMapper.class
        }
)
public interface CoordenadaMapper {

    CoordenadaSalidaDto toDTO(Coordenada coordenada);

    Coordenada toEntityCreate(CoordenadaEntradaCreateDto dto);
    Coordenada toEntityUpdate(CoordenadaEntradaUpdateDto dto);

    List<CoordenadaSalidaDto> toDTOList(List<Coordenada> coordenadas);
    //List<Coordenada> toEntityList(List<CoordenadaDTO> coordenadasDto);

    // <--- Relaciones Dto's --->
    CoordenadaSalidaRutaDto toSalidaRutaDto(Coordenada entity);
}
