package es.iespuertodelacruz.mp.canarytrails.dto.ruta;

import java.util.List;

public record RutaEntradaUpdateDto(
        int id,
        String nombre,
        String dificultad,
        long tiempoDuracion,
        float distanciaMetros,
        float desnivel,
        boolean aprobada,
        Integer usuario,
        //List<Integer> comentarios,
        List<Integer> faunas,
        List<Integer> floras,
        List<Integer> coordenadas,
        List<Integer> municipios
) {
}
