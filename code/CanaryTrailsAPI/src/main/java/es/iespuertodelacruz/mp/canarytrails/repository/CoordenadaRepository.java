package es.iespuertodelacruz.mp.canarytrails.repository;

import es.iespuertodelacruz.mp.canarytrails.entities.Coordenada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface CoordenadaRepository extends JpaRepository<Coordenada, Integer> {

    @Modifying
    @Query(
            value="DELETE FROM coordenadas WHERE id = :id",
            nativeQuery = true
    ) //Native Query
    int deleteCoordenadaById(@Param("id") int id);

    @Modifying
    @Query(
            value= "DELETE FROM coordenada_ruta WHERE coordenada_id = :coordenadaId",
            nativeQuery = true)
    int deleteRutaCoordenadaRelation(@Param("coordenadaId") int coordenadaId);

    @Modifying
    @Query(
            value="INSERT INTO coordenada_ruta (coordenada_id, ruta_id) VALUES (:coordenadaId, :rutaId)",
            nativeQuery = true)
    void addRutaCoordenadaRelation(@Param("coordenadaId") int coordenadaId, @Param("rutaId") int rutaId);

    @Query(
            value = "SELECT * FROM coordenadas WHERE latitud = :latitud AND longitud = :longitud",
            nativeQuery = true
    )
    Optional<Coordenada> findByLatitudAndLongitud(@Param("latitud") BigDecimal latitud, @Param("longitud") BigDecimal longitud);

}
