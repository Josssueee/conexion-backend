package com.conexion.backend.repository;

import com.conexion.backend.model.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Integer> {
    
    // Para UC04.02: Obtener historial de pagos por ID de servicio
    List<Pago> findByServicioIdServicio(Integer idServicio);
}
