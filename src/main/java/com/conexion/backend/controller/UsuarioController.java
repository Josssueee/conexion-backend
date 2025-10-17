package com.conexion.backend.controller;

import com.conexion.backend.model.Usuario;
import com.conexion.backend.service.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la consulta de Usuarios.
 * URL Base: /api/usuariosasee
 */
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> findAll() {
        return ResponseEntity.ok(usuarioService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> findById(@PathVariable Integer id) {
        return usuarioService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> updateUser(@PathVariable Integer id, @RequestBody com.conexion.backend.dto.RegisterRequest request) {
        try {
            Usuario updatedUser = usuarioService.updateUser(id, request);
            return ResponseEntity.ok(updatedUser);
        } catch (com.conexion.backend.exception.BusinessLogicException e) {
            return ResponseEntity.badRequest().build(); // O un manejo de error más específico
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        usuarioService.deleteById(id);
        return ResponseEntity.noContent().build(); // HTTP 204 No Content
    }
}