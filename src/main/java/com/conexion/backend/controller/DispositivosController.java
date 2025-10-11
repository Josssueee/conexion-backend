package com.conexion.backend.controller;

import com.conexion.backend.model.Dispositivo;
import com.conexion.backend.service.service.DispositivosService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controlador REST para la gestión de Dispositivos.
 * URL Base: /api/dispositivos
 */
@RestController
@RequestMapping("/api/dispositivos")
public class DispositivosController {

    private final DispositivosService dispositivosService;

    public DispositivosController(DispositivosService dispositivosService) {
        this.dispositivosService = dispositivosService;
    }

    @GetMapping
    public ResponseEntity<List<Dispositivo>> findAll() {
        return ResponseEntity.ok(dispositivosService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Dispositivo> findById(@PathVariable Integer id) {
        return dispositivosService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> save(@RequestBody Dispositivo dispositivo) {
        Dispositivo savedDispositivo = dispositivosService.save(dispositivo);
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Dispositivo registrado exitosamente.");
        response.put("data", savedDispositivo);
        
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Dispositivo> update(@PathVariable Integer id, @RequestBody Dispositivo dispositivoDetails) {
        return dispositivosService.findById(id)
                .map(dispositivo -> {
                    dispositivo.setModelo(dispositivoDetails.getModelo());
                    dispositivo.setDireccionMac(dispositivoDetails.getDireccionMac());
                    dispositivo.setIpLocal(dispositivoDetails.getIpLocal());
                    dispositivo.setContrasenaWifi(dispositivoDetails.getContrasenaWifi());
                    dispositivo.setSsid(dispositivoDetails.getSsid());
                    return ResponseEntity.ok(dispositivosService.save(dispositivo));
                }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (dispositivosService.findById(id).isPresent()) {
            dispositivosService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
