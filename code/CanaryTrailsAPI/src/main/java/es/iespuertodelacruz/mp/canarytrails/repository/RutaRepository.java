package es.iespuertodelacruz.mp.canarytrails.repository;

import es.iespuertodelacruz.mp.canarytrails.entities.Ruta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RutaRepository extends JpaRepository<Ruta, Integer> {

    @Query(
            value="DELETE FROM rutas WHERE id = :id",
            nativeQuery = true
    ) //Native Query
    int deleteRutaById(@Param("id") int id);

    @Query(
            value= "DELETE FROM ruta_fauna WHERE ruta_id = :rutaId",
            nativeQuery = true)
    int deleteRutaFaunaRelation(@Param("rutaId") int rutaId);

    @Query(
            value= "DELETE FROM usuario_ruta_favorita WHERE ruta_id = :rutaId",
            nativeQuery = true)
    int deleteRutaFavoritaRelation(@Param("rutaId") int rutaId);

    @Query(
            value= "DELETE FROM ruta_flora WHERE ruta_id = :rutaId",
            nativeQuery = true)
    int deleteRutaFloraRelation(@Param("rutaId") int rutaId);

    @Query(
            value= "DELETE FROM ruta_municipio WHERE ruta_id = :rutaId",
            nativeQuery = true)
    int deleteRutaMunicipioRelation(@Param("rutaId") int rutaId);

    @Query(
            value= "DELETE FROM coordenada_ruta WHERE ruta_id = :rutaId",
            nativeQuery = true)
    int deleteRutaCoordenadaRelation(@Param("rutaId") int rutaId);

}
