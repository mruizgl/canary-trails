package es.iespuertodelacruz.mp.canarytrails.controller.v2;

import es.iespuertodelacruz.mp.canarytrails.dto.municipio.MunicipioSalidaDto;
import es.iespuertodelacruz.mp.canarytrails.entities.Municipio;
import es.iespuertodelacruz.mp.canarytrails.mapper.MunicipioMapper;
import es.iespuertodelacruz.mp.canarytrails.service.MunicipioService;
import es.iespuertodelacruz.mp.canarytrails.service.RutaService;
import es.iespuertodelacruz.mp.canarytrails.service.ZonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2/municipios")
@CrossOrigin(origins = "*")
public class MunicipioControllerV2 {

    @Autowired
    MunicipioService municipioService;

    @Autowired
    MunicipioMapper municipioMapper;


    /**
     * Endpoint que devuelve una lista de todos los municipios
     * @return todos los municipios de la bbdd
     */
    @GetMapping
    public ResponseEntity<?> getAll() {
        List<Municipio> municipios = municipioService.findAll();
        List<MunicipioSalidaDto> dtos = municipios.stream()
                .map(municipioMapper::toDto)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    /**
     * Endpoint que devuelve un municipio a partir de su id
     * @param id del municipio
     * @return municipio cuya id se ha introducido
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        Municipio municipio = municipioService.findById(id);

        if (municipio == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(municipioMapper.toDto(municipio));
    }
}
