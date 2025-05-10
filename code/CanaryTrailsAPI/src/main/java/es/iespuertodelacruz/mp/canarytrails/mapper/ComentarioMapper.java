package es.iespuertodelacruz.mp.canarytrails.mapper;

import es.iespuertodelacruz.mp.canarytrails.dto.comentario.ComentarioEntradaDto;
import es.iespuertodelacruz.mp.canarytrails.dto.comentario.ComentarioSalidaDto;
import es.iespuertodelacruz.mp.canarytrails.entities.Comentario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ComentarioMapper {
    Comentario toEntity(ComentarioEntradaDto dto);

    @Mapping(source = "usuario.nombre", target = "nombreUsuario")
    @Mapping(source = "ruta.nombre", target = "nombreRuta")
    ComentarioSalidaDto toDto(Comentario comentario);
}
