package com.conexion.backend.controller;

import com.conexion.backend.dto.DispositivoDTO;
import com.conexion.backend.service.service.DispositivosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dispositivos")
public class DispositivosController {

    @Autowired
    private DispositivosService dispositivosService;

    @GetMapping
    public ResponseEntity<List<DispositivoDTO>> findAll() {
        return ResponseEntity.ok(dispositivosService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DispositivoDTO> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(dispositivosService.findById(id));
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<DispositivoDTO>> search(@RequestParam String query) {
        return ResponseEntity.ok(dispositivosService.search(query));
    }

    @PostMapping
    public ResponseEntity<DispositivoDTO> save(@RequestBody DispositivoDTO dto) {
        return new ResponseEntity<>(dispositivosService.save(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DispositivoDTO> update(@PathVariable Integer id, @RequestBody DispositivoDTO dto) {
        return ResponseEntity.ok(dispositivosService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        dispositivosService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
