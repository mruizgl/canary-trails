package es.iespuertodelacruz.mp.canarytrails.controller;

import es.iespuertodelacruz.mp.canarytrails.dto.zona.ZonaSalidaDto;
import es.iespuertodelacruz.mp.canarytrails.entities.Zona;
import es.iespuertodelacruz.mp.canarytrails.mapper.ZonaMapper;
import es.iespuertodelacruz.mp.canarytrails.service.ZonaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/zonas")
@CrossOrigin
public class ZonaController {

    private static final Logger logger = LoggerFactory.getLogger(ZonaController.class);

    @Autowired
    private ZonaService zonaService;

    @Autowired
    private ZonaMapper zonaMapper;

    @Operation(summary = "Obtener todas las zonas")
    @GetMapping
    public List<ZonaSalidaDto> getAll() {
        logger.info("Petición GET para obtener todas las zonas");
        return zonaService.findAll().stream()
                .map(zonaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Obtener zona por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Zona encontrada"),
            @ApiResponse(responseCode = "404", description = "Zona no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ZonaSalidaDto> getById(@PathVariable Integer id) {
        logger.info("Petición GET para obtener la zona con ID {}", id);
        Optional<Zona> zonaOpt = zonaService.findById(id);
        return zonaOpt.map(z -> {
            logger.debug("Zona encontrada: {}", z);
            return ResponseEntity.ok(zonaMapper.toDTO(z));
        }).orElseGet(() -> {
            logger.warn("Zona con ID {} no encontrada", id);
            return ResponseEntity.notFound().build();
        });
    }

    @Operation(summary = "Crear una nueva zona")
    @PostMapping
    public ResponseEntity<ZonaSalidaDto> create(@RequestBody ZonaSalidaDto zonaSalidaDto) {
        logger.info("Petición POST para crear zona: {}", zonaSalidaDto);
        Zona saved = zonaService.save(zonaMapper.toEntity(zonaSalidaDto));
        logger.debug("Zona creada con ID {}", saved.getId());
        return ResponseEntity.ok(zonaMapper.toDTO(saved));
    }

    @Operation(summary = "Actualizar una zona existente por ID")
    @PutMapping("/{id}")
    public ResponseEntity<ZonaSalidaDto> update(@PathVariable Integer id, @RequestBody ZonaSalidaDto zonaSalidaDto) {
        logger.info("Petición PUT para actualizar zona con ID {}", id);
        return zonaService.findById(id)
                .map(existing -> {
                    logger.debug("Zona original: {}", existing);
                    existing.setNombre(zonaSalidaDto.getNombre());
                    Zona updated = zonaService.save(existing);
                    logger.debug("Zona actualizada: {}", updated);
                    return ResponseEntity.ok(zonaMapper.toDTO(updated));
                })
                .orElseGet(() -> {
                    logger.warn("Zona con ID {} no encontrada para actualización", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @Operation(summary = "Eliminar una zona por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        logger.info("Petición DELETE para eliminar zona con ID {}", id);
        if (zonaService.findById(id).isPresent()) {
            zonaService.deleteById(id);
            logger.debug("Zona con ID {} eliminada", id);
            return ResponseEntity.noContent().build();
        } else {
            logger.warn("Zona con ID {} no encontrada para eliminación", id);
            return ResponseEntity.notFound().build();
        }
    }
}
