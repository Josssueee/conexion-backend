package com.conexion.backend.controller;

import com.conexion.backend.model.Rol;
import com.conexion.backend.service.service.RolService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controlador REST para la gestión de Roles.
 * URL Base: /api/roles
 */
@RestController
@RequestMapping("/api/roles")
public class RolController {

    private final RolService rolService;

    public RolController(RolService rolService) {
        this.rolService = rolService;
    }

    @GetMapping
    public ResponseEntity<List<Rol>> findAll() {
        return ResponseEntity.ok(rolService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rol> findById(@PathVariable Integer id) {
        return rolService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> save(@RequestBody Rol rol) {
        Rol savedRol = rolService.save(rol);
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Rol registrado exitosamente.");
        response.put("data", savedRol);
        
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Rol> update(@PathVariable Integer id, @RequestBody Rol rolDetails) {
        return rolService.findById(id)
                .map(rol -> {
                    rol.setNombreRol(rolDetails.getNombreRol());
                    return ResponseEntity.ok(rolService.save(rol));
                }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (rolService.findById(id).isPresent()) {
            rolService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}