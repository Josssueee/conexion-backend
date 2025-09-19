package com.conexion.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "clasificacion_dispositivos")
@Data
@NoArgsConstructor
public class ClasificacionDispositivo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idClasificacion;

    @Column(nullable = false, unique = true)
    private String nombreClasificacion;
}
