package es.iespuertodelacruz.mp.canarytrails.repository;


import es.iespuertodelacruz.mp.canarytrails.entities.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComentarioRepository extends JpaRepository<Comentario, Integer> {

    @Modifying
    @Query(
            value="DELETE FROM comentarios WHERE id = :id",
            nativeQuery = true
    ) //Native Query
    int deleteComentarioById(@Param("id") int id);


    @Modifying
    @Query(
            value="DELETE FROM comentarios WHERE ruta_id = :id",
            nativeQuery = true
    ) //Native Query
    int deleteComentarioByRutaId(@Param("id") int id);


    // A futuro se puede cambiar por que el comentario sea de usuario_eliminado
    @Modifying
    @Query(
            value="DELETE FROM comentarios WHERE usuario_id = :id",
            nativeQuery = true
    ) //Native Query
    int deleteComentarioByUsuarioId(@Param("id") int id);
}


