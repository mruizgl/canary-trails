package es.iespuertodelacruz.mp.canarytrails.dto.ruta;

import es.iespuertodelacruz.mp.canarytrails.dto.fauna.FaunaSalidaDto;
import es.iespuertodelacruz.mp.canarytrails.dto.usuario.UsuarioSalidaDtoV2;

import java.util.List;

/**
 * Dto de salida para las Rutas
 * V1: Muestra las relaciones
 * @param id
 * @param nombre
 * @param dificultad
 * @param tiempoDuracion
 * @param distanciaMetros
 * @param desnivel
 * @param aprobada
 */
public record RutaSalidaDtoV1(
        int id,
        String nombre,
        String dificultad,
        long tiempoDuracion,
        float distanciaMetros,
        float desnivel,
        boolean aprobada
        //UsuarioSalidaDtoV2 usuario,     //Usuario sin informacion adicional
        //List<ComentarioSalidaDtoV1> comentarios,   //Comentario con la información del usuario que lo hizo
        //List<MunicipiosSalidaDtoV2> municpios,    //Municipio sin información adicional
        //FaunaSalidaDto faunas,
        //FloraSalidaDto floras,

) {
}
