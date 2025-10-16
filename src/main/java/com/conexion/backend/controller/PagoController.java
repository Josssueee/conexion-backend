package com.conexion.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/pagos")
public class PagoController {

    private final PagoService pagoService;

    @Autowired
    public PagoController(PagoService pagoService) {
        this.pagoService = pagoService;
    }

    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<PagoDTO>>> findAll() {
        List<PagoDTO> pagos = pagoService.findAll();
        return ResponseEntity.ok(ApiResponseDTO.<List<PagoDTO>>builder()
                .success(true)
                .message("Historial de todos los pagos.")
                .data(pagos)
                .build());
    }

    // UC04.01: Registrar un nuevo pago
    @PostMapping
    public ResponseEntity<ApiResponseDTO<PagoDTO>> registrarPago(@RequestBody PagoDTO pagoDTO) {
        try {
            PagoDTO nuevoPago = pagoService.registrarPago(pagoDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponseDTO.<PagoDTO>builder()
                    .success(true)
                    .message("Pago registrado y servicio actualizado exitosamente.")
                    .data(nuevoPago)
                    .build());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponseDTO.<PagoDTO>builder()
                    .success(false)
                    .message(e.getMessage())
                    .build());
        }
    }

    // UC04.02: Consultar el historial de pagos de un servicio
    @GetMapping("/servicio/{idServicio}")
    public ResponseEntity<ApiResponseDTO<List<PagoDTO>>> getHistorialPagos(@PathVariable Integer idServicio) {
        List<PagoDTO> historial = pagoService.getHistorialPagosPorServicio(idServicio);
        return ResponseEntity.ok(ApiResponseDTO.<List<PagoDTO>>builder()
                .success(true)
                .message("Historial de pagos del servicio " + idServicio)
                .data(historial)
                .build());
    }

    @GetMapping("/query")
    public ResponseEntity<ApiResponseDTO<List<PagoDTO>>> search(@RequestParam String query) {
        List<PagoDTO> pagos = pagoService.search(query);
        return ResponseEntity.ok(ApiResponseDTO.<List<PagoDTO>>builder()
                .success(true)
                .message("Resultado de la búsqueda de pagos.")
                .data(pagos)
                .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<PagoDTO>> anularPago(@PathVariable Integer id) {
        PagoDTO pagoAnulado = pagoService.anularPago(id);
        return ResponseEntity.ok(ApiResponseDTO.<PagoDTO>builder()
                .success(true)
                .message("Pago anulado exitosamente.")
                .data(pagoAnulado)
                .build());
    }

    @PatchMapping("/{id}/comentario")
    public ResponseEntity<ApiResponseDTO<PagoDTO>> updateComentario(@PathVariable Integer id, @RequestBody String comentario) {
        PagoDTO pagoActualizado = pagoService.updateComentario(id, comentario);
        return ResponseEntity.ok(ApiResponseDTO.<PagoDTO>builder()
                .success(true)
                .message("Comentario del pago actualizado.")
                .data(pagoActualizado)
                .build());
    }
}
