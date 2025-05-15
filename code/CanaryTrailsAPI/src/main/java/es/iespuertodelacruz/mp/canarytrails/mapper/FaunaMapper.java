package es.iespuertodelacruz.mp.canarytrails.mapper;

import es.iespuertodelacruz.mp.canarytrails.dto.fauna.FaunaEntradaCreateDto;
import es.iespuertodelacruz.mp.canarytrails.dto.fauna.FaunaEntradaUpdateDto;
import es.iespuertodelacruz.mp.canarytrails.dto.fauna.FaunaSalidaDto;
import es.iespuertodelacruz.mp.canarytrails.entities.Fauna;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring",
        uses = {
                RelacionesMapper.class
        }
)
public interface FaunaMapper {
    //FaunaMapper INSTANCE = Mappers.getMapper(FaunaMapper.class);


    FaunaSalidaDto toDto(Fauna entity);

    @Mapping(target = "usuario", ignore = true)
    @Mapping(target = "rutas", ignore = true)
    Fauna toEntityCreate(FaunaEntradaCreateDto dto);

    @Mapping(target = "usuario", ignore = true)
    @Mapping(target = "rutas", ignore = true)
    Fauna toEntityUpdate(FaunaEntradaUpdateDto dto);
}
