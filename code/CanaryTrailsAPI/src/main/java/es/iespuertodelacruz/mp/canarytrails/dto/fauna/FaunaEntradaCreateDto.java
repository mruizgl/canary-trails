package es.iespuertodelacruz.mp.canarytrails.dto.fauna;

import java.util.List;

public record FaunaEntradaCreateDto(
        String nombre,
        String descripcion,
        boolean aprobada,
        int usuario,
        List<Integer> rutas
){
}
