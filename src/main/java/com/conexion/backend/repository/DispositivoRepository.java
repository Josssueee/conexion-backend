package com.conexion.backend.repository;

import com.conexion.backend.model.Dispositivo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface DispositivoRepository extends JpaRepository<Dispositivo, Integer> {
    List<Dispositivo> findByModeloContainingIgnoreCaseOrDireccionMacContainingIgnoreCase(String modelo, String mac);
}
