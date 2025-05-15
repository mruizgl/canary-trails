package es.iespuertodelacruz.mp.canarytrails.dto.comentario;


public record ComentarioEntradaCreateDto(
        String titulo,
        String descripcion,
        Integer usuario,
        Integer ruta
) {}

