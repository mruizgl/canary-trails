package es.iespuertodelacruz.mp.canarytrails.controller;

import es.iespuertodelacruz.mp.canarytrails.dto.zona.ZonaEntradaDto;
import es.iespuertodelacruz.mp.canarytrails.dto.zona.ZonaSalidaDto;
import es.iespuertodelacruz.mp.canarytrails.entities.Zona;
import es.iespuertodelacruz.mp.canarytrails.mapper.ZonaMapper;
import es.iespuertodelacruz.mp.canarytrails.service.ZonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1/admin/zonas")
@CrossOrigin(origins = "*")
public class ZonaController {

    @Autowired
    private ZonaService zonaService;

    @Autowired
    private ZonaMapper zonaMapper;

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<Zona> zonas = zonaService.findAll();
        List<ZonaSalidaDto> dtos = zonaMapper.toDTOList(zonas);
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        return zonaService.findById(id)
                .map(zona -> ResponseEntity.ok(zonaMapper.toDTO(zona)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public ResponseEntity<?> create(@RequestBody ZonaEntradaDto dto) {
        Zona zona = new Zona();
        zona.setNombre(dto.nombre());
        Zona saved = zonaService.save(zona);
        return ResponseEntity.ok(zonaMapper.toDTO(saved));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody ZonaEntradaDto dto) {
        return zonaService.findById(id)
                .map(zona -> {
                    zona.setNombre(dto.nombre());
                    Zona updated = zonaService.save(zona);
                    return ResponseEntity.ok(zonaMapper.toDTO(updated));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        try {
            zonaService.eliminarZonaPorId(id);
            return ResponseEntity.ok("Zona eliminada correctamente.");
        } catch (IllegalStateException | NoSuchElementException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
}
