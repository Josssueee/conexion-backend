package com.conexion.backend.controller;

import com.conexion.backend.dto.ApiResponseDTO;
import com.conexion.backend.dto.PlanDTO;
import com.conexion.backend.dto.ServicioDTO;
import com.conexion.backend.service.service.ServicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/servicios")
public class ServicioController {

    private final ServicioService servicioService;

    @Autowired
    public ServicioController(ServicioService servicioService) {
        this.servicioService = servicioService;
    }

    // UC03.01: Asignar un servicio a un cliente
    @PostMapping
    public ResponseEntity<ApiResponseDTO<ServicioDTO>> asignarServicio(@RequestBody ServicioDTO servicioDTO) {
        try {
            ServicioDTO nuevoServicio = servicioService.asignarServicio(servicioDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponseDTO.<ServicioDTO>builder()
                    .success(true)
                    .message("Servicio asignado exitosamente.")
                    .data(nuevoServicio)
                    .build());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponseDTO.<ServicioDTO>builder()
                    .success(false)
                    .message(e.getMessage())
                    .build());
        }
    }

    // UC06.01: Suspender un servicio de internet
    @PutMapping("/{id}/suspender")
    public ResponseEntity<ApiResponseDTO<ServicioDTO>> suspenderServicio(@PathVariable Integer id) {
        try {
            ServicioDTO actualizado = servicioService.suspenderServicio(id);
            return ResponseEntity.ok(ApiResponseDTO.<ServicioDTO>builder()
                    .success(true)
                    .message("Servicio suspendido exitosamente.")
                    .data(actualizado)
                    .build());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponseDTO.<ServicioDTO>builder()
                    .success(false)
                    .message(e.getMessage())
                    .build());
        }
    }
    
    // UC06.02: Reactivar un servicio de internet
    @PutMapping("/{id}/reactivar")
    public ResponseEntity<ApiResponseDTO<ServicioDTO>> reactivarServicio(@PathVariable Integer id) {
        try {
            ServicioDTO actualizado = servicioService.reactivarServicio(id);
            return ResponseEntity.ok(ApiResponseDTO.<ServicioDTO>builder()
                    .success(true)
                    .message("Servicio reactivado exitosamente.")
                    .data(actualizado)
                    .build());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponseDTO.<ServicioDTO>builder()
                    .success(false)
                    .message(e.getMessage())
                    .build());
        }
    }
    
    // UC03.02: Consultar listado completo de planes
    @GetMapping("/planes")
    public ResponseEntity<ApiResponseDTO<List<PlanDTO>>> getAllPlanes() {
        List<PlanDTO> planes = servicioService.getAllPlanes();
        return ResponseEntity.ok(ApiResponseDTO.<List<PlanDTO>>builder()
                .success(true)
                .message("Listado completo de planes.")
                .data(planes)
                .build());
    }
    
    // UC05.02: Buscar servicios por estado (Ej. ACTIVO, SUSPENDIDO)
    @GetMapping("/buscar")
    public ResponseEntity<ApiResponseDTO<List<ServicioDTO>>> buscarServicios(@RequestParam String query) {
        List<ServicioDTO> servicios = servicioService.buscarServicios(query);
        return ResponseEntity.ok(ApiResponseDTO.<List<ServicioDTO>>builder()
                .success(true)
                .message("Resultados de la búsqueda de servicios.")
                .data(servicios)
                .build());
    }
}
