package es.iespuertodelacruz.mp.canarytrails.repository;

import es.iespuertodelacruz.mp.canarytrails.entities.Zona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ZonaRepository extends JpaRepository<Zona, Integer> {
}