package es.iespuertodelacruz.mp.canarytrails.dto;

import java.util.List;

public record FaunaEntradaDto(
        int id,
        String nombre,
        String descripcion,
        boolean aprobada,
        int usuario,
        List<Integer> rutas
){
}
