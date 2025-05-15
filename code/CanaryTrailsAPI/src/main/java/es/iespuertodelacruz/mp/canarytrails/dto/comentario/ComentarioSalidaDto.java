package es.iespuertodelacruz.mp.canarytrails.dto.comentario;

public record ComentarioSalidaDto(
        Integer id,
        String titulo,
        String descripcion,
        UsuarioSalidaComentarioDto usuario,
        RutaSalidaComentarioDto ruta
) {}
