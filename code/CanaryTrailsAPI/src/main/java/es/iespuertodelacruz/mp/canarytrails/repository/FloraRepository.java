package es.iespuertodelacruz.mp.canarytrails.repository;

import es.iespuertodelacruz.mp.canarytrails.entities.Flora;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FloraRepository extends JpaRepository<Flora, Integer> {

    @Modifying
    @Query(
            value="DELETE FROM floras WHERE id = :id",
            nativeQuery = true
    ) //Native Query
    int deleteFloraById(@Param("id") int id);

    @Modifying
    @Query(
            value= "DELETE FROM ruta_flora WHERE flora_id = :floraId",
            nativeQuery = true)
    int deleteRutaFloraRelation(@Param("floraId") int floraId);

    @Modifying
    @Query(
            value="INSERT INTO ruta_flora (flora_id, ruta_id) VALUES (:floraId, :rutaId)",
            nativeQuery = true)
    void addRutaFloraRelation(@Param("floraId") int floraId, @Param("rutaId") int rutaId);
}