package es.iespuertodelacruz.mp.canarytrails.mapper;

import es.iespuertodelacruz.mp.canarytrails.dto.comentario.RutaSalidaComentarioDto;
import es.iespuertodelacruz.mp.canarytrails.dto.comentario.UsuarioSalidaComentarioDto;
import es.iespuertodelacruz.mp.canarytrails.dto.coordenada.RutaSalidaCoordenadaDto;
import es.iespuertodelacruz.mp.canarytrails.dto.fauna.RutaSalidaFaunaDto;
import es.iespuertodelacruz.mp.canarytrails.dto.fauna.UsuarioSalidaFaunaDto;
import es.iespuertodelacruz.mp.canarytrails.dto.flora.RutaSalidaFloraDto;
import es.iespuertodelacruz.mp.canarytrails.dto.flora.UsuarioSalidaFloraDto;
import es.iespuertodelacruz.mp.canarytrails.dto.municipio.RutaSalidaMunicipioDto;
import es.iespuertodelacruz.mp.canarytrails.dto.municipio.ZonaSalidaMunicipioDto;
import es.iespuertodelacruz.mp.canarytrails.dto.ruta.*;
import es.iespuertodelacruz.mp.canarytrails.dto.usuario.*;
import es.iespuertodelacruz.mp.canarytrails.dto.zona.MunicipioSalidaZonaDto;
import es.iespuertodelacruz.mp.canarytrails.entities.*;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RelacionesMapper {

    //  <--- Zona Relaciones --->
    MunicipioSalidaZonaDto toSalidaZonaDto(Municipio municipio);

    //  <--- Municipio Relaciones --->
    ZonaSalidaMunicipioDto toSalidaMunicipioDto(Zona zona);
    RutaSalidaMunicipioDto toSalidaMunicipioDto(Ruta ruta);

    //  <--- Rutas Relaciones --->
    UsuarioSalidaRutaDto toSalidaRutaDto(Usuario usuario);
    ComentarioSalidaRutaDto toSalidaRutaDto(Comentario comentario);
    CoordenadaSalidaRutaDto toSalidaRutaDto(Coordenada coordenada);
    FaunaSalidaRutaDto toSalidaRutaDto(Fauna fauna);
    FloraSalidaRutaDto toSalidaRutaDto(Flora flora);
    MunicipioSalidaRutaDto toSalidaRutaDto(Municipio municipio);

    //  <--- Rutas Favoritas Relaciones --->

    //  <--- Usuarios Relaciones --->
    ComentarioSalidaUsuarioDto toSalidaUsuarioDto(Comentario entity);
    FaunaSalidaUsuarioDto toSalidaUsuarioDto(Fauna entity);
    FloraSalidaUsuarioDto toSalidaUsuarioDto(Flora entity);
    RutaSalidaUsuarioDto toSalidaUsuarioDto(Ruta entity);
    RutaFavoritaSalidaUsuarioDto toSalidaUsuarioRutaFavoritaDto(Ruta ruta);

    //  <--- Faunas Relaciones --->
    UsuarioSalidaFaunaDto toSalidaFaunaDto(Usuario usuario);
    RutaSalidaFaunaDto toSalidaFaunaDto(Ruta ruta);

    //  <--- Flora Relaciones --->
    UsuarioSalidaFloraDto toSalidaFloraDto(Usuario usuario);
    RutaSalidaFloraDto toSalidaFloraDto(Ruta ruta);

    //  <--- Coordenadas Relaciones --->
    RutaSalidaCoordenadaDto toSalidaCoordenadaDto(Ruta ruta);

    //  <--- Comentarios Relaciones --->
    UsuarioSalidaComentarioDto toSalidaComentario(Usuario usuario);
    RutaSalidaComentarioDto toSalidaComentario(Ruta ruta);
}
