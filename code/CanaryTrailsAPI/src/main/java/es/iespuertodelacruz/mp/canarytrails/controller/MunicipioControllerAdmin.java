package es.iespuertodelacruz.mp.canarytrails.controller;

import es.iespuertodelacruz.mp.canarytrails.dto.municipio.MunicipioEntradaDto;
import es.iespuertodelacruz.mp.canarytrails.dto.municipio.MunicipioEdicionDto;
import es.iespuertodelacruz.mp.canarytrails.dto.municipio.MunicipioSalidaDto;
import es.iespuertodelacruz.mp.canarytrails.entities.Municipio;
import es.iespuertodelacruz.mp.canarytrails.entities.Zona;
import es.iespuertodelacruz.mp.canarytrails.mapper.MunicipioMapper;
import es.iespuertodelacruz.mp.canarytrails.service.MunicipioService;
import es.iespuertodelacruz.mp.canarytrails.service.ZonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/municipios")
@CrossOrigin(origins = "*")
public class MunicipioControllerAdmin {

    @Autowired
    MunicipioService municipioService;

    @Autowired
    MunicipioMapper municipioMapper;

    @Autowired
    ZonaService zonaService;

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<Municipio> municipios = municipioService.findAll();
        List<MunicipioSalidaDto> dtos = municipios.stream()
                .map(municipioMapper::toDto)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        Municipio municipio = municipioService.findById(id);
        if (municipio == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(municipioMapper.toDto(municipio));
    }

    @PostMapping("/add")
    public ResponseEntity<?> create(@RequestBody MunicipioEntradaDto dto) {
        Municipio municipio = municipioMapper.toEntity(dto);
        Zona zona = zonaService.findById(dto.zonaId()).orElse(null);

        if (zona == null) {
            return ResponseEntity.badRequest().body("Zona con ID " + dto.zonaId() + " no encontrada");
        }

        municipio.setZona(zona);
        Municipio guardado = municipioService.save(municipio);
        return ResponseEntity.ok(municipioMapper.toDto(guardado));
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody MunicipioEdicionDto dto) {
        Municipio municipio = municipioMapper.toEntity(dto);
        municipio.setId(dto.id());

        boolean actualizado = municipioService.update(municipio);

        if (actualizado) {
            return ResponseEntity.ok(municipioMapper.toDto(municipio));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        boolean eliminado = municipioService.deleteById(id);

        if (eliminado) {
            return ResponseEntity.ok("Municipio eliminado correctamente");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
