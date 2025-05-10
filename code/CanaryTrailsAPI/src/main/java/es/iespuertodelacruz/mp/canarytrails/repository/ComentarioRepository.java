package es.iespuertodelacruz.mp.canarytrails.repository;


import es.iespuertodelacruz.mp.canarytrails.entities.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComentarioRepository extends JpaRepository<Comentario, Integer> {
    List<Comentario> findByUsuarioId(Integer usuarioId);
}


