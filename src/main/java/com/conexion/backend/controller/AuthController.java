package com.conexion.backend.controller;

import com.conexion.backend.dto.LoginRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    // AuthenticationManager no es un bean simple, requiere un Provider, 
    // lo inyectamos aquí para delegarle el proceso de login.
    public AuthController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        try {
            // 1. Crear el objeto de autenticación con las credenciales
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getNombreUsuario(),
                            loginRequest.getContrasena()
                    )
            );
            
            // Si la autenticación es exitosa, Spring Security actualiza el contexto.
            // Aquí, típicamente se genera un JWT, pero por ahora solo retornamos éxito.
            
            return ResponseEntity.ok("Login exitoso para el usuario: " + authentication.getName());

        } catch (UsernameNotFoundException e) {
            // Captura si el UserDetailsService no encuentra el usuario
            return ResponseEntity.status(401).body("Usuario no encontrado.");
        } catch (AuthenticationException e) {
            // Captura cualquier otra falla de autenticación (ej: contraseña incorrecta)
            return ResponseEntity.status(401).body("Credenciales inválidas.");
        }
    }
}
