package es.iespuertodelacruz.mp.canarytrails.dto.ruta;

/**
 * Dto salida de Rutas
 * V2: por que muestra la información simple, sin relaciones
 */
public record RutaSalidaDtoV2(
        int id,
        String nombre,
        String dificultad,
        long tiempoDuracion,
        float distanciaMetros,
        float desnivel,
        boolean aprobada
) {
}
