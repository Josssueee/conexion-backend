package com.conexion.backend.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class PagoDTO {
    private Integer idPago;
    private Integer idServicio;
    private Date fechaPago;
    private BigDecimal monto;
    private String metodoPago;
    private Integer diasCubiertos;
    private String comentario;
    private Date periodoInicio;
    private Date periodoFin;
    // Campo opcional para manejar la 'Regulación' (UC08.01)
    private boolean requiereRegulacion; 
    private BigDecimal montoRegulacion;
}
