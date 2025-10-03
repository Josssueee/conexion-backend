package com.conexion.backend.dto;

import lombok.Data;

@Data
public class DispositivoDTO {
    private Integer idDispositivo;
    private String modelo;
    private String direccionMac;
    private String ipLocal;
    private String contrasenaWifi;
    private String ssid;
    private Integer idClasificacion;
}
