package com.conexion.backend.repository;

import com.conexion.backend.model.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Integer> {
    List<Pago> findByServicioIdServicio(Integer idServicio);
    List<Pago> findByServicioClienteNombreContainingIgnoreCaseOrComentarioContainingIgnoreCase(String nombre, String comentario);
}
