package es.iespuertodelacruz.mp.canarytrails.service;

import es.iespuertodelacruz.mp.canarytrails.common.IServiceGeneric;
import es.iespuertodelacruz.mp.canarytrails.entities.Fauna;
import es.iespuertodelacruz.mp.canarytrails.entities.Flora;
import es.iespuertodelacruz.mp.canarytrails.entities.Ruta;
import es.iespuertodelacruz.mp.canarytrails.repository.FaunaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FaunaService implements IServiceGeneric<Fauna, Integer> {

    @Autowired
    FaunaRepository faunaRepository;

    @Override
    public List<Fauna> findAll() {
        return faunaRepository.findAll();
    }

    public List<Fauna> findAllByCreadorId(Integer id){
        return faunaRepository.findCreadasByUsuarioId(id);
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

        /*if(object.getFoto() == null || object.getFoto().isBlank()){
            object.setFoto("src/main/resources/uploads/fauna/default");
        }*/

        //La foto se permite establecerse en nulo, en el front cargamos un "No tiene foto" o algo así

        Fauna savedFauna = faunaRepository.save(object);

        if (savedFauna.getRutas() != null && !savedFauna.getRutas().isEmpty()) {
            for (Ruta ruta : savedFauna.getRutas()) {
                faunaRepository.addRutaFaunaRelation(savedFauna.getId(), ruta.getId());
            }
        }

        return savedFauna;
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

            if(object.getFoto() != null && !object.getFoto().isBlank()){
                fauna.setFoto(object.getFoto());
            }

            Fauna savedFauna = faunaRepository.save(fauna);

            // Se borran las relaciones siempre. Si hay nuevas se actualizan, si no se quiere actualizar se tienen q
            // poner las id de las que ya estaban, y si no se pone ninguna o un 0, se borran todas las relaciones
            int cantidad = faunaRepository.deleteRutaFaunaRelation(savedFauna.getId());

            if (savedFauna.getRutas() != null && !savedFauna.getRutas().isEmpty()) {
                for (Ruta ruta : object.getRutas()) {
                    faunaRepository.addRutaFaunaRelation(savedFauna.getId(), ruta.getId());
                }
            }

            return true;
        } else{
            return false;
        }
        
    }

    @Transactional
    public boolean uploadFoto(Fauna fauna){
        faunaRepository.save(fauna);
        return true;
    }

    @Override
    @Transactional
    public boolean deleteById(Integer id) {

        faunaRepository.deleteRutaFaunaRelation(id);
        int cantidad = faunaRepository.deleteFaunaById(id);
        
        return cantidad > 0;
    }
}
