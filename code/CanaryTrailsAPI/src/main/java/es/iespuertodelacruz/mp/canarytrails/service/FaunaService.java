package es.iespuertodelacruz.mp.canarytrails.service;

import es.iespuertodelacruz.mp.canarytrails.common.IServiceGeneric;
import es.iespuertodelacruz.mp.canarytrails.entities.Fauna;
import es.iespuertodelacruz.mp.canarytrails.repository.FaunaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class FaunaService implements IServiceGeneric<Fauna, Integer> {

    @Autowired
    FaunaRepository faunaRepository;

    @Override
    public List<Fauna> findAll() {
        return faunaRepository.findAll();
    }

    @Override
    public Fauna findById(Integer id) {
        return faunaRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Fauna save(Fauna object) {

        if(object.getNombre() == null){
            throw new RuntimeException("El fauna ha de tener nombre");
        }

        if(object.getDescripcion() == null){
            throw new RuntimeException("La descripción ha de estar completa para crearla");
        }

        if(object.getUsuario() == null ){
            throw new RuntimeException("El usuario ha de existir");
        }

        /*if (savedFauna.getRutas() != null && !savedFauna.getRutas().isEmpty()) {
            for (Ruta ruta : savedFauna.getRutas()) {
                faunaRepository.addRutaFaunaRelation(savedFauna.getId(), ruta.getId());
            }
        }*/

        return faunaRepository.save(object);
    }

    @Override
    @Transactional
    public boolean update(Fauna object) {

        if(object != null && object.getId() != null) {

            Fauna fauna = faunaRepository.findById(object.getId()).orElse(null);

            if(fauna == null){
                throw new RuntimeException("No existe la fauna " +object);
                //TODO: cambiarlo por un false ?
            }

            if(object.getNombre() != null){
                fauna.setNombre(object.getNombre());
            }

            if(object.getDescripcion() != null){
                fauna.setDescripcion(object.getDescripcion());
            }

            if(object.getAprobada() != null){
                fauna.setAprobada(object.getAprobada());
            }

            if(object.getUsuario() != null){
                fauna.setUsuario(object.getUsuario());
            }

            if (object.getRutas() != null) {
                fauna.setRutas(object.getRutas());
            }

            faunaRepository.save(fauna);

            return true;
        } else{
            return false;
        }
        
    }

    @Override
    public boolean deleteById(Integer id) {

        faunaRepository.deleteRutaFaunaRelation(id);
        int cantidad = faunaRepository.deleteFaunaById(id);
        
        return cantidad > 0;
    }
}
