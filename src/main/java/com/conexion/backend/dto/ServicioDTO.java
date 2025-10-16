package com.conexion.backend.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class ServicioDTO {
    private Integer idServicio;
    private Integer idCliente; // Solo el ID es necesario para el registro
    private Integer idPlan;
    private Integer idDispositivo;
    private Date fechaActivacion;
    private Date proximoPago;
    private String estadoServicio; // Ejemplo: 'ACTIVO', 'SUSPENDIDO'
    private BigDecimal descuentoMonto;
    private PlanDTO plan; // Datos del plan para la visualización
    private DispositivoDTO dispositivo; // Datos del dispositivo para la visualización
    private List<PagoDTO> pagos; // Para visualizar historial de pagos
}
