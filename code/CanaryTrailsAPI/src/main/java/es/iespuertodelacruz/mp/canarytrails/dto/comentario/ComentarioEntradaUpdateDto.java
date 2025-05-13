package es.iespuertodelacruz.mp.canarytrails.dto.comentario;

public record ComentarioEntradaUpdateDto(
        Integer id,
        String titulo,
        String descripcion,
        Integer usuario,
        Integer ruta
) {
}
