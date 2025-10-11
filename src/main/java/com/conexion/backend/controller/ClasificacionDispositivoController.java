package com.conexion.backend.controller;

import com.conexion.backend.model.ClasificacionDispositivo;
import com.conexion.backend.service.service.ClasificacionDispositivoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controlador REST para la gestión de ClasificacionDispositivo (Catálogo de Tipos de Equipo).
 * URL Base: /api/clasificaciones
 */
@RestController
@RequestMapping("/api/clasificaciones")
public class ClasificacionDispositivoController {

    private final ClasificacionDispositivoService service;

    public ClasificacionDispositivoController(ClasificacionDispositivoService service) {
        this.service = service;
    }

    /**
     * GET: Obtener todas las clasificaciones.
     */
    @GetMapping
    public ResponseEntity<List<ClasificacionDispositivo>> findAll() {
        List<ClasificacionDispositivo> clasificaciones = service.findAll();
        return ResponseEntity.ok(clasificaciones);
    }

    /**
     * GET: Obtener una clasificación por su ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ClasificacionDispositivo> findById(@PathVariable Integer id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * POST: Crear una nueva clasificación.
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> save(@RequestBody ClasificacionDispositivo clasificacion) {
        ClasificacionDispositivo savedClasificacion = service.save(clasificacion);
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Clasificación creada exitosamente.");
        response.put("data", savedClasificacion);
        
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * PUT: Actualizar una clasificación existente.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ClasificacionDispositivo> update(@PathVariable Integer id, @RequestBody ClasificacionDispositivo details) {
        return service.findById(id)
                .map(clasificacion -> {
                    // Solo actualizamos campos específicos (ej: nombre)
                    clasificacion.setNombreClasificacion(details.getNombreClasificacion());

                    return ResponseEntity.ok(service.save(clasificacion));
                }).orElse(ResponseEntity.notFound().build());
    }

    /**
     * DELETE: Eliminar una clasificación por su ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (service.findById(id).isPresent()) {
            service.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
