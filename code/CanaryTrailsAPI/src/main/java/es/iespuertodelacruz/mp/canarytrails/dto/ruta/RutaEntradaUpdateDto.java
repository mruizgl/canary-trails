package es.iespuertodelacruz.mp.canarytrails.dto.ruta;

public record RutaEntradaUpdateDto(
        int id,
        String nombre,
        String dificultad,
        long tiempoDuracion,
        float distanciaMetros,
        float desnivel,
        boolean aprobada
) {
}
