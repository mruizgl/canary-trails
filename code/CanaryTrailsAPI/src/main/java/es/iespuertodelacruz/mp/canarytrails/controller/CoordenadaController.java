package es.iespuertodelacruz.mp.canarytrails.controller;

import es.iespuertodelacruz.mp.canarytrails.dto.coordenada.CoordenadaEntradaCreateDto;
import es.iespuertodelacruz.mp.canarytrails.dto.coordenada.CoordenadaEntradaUpdateDto;
import es.iespuertodelacruz.mp.canarytrails.dto.coordenada.CoordenadaSalidaDto;
import es.iespuertodelacruz.mp.canarytrails.dto.flora.FloraSalidaDto;
import es.iespuertodelacruz.mp.canarytrails.entities.Coordenada;
import es.iespuertodelacruz.mp.canarytrails.entities.Flora;
import es.iespuertodelacruz.mp.canarytrails.entities.Ruta;
import es.iespuertodelacruz.mp.canarytrails.entities.Usuario;
import es.iespuertodelacruz.mp.canarytrails.service.CoordenadaService;
import es.iespuertodelacruz.mp.canarytrails.service.RutaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import es.iespuertodelacruz.mp.canarytrails.mapper.CoordenadaMapper;


@RestController
@RequestMapping("/api/v1/admin/coordenadas")
@CrossOrigin(origins = "*")
public class CoordenadaController {

    @Autowired
    CoordenadaService coordenadaService;

    @Autowired
    CoordenadaMapper coordenadaMapper;

    @Autowired
    RutaService rutaService;

    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(coordenadaMapper.toDTOList(coordenadaService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        Coordenada coordenada = coordenadaService.findById(id);

        if(coordenada == null){
            return ResponseEntity.notFound().build();
        }

        CoordenadaSalidaDto dto = coordenadaMapper.toDTO(coordenada);

        return ResponseEntity.ok(dto);
    }

    @PostMapping("/add")
    public ResponseEntity<?> create(@RequestBody CoordenadaEntradaCreateDto dto) {

        Coordenada coordenada = coordenadaMapper.toEntityCreate(dto);

        for( int id : dto.rutas()){
            Ruta ruta = rutaService.findById(id);
            if(ruta != null){
                coordenada.getRutas().add(ruta);
            }
        }

        Coordenada coordenadaGuardada = coordenadaService.save(coordenada);

        CoordenadaSalidaDto dtoSalida = coordenadaMapper.toDTO(coordenadaGuardada);

        return ResponseEntity.ok(dtoSalida);
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody CoordenadaEntradaUpdateDto dto) {
        Coordenada coordenada = coordenadaMapper.toEntityUpdate(dto);

        if(dto.rutas() != null){
            List<Ruta> nuevasRutas = new ArrayList<>();
            for( int id : dto.rutas()){
                Ruta ruta = rutaService.findById(id);
                if(ruta != null){
                    nuevasRutas.add(ruta);
                }
            }
            coordenada.setRutas(nuevasRutas);
        }

        return ResponseEntity.ok(coordenadaService.update(coordenada));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        return ResponseEntity.ok(coordenadaService.deleteById(id));
    }

    /*@PostMapping("/lote")
    public List<CoordenadaDTO> createBatch(@RequestBody List<CoordenadaDTO> coordenadasDTO) {
        List<Coordenada> coordenadas = coordenadaMapper.toEntityList(coordenadasDTO);
        List<Coordenada> saved = coordenadaService.saveAll(coordenadas);
        return coordenadaMapper.toDTOList(saved);
    }*/
}