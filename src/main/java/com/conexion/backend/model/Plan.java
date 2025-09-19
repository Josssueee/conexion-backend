package com.conexion.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Entity
@Table(name = "planes")
@Data
@NoArgsConstructor
public class Plan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPlan;

    @Column(nullable = false, unique = true)
    private String nombrePlan;

    @Column(nullable = false)
    private String velocidad;

    @Column(nullable = false)
    private BigDecimal costoMensual;
}