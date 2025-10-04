package com.conexion.backend.dto;

public class LoginRequest {

    private String nombreUsuario;
    private String contrasena;

    // Constructor vacío (necesario para Jackson)
    public LoginRequest() {
    }

    // Getters
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    // Setters
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
}
