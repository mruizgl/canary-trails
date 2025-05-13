package es.iespuertodelacruz.mp.canarytrails.mapper;

import es.iespuertodelacruz.mp.canarytrails.dto.comentario.ComentarioEntradaCreateDto;
import es.iespuertodelacruz.mp.canarytrails.dto.comentario.ComentarioEntradaUpdateDto;
import es.iespuertodelacruz.mp.canarytrails.dto.comentario.ComentarioSalidaDto;
import es.iespuertodelacruz.mp.canarytrails.dto.ruta.ComentarioSalidaRutaDto;
import es.iespuertodelacruz.mp.canarytrails.dto.usuario.ComentarioSalidaUsuarioDto;
import es.iespuertodelacruz.mp.canarytrails.entities.Comentario;
import org.mapstruct.Mapper;

@Mapper(
        componentModel = "spring",
        uses = {
                RelacionesMapper.class
        }
)
public interface ComentarioMapper {

    ComentarioSalidaDto toDto(Comentario comentario);

    Comentario toEntityCreate(ComentarioEntradaCreateDto dto);
    Comentario toEntityUpdate(ComentarioEntradaUpdateDto dto);

}
