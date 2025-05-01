package es.iespuertodelacruz.mp.canarytrails.repository;

import es.iespuertodelacruz.mp.canarytrails.entities.Ruta;
import es.iespuertodelacruz.mp.canarytrails.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    @Query(
            value="DELETE FROM usuarios WHERE id = :id",
            nativeQuery = true
    ) //Native Query
    int deleteUsuarioBydId(@Param("id") int id);

    @Query(
            value="SELECT FROM usuario_ruta_favorita WHERE id = :id",
            nativeQuery = true
    )
    List<Ruta> getAllRutasFavoritasById(@Param("id") int idUsuario);
}
