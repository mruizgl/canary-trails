package es.iespuertodelacruz.mp.canarytrails.dto.flora;

public record RutaSalidaFlora(
        int id,
        String nombre,
        String dificultad,
        long tiempoDuracion,
        float distanciaMetros,
        float desnivel,
        boolean aprobada
) {
}
