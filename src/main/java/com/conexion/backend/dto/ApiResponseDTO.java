package com.conexion.backend.dto;

import lombok.Builder;
import lombok.Data;

// DTO genérico para respuestas de la API (ej. éxito/error)
@Data
@Builder
public class ApiResponseDTO<T> {
    private boolean success;
    private String message;
    private T data;
}
