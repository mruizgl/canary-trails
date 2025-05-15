package es.iespuertodelacruz.mp.canarytrails.service;

import es.iespuertodelacruz.mp.canarytrails.common.IServiceGeneric;
import es.iespuertodelacruz.mp.canarytrails.entities.Municipio;
import es.iespuertodelacruz.mp.canarytrails.entities.Ruta;
import es.iespuertodelacruz.mp.canarytrails.entities.Zona;
import es.iespuertodelacruz.mp.canarytrails.repository.MunicipioRepository;
import es.iespuertodelacruz.mp.canarytrails.repository.ZonaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MunicipioService implements IServiceGeneric<Municipio, Integer> {

    private static final Logger logger = LoggerFactory.getLogger(MunicipioService.class);

    @Autowired
    MunicipioRepository municipioRepository;

    @Autowired
    ZonaRepository zonaRepository;

    @Override
    public List<Municipio> findAll() {
        logger.info("Obteniendo todos los municipios");
        return municipioRepository.findAll();
    }

    @Override
    public Municipio findById(Integer id) {
        logger.info("Buscando municipio con ID {}", id);
        return municipioRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Municipio save(Municipio municipio) {
        logger.info("Guardando nuevo municipio: {}", municipio);

        if (municipio.getNombre() == null || municipio.getNombre().isBlank()) {
            logger.warn("Nombre del municipio es obligatorio");
            throw new RuntimeException("El municipio debe tener un nombre");
        }

        if (municipio.getAltitudMedia() == null) {
            logger.warn("Altitud media es obligatoria");
            throw new RuntimeException("El municipio debe tener altitud media");
        }

        if (municipio.getLatitudGeografica() == null) {
            logger.warn("Latitud geográfica es obligatoria");
            throw new RuntimeException("El municipio debe tener latitud");
        }
        if (municipio.getLongitudGeografica() == null) {
            logger.warn("Longitud geográfica es obligatoria");
            throw new RuntimeException("El municipio debe tener longitud");
        }
        if (municipio.getZona() == null || municipio.getZona().getId() == null) {
            logger.warn("Zona del municipio es obligatoria");
            throw new RuntimeException("El municipio debe tener una zona válida");
        }

        Municipio municipioGuardado = municipioRepository.save(municipio);

        if (municipioGuardado.getRutas() != null && !municipioGuardado.getRutas().isEmpty()) {
            for (Ruta ruta : municipioGuardado.getRutas()) {
                municipioRepository.addRutaMunicipioRelation(municipioGuardado.getId(), ruta.getId());
            }
        }

        logger.debug("Municipio guardado con ID {}", municipioGuardado.getId());
        return municipioGuardado;
    }

    @Override
    @Transactional
    public boolean update(Municipio object) {

        if(object != null && object.getId() != null) {
            logger.info("Actualizando municipio con ID {}", object.getId());

            Municipio municipio = municipioRepository.findById(object.getId()).orElse(null);

            if(municipio == null){
                throw new RuntimeException("No existe la municipio " +object);
                //TODO: cambiarlo por un false ?
            }

            if(object.getNombre() != null){
                logger.info("Nombre actualizando de {} a {}", object.getNombre(), municipio.getNombre() );
                municipio.setNombre(object.getNombre());
            }

            if (object.getAltitudMedia() != null) {
                logger.info("Altitud Media actualizando de {} a {}", object.getAltitudMedia(), municipio.getAltitudMedia() );
                municipio.setAltitudMedia(object.getAltitudMedia());
            }

            if (object.getLatitudGeografica() != null) {
                logger.info("Latitud actualizando de {} a {}", object.getLatitudGeografica(), municipio.getLatitudGeografica() );
                municipio.setLatitudGeografica(object.getLatitudGeografica());
            }

            if (object.getLongitudGeografica() != null) {
                logger.info("Longitud actualizando de {} a {}", object.getLongitudGeografica(), municipio.getLongitudGeografica() );
                municipio.setLongitudGeografica(object.getLongitudGeografica());
            }

            if (object.getZona() != null) {
                logger.info("Zona actualizando de {} a {}", object.getZona(), municipio.getZona() );
                municipio.setZona(object.getZona());
            }

            if (object.getRutas() != null) {
                logger.info("Rutas actualizandos");
                municipio.setRutas(object.getRutas());
            }

            Municipio savedMunicipio = municipioRepository.save(municipio);

            int cantidad = municipioRepository.deleteRutaMunicipioRelation(municipio.getId());
            logger.info("Relaciones actualizadas. Borradas {} relaciones", cantidad);
            //System.out.println("HEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEY"+cantidad);

            if (savedMunicipio.getRutas() != null && !savedMunicipio.getRutas().isEmpty()) {
                for (Ruta ruta : object.getRutas()) {
                    logger.info("Relaciones actualizando. Creando relacion municipio {} ruta {}", savedMunicipio.getId(), ruta.getId());
                    municipioRepository.addRutaMunicipioRelation(savedMunicipio.getId(), ruta.getId());
                }
            }

            logger.warn("Objeto terminado de actualizar");
            return true;
        } else{
            logger.warn("Intento de actualización con objeto o id nula");
            return false;
        }
    }

    @Override
    @Transactional
    public boolean deleteById(Integer id) {

        Municipio municipio = findById(id);

        if(!municipio.getRutas().isEmpty()){
            logger.warn("El municipio a eliminar contiene las rutas {} y no puede ser eliminado", municipio.getRutas().toString());
            throw new RuntimeException("El municipio a eliminar no puede contener rutas");
        }

        logger.info("Eliminando municipio con ID {}", id);
        int cantidad = municipioRepository.deleteMunicipioById(id);

        return cantidad > 0;
    }
}
