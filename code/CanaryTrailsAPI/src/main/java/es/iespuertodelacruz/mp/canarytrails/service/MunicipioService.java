package es.iespuertodelacruz.mp.canarytrails.service;

import es.iespuertodelacruz.mp.canarytrails.common.IServiceGeneric;
import es.iespuertodelacruz.mp.canarytrails.entities.Municipio;
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

        Zona zona = zonaRepository.findById(municipio.getZona().getId())
                .orElseThrow(() -> {
                    logger.error("Zona con ID {} no encontrada", municipio.getZona().getId());
                    return new RuntimeException("Zona no encontrada");
                });

        municipio.setZona(zona);

        Municipio guardado = municipioRepository.save(municipio);
        logger.debug("Municipio guardado con ID {}", guardado.getId());
        return guardado;
    }

    @Override
    @Transactional
    public boolean update(Municipio municipio) {
        logger.info("Actualizando municipio con ID {}", municipio.getId());

        if (municipio != null && municipio.getId() != null) {
            Municipio existente = municipioRepository.findById(municipio.getId()).orElse(null);
            if (existente == null) {
                logger.warn("No se encontró el municipio con ID {}", municipio.getId());
                return false;
            }

            if (municipio.getNombre() != null) existente.setNombre(municipio.getNombre());
            if (municipio.getAltitudMedia() != null) existente.setAltitudMedia(municipio.getAltitudMedia());
            if (municipio.getLatitudGeografica() != null) existente.setLatitudGeografica(municipio.getLatitudGeografica());
            if (municipio.getLongitudGeografica() != null) existente.setLongitudGeografica(municipio.getLongitudGeografica());
            if (municipio.getZona() != null && municipio.getZona().getId() != null) {
                Zona zona = zonaRepository.findById(municipio.getZona().getId())
                        .orElseThrow(() -> {
                            logger.error("Zona con ID {} no encontrada", municipio.getZona().getId());
                            return new RuntimeException("Zona no encontrada");
                        });
                existente.setZona(zona);
            }

            municipioRepository.save(existente);
            logger.debug("Municipio actualizado con ID {}", existente.getId());
            return true;
        }

        logger.warn("Intento de actualización con datos incompletos");
        return false;
    }

    @Override
    @Transactional
    public boolean deleteById(Integer id) {
        logger.info("Eliminando municipio con ID {}", id);
        municipioRepository.deleteById(id);
        logger.debug("Municipio con ID {} eliminado", id);
        return true;
    }
}
