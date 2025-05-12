package es.iespuertodelacruz.mp.canarytrails.dto.municipio;

public record RutaSalidaMunicipioDto(
        int id,
        String nombre,
        String dificultad,
        long tiempoDuracion,
        float distanciaMetros,
        float desnivel,
        boolean aprobada
) {
}
