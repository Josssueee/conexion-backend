package com.conexion.backend.repository;

import com.conexion.backend.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    
    // Para UC02.01 (Reglas de Negocio: Validar unicidad)
    boolean existsByDpi(String dpi);
    boolean existsByTelefono(String telefono);

    // Para UC05.01 (Búsqueda de clientes)
    List<Cliente> findByNombreContainingIgnoreCaseOrDpiOrTelefono(String nombre, String dpi, String telefono);
}
