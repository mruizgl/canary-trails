package es.iespuertodelacruz.mp.canarytrails.repository;

import es.iespuertodelacruz.mp.canarytrails.entities.Municipio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface MunicipioRepository extends JpaRepository<Municipio, Integer> {

    @Modifying
    @Query(
            value="DELETE FROM municipios WHERE id = :id",
            nativeQuery = true
    ) //Native Query
    int deleteMunicipioById(@Param("id") int id);

    @Modifying
    @Query(
            value= "DELETE FROM ruta_municipio WHERE municipio_id = :municipioId",
            nativeQuery = true)
    int deleteRutaMunicipioRelation(@Param("municipioId") int municipioId);

    @Modifying
    @Query(
            value="INSERT INTO ruta_municipio (municipio_id, ruta_id) VALUES (:municipioId, :rutaId)",
            nativeQuery = true)
    void addRutaMunicipioRelation(@Param("municipioId") int municipioId, @Param("rutaId") int rutaId);

}
