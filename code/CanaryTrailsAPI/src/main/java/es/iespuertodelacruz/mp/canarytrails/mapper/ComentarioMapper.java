package es.iespuertodelacruz.mp.canarytrails.mapper;

import es.iespuertodelacruz.mp.canarytrails.dto.comentario.ComentarioEntradaCreateDto;
import es.iespuertodelacruz.mp.canarytrails.dto.comentario.ComentarioEntradaUpdateDto;
import es.iespuertodelacruz.mp.canarytrails.dto.comentario.ComentarioSalidaDto;
import es.iespuertodelacruz.mp.canarytrails.dto.ruta.ComentarioSalidaRutaDto;
import es.iespuertodelacruz.mp.canarytrails.dto.usuario.ComentarioSalidaUsuarioDto;
import es.iespuertodelacruz.mp.canarytrails.entities.Comentario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring",
        uses = {
                RelacionesMapper.class
        }
)
public interface ComentarioMapper {

    ComentarioSalidaDto toDto(Comentario comentario);

    @Mapping(target = "ruta", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    Comentario toEntityCreate(ComentarioEntradaCreateDto dto);

    @Mapping(target = "ruta", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    Comentario toEntityUpdate(ComentarioEntradaUpdateDto dto);

}
