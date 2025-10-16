
package com.conexion.backend.repository;

import com.conexion.backend.model.Dispositivo;
import com.conexion.backend.model.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ServicioRepository extends JpaRepository<Servicio, Integer> {
    boolean existsByDispositivoAndEstadoServicio(Dispositivo dispositivo, String estado);

    @Query("SELECT s FROM Servicio s WHERE LOWER(s.cliente.nombre) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(s.cliente.apellido) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(s.dispositivo.direccionMac) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Servicio> searchByClienteOrMac(@Param("query") String query);
}

