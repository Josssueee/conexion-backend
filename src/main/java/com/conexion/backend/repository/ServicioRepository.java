package com.conexion.backend.repository;

import com.conexion.backend.model.Dispositivo;
import com.conexion.backend.model.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ServicioRepository extends JpaRepository<Servicio, Integer> {
    
    // Para UC03.01 (Regla de Negocio: Dispositivo no asignado a servicio activo)
    boolean existsByDispositivoAndEstadoServicio(Dispositivo dispositivo, String estadoServicio);

    // Para UC05.02 (Búsqueda por estado)
    List<Servicio> findByEstadoServicioContainingIgnoreCase(String estadoServicio);
}
