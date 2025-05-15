package es.iespuertodelacruz.mp.canarytrails.mapper;

import es.iespuertodelacruz.mp.canarytrails.dto.flora.FloraEntradaCreateDto;
import es.iespuertodelacruz.mp.canarytrails.dto.flora.FloraEntradaUpdateDto;
import es.iespuertodelacruz.mp.canarytrails.dto.flora.FloraSalidaDto;
import es.iespuertodelacruz.mp.canarytrails.dto.ruta.FloraSalidaRutaDto;
import es.iespuertodelacruz.mp.canarytrails.dto.usuario.FloraSalidaUsuarioDto;
import es.iespuertodelacruz.mp.canarytrails.entities.Flora;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(
        componentModel = "spring",
        uses = {
                RelacionesMapper.class
        }
)
public interface FloraMapper {
    //FloraMapper INSTANCE = Mappers.getMapper(FloraMapper.class);

    FloraSalidaDto toDTO(Flora flora);

    @Mapping(target = "usuario", ignore = true)
    @Mapping(target = "rutas", ignore = true)
    Flora toEntityCreate(FloraEntradaCreateDto dto);

    @Mapping(target = "usuario", ignore = true)
    @Mapping(target = "rutas", ignore = true)
    Flora toEntityUpdate(FloraEntradaUpdateDto dto);

    List<FloraSalidaDto> toDTOList(List<Flora> floraList);
    //List<Flora> toEntityList(List<FloraDTO> floraDTOList);

    //  <--- Relaciones Dto's ---->
    FloraSalidaRutaDto toSalidaRutaDto(Flora entity);
    FloraSalidaUsuarioDto toSalidaUsuarioDto(Flora entity);
}
