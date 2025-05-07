package es.iespuertodelacruz.mp.canarytrails.controller;

import es.iespuertodelacruz.mp.canarytrails.dto.municipio.MunicipioEdicionDto;
import es.iespuertodelacruz.mp.canarytrails.dto.municipio.MunicipioEntradaDto;
import es.iespuertodelacruz.mp.canarytrails.dto.municipio.MunicipioSalidaDto;
import es.iespuertodelacruz.mp.canarytrails.entities.Municipio;
import es.iespuertodelacruz.mp.canarytrails.entities.Zona;
import es.iespuertodelacruz.mp.canarytrails.mapper.MunicipioMapper;
import es.iespuertodelacruz.mp.canarytrails.service.MunicipioService;
import es.iespuertodelacruz.mp.canarytrails.service.ZonaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * Controlador para la gestión de municipios en la API de administración.
 * Proporciona endpoints para crear, leer, actualizar y eliminar municipios.
 * @author Melissa R.G. y Pedro M.E.
 */
@RestController
@RequestMapping("/api/v1/admin/municipios")
@CrossOrigin
public class MunicipioControllerAdmin {

    private static final Logger logger = LoggerFactory.getLogger(MunicipioControllerAdmin.class);

    @Autowired
    ZonaService zonaService;

    @Autowired
    MunicipioService municipioService;

    @Autowired
    MunicipioMapper municipioMapper;

    @Operation(summary = "Obtener todos los municipios")
    @GetMapping
    public ResponseEntity<List<MunicipioSalidaDto>> getAll() {
        logger.info("GET /api/v1/admin/municipios - Obtener todos los municipios");
        List<MunicipioSalidaDto> lista = municipioService.findAll().stream()
                .map(municipioMapper::toDto)
                .toList();
        return ResponseEntity.ok(lista);
    }

    @Operation(summary = "Obtener un municipio por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Municipio encontrado"),
            @ApiResponse(responseCode = "404", description = "Municipio no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<MunicipioSalidaDto> getById(@PathVariable Integer id) {
        logger.info("GET /api/v1/admin/municipios/{} - Obtener municipio por ID", id);
        Municipio municipio = municipioService.findById(id);
        if (municipio == null) {
            logger.warn("Municipio con ID {} no encontrado", id);
            return ResponseEntity.notFound().build();
        }
        logger.debug("Municipio encontrado: {}", municipio);
        return ResponseEntity.ok(municipioMapper.toDto(municipio));
    }

    @Operation(summary = "Crear un nuevo municipio")
    @PostMapping("/add")
    public ResponseEntity<MunicipioSalidaDto> create(@RequestBody MunicipioEntradaDto dto) {
        logger.info("POST /api/v1/admin/municipios/add - Crear municipio: {}", dto);

        Municipio municipio = municipioMapper.toEntity(dto);

        // Buscar la zona (lanzar 404 si no existe)
        Zona zona = zonaService.findById(dto.zonaId())
                .orElseThrow(() -> {
                    logger.warn("Zona con ID {} no encontrada", dto.zonaId());
                    return new ResponseStatusException(
                            HttpStatus.NOT_FOUND, "Zona con ID " + dto.zonaId() + " no encontrada"
                    );
                });

        municipio.setZona(zona);


        Municipio guardado = municipioService.save(municipio);
        logger.debug("Municipio creado con ID {}", guardado.getId());

        return ResponseEntity.ok(municipioMapper.toDto(guardado));
    }

    @Operation(summary = "Actualizar un municipio existente")
    @PutMapping("/update")
    public ResponseEntity<Boolean> update(@RequestBody MunicipioEdicionDto dto) {
        logger.info("PUT /api/v1/admin/municipios/update - Actualizar municipio ID {}", dto.id());

        Municipio municipio = municipioMapper.toEntity(dto);
        municipio.setId(dto.id());

        boolean actualizado = municipioService.update(municipio);
        if (actualizado) {
            logger.debug("Municipio actualizado: {}", municipio);
            return ResponseEntity.ok(true);
        } else {
            logger.warn("No se pudo actualizar el municipio con ID {}", dto.id());
            return ResponseEntity.notFound().build();
        }
    }


    @Operation(summary = "Eliminar un municipio por ID")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        logger.info("DELETE /api/v1/admin/municipios/delete/{} - Eliminar municipio", id);
        boolean eliminado = municipioService.deleteById(id);
        if (eliminado) {
            logger.debug("Municipio con ID {} eliminado", id);
            return ResponseEntity.noContent().build();
        } else {
            logger.warn("Municipio con ID {} no encontrado para eliminación", id);
            return ResponseEntity.notFound().build();
        }
    }
}