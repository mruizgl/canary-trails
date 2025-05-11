package es.iespuertodelacruz.mp.canarytrails.mapper;

import es.iespuertodelacruz.mp.canarytrails.dto.fauna.FaunaEntradaCreateDto;
import es.iespuertodelacruz.mp.canarytrails.dto.fauna.FaunaEntradaUpdateDto;
import es.iespuertodelacruz.mp.canarytrails.dto.fauna.FaunaSalidaDto;
import es.iespuertodelacruz.mp.canarytrails.dto.ruta.FaunaSalidaRutaDto;
import es.iespuertodelacruz.mp.canarytrails.dto.usuario.FaunaSalidaUsuarioDto;
import es.iespuertodelacruz.mp.canarytrails.entities.Fauna;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.context.annotation.Lazy;

@Mapper(
        componentModel = "spring",
        uses = {
                //UsuarioMapper.class
                //RutaMapper.class
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

    //  <--- Relaciones Dto's --->
    FaunaSalidaRutaDto toSalidaRutaDto(Fauna entity);
    FaunaSalidaUsuarioDto toSalidaUsuarioDto(Fauna entity);
}
