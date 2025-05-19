package es.iespuertodelacruz.mp.canarytrails.dto.ruta;

import java.util.List;

public record RutaSalidaDto(
        int id,
        String nombre,
        String dificultad,
        long tiempoDuracion,
        float distanciaMetros,
        float desnivel,
        boolean aprobada,
        UsuarioSalidaRutaDto usuario,     //Usuario sin informacion adicional
        List<ComentarioSalidaRutaDto> comentarios,   //Comentario con la información del usuario que lo hizo
        List<FaunaSalidaRutaDto> faunas,
        List<FloraSalidaRutaDto> floras,
        List<CoordenadaSalidaRutaDto> coordenadas,
        List<MunicipioSalidaRutaDto> municipios,  //Municipio sin información adicional
        List<String> fotos
) {
}
