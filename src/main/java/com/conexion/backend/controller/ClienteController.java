package com.conexion.backend.controller;

import com.conexion.backend.dto.ApiResponseDTO;
import com.conexion.backend.dto.ClienteDTO;
import com.conexion.backend.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    @Autowired
    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    // UC02.01: Registrar un nuevo cliente
    @PostMapping
    public ResponseEntity<ApiResponseDTO<ClienteDTO>> registrarCliente(@RequestBody ClienteDTO clienteDTO) {
        try {
            ClienteDTO nuevoCliente = clienteService.registrarCliente(clienteDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponseDTO.<ClienteDTO>builder()
                    .success(true)
                    .message("Cliente registrado exitosamente.")
                    .data(nuevoCliente)
                    .build());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponseDTO.<ClienteDTO>builder()
                    .success(false)
                    .message(e.getMessage())
                    .build());
        }
    }
    
    // UC02.02: Modificar los datos de un cliente existente
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<ClienteDTO>> actualizarCliente(@PathVariable Integer id, @RequestBody ClienteDTO clienteDTO) {
        try {
            ClienteDTO actualizado = clienteService.actualizarCliente(id, clienteDTO);
            return ResponseEntity.ok(ApiResponseDTO.<ClienteDTO>builder()
                    .success(true)
                    .message("Cliente actualizado exitosamente.")
                    .data(actualizado)
                    .build());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponseDTO.<ClienteDTO>builder()
                    .success(false)
                    .message(e.getMessage())
                    .build());
        }
    }

    // UC05.01: Buscar clientes por nombre, DPI, o teléfono
    @GetMapping("/buscar")
    public ResponseEntity<ApiResponseDTO<List<ClienteDTO>>> buscarClientes(@RequestParam String query) {
        List<ClienteDTO> clientes = clienteService.buscarClientes(query);
        return ResponseEntity.ok(ApiResponseDTO.<List<ClienteDTO>>builder()
                .success(true)
                .message("Resultados de la búsqueda.")
                .data(clientes)
                .build());
    }

    // UC05.03: Visualizar todos los datos de un cliente y sus servicios asociados
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<ClienteDTO>> getCliente(@PathVariable Integer id) {
        try {
            ClienteDTO cliente = clienteService.getClienteCompleto(id);
            return ResponseEntity.ok(ApiResponseDTO.<ClienteDTO>builder()
                    .success(true)
                    .message("Datos completos del cliente.")
                    .data(cliente)
                    .build());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponseDTO.<ClienteDTO>builder()
                    .success(false)
                    .message(e.getMessage())
                    .build());
        }
    }

    // UC09.01: Eliminar un cliente de manera lógica
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<Void>> eliminarClienteLogico(@PathVariable Integer id) {
        try {
            clienteService.eliminarClienteLogico(id);
            return ResponseEntity.ok(ApiResponseDTO.<Void>builder()
                    .success(true)
                    .message("Cliente inactivado exitosamente (eliminación lógica).")
                    .build());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponseDTO.<Void>builder()
                    .success(false)
                    .message(e.getMessage())
                    .build());
        }
    }
}
