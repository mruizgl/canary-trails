package es.iespuertodelacruz.mp.canarytrails.repository;

import es.iespuertodelacruz.mp.canarytrails.entities.Fauna;
import es.iespuertodelacruz.mp.canarytrails.entities.Ruta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FaunaRepository extends JpaRepository<Fauna, Integer> {

    @Modifying
    @Query(
            value="DELETE FROM faunas WHERE id = :id",
            nativeQuery = true
    ) //Native Query
    int deleteFaunaById(@Param("id") int id);

    @Modifying
    @Query(
            value= "DELETE FROM ruta_fauna WHERE fauna_id = :faunaId",
            nativeQuery = true)
    int deleteRutaFaunaRelation(@Param("faunaId") int faunaId);

    @Modifying
    @Query(
            value="INSERT INTO ruta_fauna (fauna_id, ruta_id) VALUES (:faunaId, :rutaId)",
            nativeQuery = true)
    void addRutaFaunaRelation(@Param("faunaId") int faunaId, @Param("rutaId") int rutaId);

    @Query(value = "SELECT * FROM faunas WHERE usuario_id = :usuarioId", nativeQuery = true)
    List<Fauna> findCreadasByUsuarioId(@Param("usuarioId") Integer usuarioId);

}
