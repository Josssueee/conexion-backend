package com.conexion.backend.dto;

import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
public class ClienteDTO {
    private Integer idCliente;
    private String nombre;
    private String apellido;
    private String telefono;
    private String direccion;
    private String dpi;
    private Date fechaRegistro;
    private String estadoCliente; // Ejemplo: 'ACTIVO', 'INACTIVO'
    private List<ServicioDTO> servicios; // Para visualizar servicios asociados
}
