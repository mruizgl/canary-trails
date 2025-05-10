package es.iespuertodelacruz.mp.canarytrails.dto.comentario;


public record ComentarioEntradaDto(
        String titulo,
        String descripcion,
        Integer usuarioId,
        Integer rutaId
) {}

