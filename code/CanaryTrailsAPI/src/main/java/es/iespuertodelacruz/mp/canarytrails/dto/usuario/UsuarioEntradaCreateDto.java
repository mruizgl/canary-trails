package es.iespuertodelacruz.mp.canarytrails.dto.usuario;

import java.util.List;

public record UsuarioEntradaCreateDto (
        String nombre,
        String correo,
        String password,
        boolean verificado,
        String rol
){
}
