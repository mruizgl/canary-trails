package es.iespuertodelacruz.mp.canarytrails.common;

import java.util.List;
import java.util.Optional;

public interface ICrudService <T, ID>{
    List<T> findAll();
    Optional<T> findById(ID id);
    T save(T entity);
    void deleteById(ID id);
}
