package es.iespuertodelacruz.mp.canarytrails.mapper;

import es.iespuertodelacruz.mp.canarytrails.dto.fauna.UsuarioSalidaFaunaDto;
import es.iespuertodelacruz.mp.canarytrails.dto.ruta.UsuarioSalidaRutaDto;
import es.iespuertodelacruz.mp.canarytrails.dto.usuario.UsuarioEntradaCreateDto;
import es.iespuertodelacruz.mp.canarytrails.dto.usuario.UsuarioEntradaDto;
import es.iespuertodelacruz.mp.canarytrails.dto.usuario.UsuarioEntradaUpdateDto;
import es.iespuertodelacruz.mp.canarytrails.dto.usuario.UsuarioSalidaDto;
import es.iespuertodelacruz.mp.canarytrails.entities.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.control.MappingControl;
import org.mapstruct.factory.Mappers;

@Mapper(
        componentModel = "spring",
        uses = {
                RelacionesMapper.class
        }
)
public interface UsuarioMapper {


    UsuarioSalidaDto toDto(Usuario entity);

    Usuario toEntityCreate(UsuarioEntradaCreateDto dto);
    Usuario toEntityUpdate(UsuarioEntradaUpdateDto dto);


}
