package es.iespuertodelacruz.mp.canarytrails.controller.v2;

import es.iespuertodelacruz.mp.canarytrails.dto.zona.ZonaEntradaCreateDto;
import es.iespuertodelacruz.mp.canarytrails.dto.zona.ZonaEntradaUpdateDto;
import es.iespuertodelacruz.mp.canarytrails.dto.zona.ZonaSalidaDto;
import es.iespuertodelacruz.mp.canarytrails.entities.Municipio;
import es.iespuertodelacruz.mp.canarytrails.entities.Zona;
import es.iespuertodelacruz.mp.canarytrails.mapper.ZonaMapper;
import es.iespuertodelacruz.mp.canarytrails.service.MunicipioService;
import es.iespuertodelacruz.mp.canarytrails.service.ZonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v2/zonas")
@CrossOrigin(origins = "*")
public class ZonaControllerV2 {

    @Autowired
    ZonaService zonaService;

    @Autowired
    ZonaMapper zonaMapper;


    /**
     * Endpoint que devuelve todas las zonas
     * @return todas las zonas existentes en la bbdd
     */
    @GetMapping
    public ResponseEntity<?> getAll() {
        List<Zona> zonas = zonaService.findAll();
        List<ZonaSalidaDto> dtos = zonaMapper.toDTOList(zonas);
        return ResponseEntity.ok(dtos);
    }

    /**
     * Endpoint que devuelve una zona segun la id
     * @param id de la zona
     * @return la zona que tenga la id introducida
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        Zona zona = zonaService.findById(id);

        if(zona == null){
            return ResponseEntity.notFound().build();
        }

        ZonaSalidaDto dto = zonaMapper.toDTO(zona);

        return ResponseEntity.ok(dto);
    }
}
