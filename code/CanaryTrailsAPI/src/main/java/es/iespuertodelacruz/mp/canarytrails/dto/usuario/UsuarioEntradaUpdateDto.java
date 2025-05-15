package es.iespuertodelacruz.mp.canarytrails.dto.usuario;

import java.util.List;

public record UsuarioEntradaUpdateDto (
        int id,
        String nombre,
        String apellidos,
        String correo,
        String password,
        boolean verificado,
        String rol
        //List<Integer> faunas,
        //List<Integer> floras,
        //List<Integer> rutas,
        //List<Integer> comentarios,
        //List<Integer> rutasFavoritas
) {
}
