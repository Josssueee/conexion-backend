package com.conexion.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO utilizado para la respuesta de autenticación (login).
 * Contiene el token JWT y el nombre de usuario.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    
    // Token JWT generado
    private String token;
    
    // Nombre del usuario autenticado
    private String nombreUsuario;

    // Mensaje opcional de respuesta.
    // Aunque el AuthController que definimos antes solo usa token y nombreUsuario,
    // si lo necesitas para mensajes de error, puedes dejarlo:
    private String message;
}