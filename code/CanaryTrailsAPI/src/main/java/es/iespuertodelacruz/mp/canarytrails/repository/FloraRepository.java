package es.iespuertodelacruz.mp.canarytrails.repository;

import es.iespuertodelacruz.mp.canarytrails.entities.Flora;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FloraRepository extends JpaRepository<Flora, Integer> {
}