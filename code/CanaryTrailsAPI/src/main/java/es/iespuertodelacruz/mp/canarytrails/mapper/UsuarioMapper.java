package es.iespuertodelacruz.mp.canarytrails.mapper;

import es.iespuertodelacruz.mp.canarytrails.dto.fauna.UsuarioSalidaFaunaDto;
import es.iespuertodelacruz.mp.canarytrails.dto.ruta.UsuarioSalidaRutaDto;
import es.iespuertodelacruz.mp.canarytrails.dto.usuario.UsuarioEntradaDto;
import es.iespuertodelacruz.mp.canarytrails.dto.usuario.UsuarioSalidaDto;
import es.iespuertodelacruz.mp.canarytrails.entities.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    //UsuarioMapper INSTANCE = Mappers.getMapper(UsuarioMapper.class);

    Usuario toEntity(UsuarioEntradaDto dto);
    UsuarioSalidaDto toDto(Usuario entity);

    // <--- Dto's --->
    UsuarioSalidaRutaDto toSalidaRutaDto(Usuario entity);

    UsuarioSalidaFaunaDto toSalidaFaunaDto(Usuario entity);
}
