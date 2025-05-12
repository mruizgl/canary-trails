package es.iespuertodelacruz.mp.canarytrails.mapper;

import es.iespuertodelacruz.mp.canarytrails.dto.comentario.ComentarioEntradaDto;
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
    Comentario toEntity(ComentarioEntradaDto dto);

    @Mapping(source = "usuario.nombre", target = "nombreUsuario")
    @Mapping(source = "ruta.nombre", target = "nombreRuta")
    ComentarioSalidaDto toDto(Comentario comentario);

    //  <--- Dto's --->
    ComentarioSalidaRutaDto toSalidaRutaDto(Comentario entity);
    ComentarioSalidaUsuarioDto toSalidaUsuarioDto(Comentario entity);
}
