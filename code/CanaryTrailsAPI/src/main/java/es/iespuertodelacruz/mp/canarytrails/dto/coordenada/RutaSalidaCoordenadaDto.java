package es.iespuertodelacruz.mp.canarytrails.dto.coordenada;

public record RutaSalidaCoordenadaDto (
        int id,
        String nombre,
        String dificultad,
        long tiempoDuracion,
        float distanciaMetros,
        float desnivel,
        boolean aprobada
){
}
