package com.conexion.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "dispositivos")
@Data
@NoArgsConstructor
public class Dispositivo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idDispositivo;

    @Column(nullable = false)
    private String modelo;

    @Column(nullable = false, unique = true)
    private String direccionMac;

    @Column(nullable = false)
    private String ipLocal;

    @Column(nullable = false)
    private String contrasenaWifi;

    @Column(nullable = false)
    private String ssid;
    
    @ManyToOne
    @JoinColumn(name = "id_clasificacion", nullable = false)
    private ClasificacionDispositivo clasificacion;
}