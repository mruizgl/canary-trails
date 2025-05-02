package es.iespuertodelacruz.mp.canarytrails.repository;

import es.iespuertodelacruz.mp.canarytrails.entities.Fauna;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FaunaRepository extends JpaRepository<Fauna, Integer> {
    @Query(
            value="DELETE FROM faunas WHERE id = :id",
            nativeQuery = true
    ) //Native Query
    int deleteFaunaById(@Param("id") int id);

    @Query(
            value= "DELETE FROM ruta_fauna WHERE fauna_id = :faunaId",
            nativeQuery = true)
    int deleteRutaFaunaRelation(@Param("faunaId") int faunaId);

    /*@Query(
            value="INSERT INTO ruta_fauna (fauna_id, ruta_id) VALUES (:faunaId, :rutaId)",
            nativeQuery = true)
    void addRutaFaunaRelation(@Param("faunaId") int faunaId, @Param("rutaId") int rutaId);*/

}
