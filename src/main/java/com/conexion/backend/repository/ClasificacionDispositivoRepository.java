package com.conexion.backend.repository;

import com.conexion.backend.model.ClasificacionDispositivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClasificacionDispositivoRepository extends JpaRepository<ClasificacionDispositivo, Integer> {
}
