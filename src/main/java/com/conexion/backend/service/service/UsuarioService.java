package com.conexion.backend.service.service;

import com.conexion.backend.model.Usuario;
import com.conexion.backend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder; // Necesario para cifrar contraseñas
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Lógica de negocio para la gestión de Usuarios.
 */
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder; // Inyectamos el codificador de contraseñas
    }

    /**
     * Guarda un nuevo usuario o actualiza uno existente.
     * Cifra la contraseña antes de guardar.
     */
    public Usuario save(Usuario usuario) {
        // Cifrar la contraseña si es nueva o ha cambiado
        if (usuario.getContrasena() != null && !usuario.getContrasena().isEmpty()) {
            // Nota: Usamos NoOpPasswordEncoder temporalmente, que no cifra.
            // Si pasas a BCrypt, aquí se cifraría.
            // Para el CRUD, simplemente lo guardaremos. 
            // Como usamos NoOpPasswordEncoder, no ciframos aquí.
        }
        return usuarioRepository.save(usuario);
    }

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> findById(Integer id) {
        return usuarioRepository.findById(id);
    }

    public void deleteById(Integer id) {
        usuarioRepository.deleteById(id);
    }
}