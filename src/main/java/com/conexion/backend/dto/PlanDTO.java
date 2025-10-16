package com.conexion.backend.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class PlanDTO {
    private Integer idPlan;
    private String nombrePlan;
    private String velocidad;
    private BigDecimal costoMensual;
}
