package es.iespuertodelacruz.mp.canarytrails.dto.comentario;

public record RutaSalidaComentarioDto(
        int id,
        String nombre,
        String dificultad,
        long tiempoDuracion,
        float distanciaMetros,
        float desnivel,
        boolean aprobada
) {
}
