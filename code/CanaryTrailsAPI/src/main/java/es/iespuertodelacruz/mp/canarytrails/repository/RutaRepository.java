package es.iespuertodelacruz.mp.canarytrails.repository;

import es.iespuertodelacruz.mp.canarytrails.entities.Ruta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface RutaRepository extends JpaRepository<Ruta, Integer> {

    @Modifying
    @Query(
            value="INSERT INTO usuario_ruta_favorita (usuario_id, ruta_id) VALUES (:usuario_id, :ruta_id)",
            nativeQuery = true
    ) //Native Query
    int addFavoritaById(@Param("usuario_id") int idUsuario, @Param("ruta_id") int idRuta);

    /**
     * Obtiene las rutas favoritas a partir de la id de un usuario
     * @param id del usuario
     * @return rutas favoritas de ese usuario
     */
    @Query(
            value="SELECT ruta_id FROM usuario_ruta_favorita WHERE usuario_id = :usuario_id",
            nativeQuery = true
    ) //Native Query
    List<Integer> findFavoritasByUserId(@Param("usuario_id") int id);

    @Query(value = """
        SELECT r.* 
        FROM rutas r
        JOIN usuario_ruta_favorita urf ON r.id = urf.ruta_id
        GROUP BY r.id
        HAVING COUNT(urf.usuario_id) >= 10
        ORDER BY COUNT(urf.usuario_id) DESC
        """, nativeQuery = true)
    List<Ruta> findRutasConMasDe10Favoritos();

    @Query(value = "SELECT * FROM rutas WHERE usuario_id = :usuarioId", nativeQuery = true)
    List<Ruta> findCreadasByUsuarioId(@Param("usuarioId") Integer usuarioId);

    /**
     * Borrar sin devolver void
     * @param id ruta a borrar
     * @return numero de columnas afectadas
     */
    @Modifying
    @Query(
            value="DELETE FROM rutas WHERE id = :id",
            nativeQuery = true
    ) //Native Query
    int deleteRutaById(@Param("id") int id);

    @Modifying
    @Query(
            value="DELETE FROM usuario_ruta_favorita WHERE usuario_id = :usuario_id AND ruta_id = :ruta_id",
            nativeQuery = true
    ) //Native Query
    int deleteFavoritaById(@Param("usuario_id") int idUsuario, @Param("ruta_id") int idRuta);



    // <------ Delete de relaciones ------->
    @Modifying
    @Query(
            value= "DELETE FROM ruta_fauna WHERE ruta_id = :rutaId",
            nativeQuery = true)
    int deleteRutaFaunaRelation(@Param("rutaId") int rutaId);

    @Modifying
    @Query(
            value= "DELETE FROM usuario_ruta_favorita WHERE ruta_id = :rutaId",
            nativeQuery = true)
    int deleteRutaFavoritaRelation(@Param("rutaId") int rutaId);

    @Modifying
    @Query(
            value= "DELETE FROM ruta_flora WHERE ruta_id = :rutaId",
            nativeQuery = true)
    int deleteRutaFloraRelation(@Param("rutaId") int rutaId);

    @Modifying
    @Query(
            value= "DELETE FROM ruta_municipio WHERE ruta_id = :rutaId",
            nativeQuery = true)
    int deleteRutaMunicipioRelation(@Param("rutaId") int rutaId);

    @Modifying
    @Query(
            value= "DELETE FROM coordenada_ruta WHERE ruta_id = :rutaId",
            nativeQuery = true)
    int deleteRutaCoordenadaRelation(@Param("rutaId") int rutaId);

}
