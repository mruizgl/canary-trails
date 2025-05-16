package es.iespuertodelacruz.mp.canarytrails.controller.v2;

import es.iespuertodelacruz.mp.canarytrails.dto.coordenada.CoordenadaEntradaCreateDto;
import es.iespuertodelacruz.mp.canarytrails.dto.coordenada.CoordenadaEntradaUpdateDto;
import es.iespuertodelacruz.mp.canarytrails.dto.coordenada.CoordenadaSalidaDto;
import es.iespuertodelacruz.mp.canarytrails.entities.Comentario;
import es.iespuertodelacruz.mp.canarytrails.entities.Coordenada;
import es.iespuertodelacruz.mp.canarytrails.mapper.CoordenadaMapper;
import es.iespuertodelacruz.mp.canarytrails.service.CoordenadaService;
import es.iespuertodelacruz.mp.canarytrails.service.RutaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v2/coordenadas")
@CrossOrigin(origins = "*")
public class CoordenadaControllerV2 {

    @Autowired
    CoordenadaService coordenadaService;

    @Autowired
    CoordenadaMapper coordenadaMapper;


    /**
     * Endpoint que devuelve una coordenada de la bbdd según la id
     * @param id de la coordenada
     * @return coordenada que tiene la id introducida
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        Coordenada coordenada = coordenadaService.findById(id);

        if(coordenada == null){
            return ResponseEntity.notFound().build();
        }

        CoordenadaSalidaDto dto = coordenadaMapper.toDTO(coordenada);

        return ResponseEntity.ok(dto);
    }

    /**
     * Endpoint que crea una coordenada en la bbdd
     * @param dto con los datos de la coordenada a crear
     * @return la coordenada creada
     */
    @PostMapping("/add")
    public ResponseEntity<?> create(@RequestBody CoordenadaEntradaCreateDto dto) {

        Coordenada coordenada = coordenadaMapper.toEntityCreate(dto);

        try{
            coordenada = coordenadaService.save(coordenada);
        } catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        return ResponseEntity.ok(coordenadaMapper.toDTO(coordenada));
    }

}