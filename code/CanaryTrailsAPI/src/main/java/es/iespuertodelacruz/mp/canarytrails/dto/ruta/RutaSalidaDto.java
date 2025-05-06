package es.iespuertodelacruz.mp.canarytrails.dto.ruta;


import es.iespuertodelacruz.mp.canarytrails.dto.fauna.FaunaSalidaDto;

public record RutaSalidaDtoV1(
        int id,
        String nombre,
        String dificultad,
        long tiempoDuracion,
        float distanciaMetros,
        float desnivel,
        boolean aprobada,
        UsuarioSalidaRutaDto usuario,     //Usuario sin informacion adicional
        //List<ComentarioSalidaDtoV1> comentarios,   //Comentario con la información del usuario que lo hizo
        //List<MunicipiosSalidaDtoV2> municpios,    //Municipio sin información adicional
        FaunaSalidaDto faunas,
        //FloraSalidaDto floras,

) {
}
