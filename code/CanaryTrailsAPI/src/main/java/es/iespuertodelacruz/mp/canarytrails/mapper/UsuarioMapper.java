package es.iespuertodelacruz.mp.canarytrails.mapper;


import es.iespuertodelacruz.mp.canarytrails.dto.usuario.UsuarioEntradaCreateDto;
import es.iespuertodelacruz.mp.canarytrails.dto.usuario.UsuarioEntradaUpdateDto;
import es.iespuertodelacruz.mp.canarytrails.dto.usuario.UsuarioSalidaDto;
import es.iespuertodelacruz.mp.canarytrails.entities.Usuario;
import org.mapstruct.Mapper;

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
