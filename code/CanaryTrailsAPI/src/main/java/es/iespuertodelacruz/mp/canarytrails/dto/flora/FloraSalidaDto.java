package es.iespuertodelacruz.mp.canarytrails.dto.flora;

import java.util.List;

public record FloraSalidaDto (
         int id,
         String nombre,
         String especie,
         String tipoHoja,
         String salidaFlor,
         String caidaFlor,
         String descripcion,
         boolean aprobada,
         UsuarioSalidaFloraDto usuario,
         List<RutaSalidaFloraDto> rutas,
         String foto
) {
}
