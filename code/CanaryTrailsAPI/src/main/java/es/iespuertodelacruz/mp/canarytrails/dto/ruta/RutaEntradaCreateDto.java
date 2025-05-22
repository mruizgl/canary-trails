package es.iespuertodelacruz.mp.canarytrails.dto.ruta;

import java.util.List;

public record RutaEntradaCreateDto (
        String nombre,
        String dificultad,
        long tiempoDuracion,
        float distanciaMetros,
        float desnivel,
        boolean aprobada,
        Integer usuario,
        //List<Integer> comentarios,    //No hay comentarios en una recien creada
        List<Integer> faunas,
        List<Integer> floras,
        List<CoordenadaEntradaCreate> coordenadas,
        List<Integer> municipios
){
}
