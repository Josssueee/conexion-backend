package com.conexion.backend.controller;

import com.conexion.backend.dto.ClasificacionDispositivoDTO;
import com.conexion.backend.service.service.ClasificacionDispositivoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/*dkis */
@RestController
@RequestMapping("/api/clasificaciones")
public class ClasificacionDispositivoController {

    @Autowired
    private ClasificacionDispositivoService service;

    @GetMapping
    public ResponseEntity<List<ClasificacionDispositivoDTO>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClasificacionDispositivoDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<ClasificacionDispositivoDTO> create(@RequestBody ClasificacionDispositivoDTO dto) {
        return new ResponseEntity<>(service.save(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClasificacionDispositivoDTO> update(@PathVariable Integer id, @RequestBody ClasificacionDispositivoDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}