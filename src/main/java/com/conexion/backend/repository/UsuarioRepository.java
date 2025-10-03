package com.conexion.backend.repository;

import com.conexion.backend.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    
    // Método necesario para UC01.01: buscar un usuario por su nombre de usuario
    Optional<Usuario> findByNombreUsuario(String nombreUsuario);
}
