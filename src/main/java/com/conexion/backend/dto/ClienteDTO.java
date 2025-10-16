package com.conexion.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
public class ClienteDTO {
    private Integer idCliente;

    @NotBlank(message = "El nombre no puede estar vacío.")
    @Size(max = 50, message = "El nombre no puede tener más de 50 caracteres.")
    private String nombre;

    @NotBlank(message = "El apellido no puede estar vacío.")
    @Size(max = 50, message = "El apellido no puede tener más de 50 caracteres.")
    private String apellido;

    @NotBlank(message = "El teléfono no puede estar vacío.")
    @Size(min = 8, max = 8, message = "El teléfono debe tener 8 dígitos.")
    private String telefono;

    @Size(max = 255, message = "La dirección no puede tener más de 255 caracteres.")
    private String direccion;

    @NotBlank(message = "El DPI no puede estar vacío.")
    @Size(min = 13, max = 13, message = "El DPI debe tener 13 dígitos.")
    private String dpi;
    
    private Date fechaRegistro;
    private String estadoCliente; // Ejemplo: 'ACTIVO', 'INACTIVO'
    private List<ServicioDTO> servicios; // Para visualizar servicios asociados
}
