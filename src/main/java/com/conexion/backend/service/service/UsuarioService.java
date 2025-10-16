package com.conexion.backend.service.service;

import com.conexion.backend.dto.RegisterRequest;
import com.conexion.backend.exception.BusinessLogicException;
import com.conexion.backend.model.Rol;
import com.conexion.backend.model.Usuario;
import com.conexion.backend.repository.RolRepository;
import com.conexion.backend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final RolRepository rolRepository; // Repositorio para buscar roles

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, RolRepository rolRepository) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.rolRepository = rolRepository;
    }

    public Usuario registerUser(RegisterRequest request) {
        // 1. Validar que el nombre de usuario no exista
        if (usuarioRepository.findByNombreUsuario(request.getUsername()).isPresent()) {
            throw new BusinessLogicException("El nombre de usuario ya existe.");
        }

        // 2. Buscar el rol en la base de datos
        Rol userRole = rolRepository.findByNombreRol(request.getRole())
                .orElseThrow(() -> new BusinessLogicException("El rol especificado no existe."));

        // 3. Crear la nueva entidad de Usuario
        Usuario newUser = new Usuario();
        newUser.setNombreUsuario(request.getUsername());
        newUser.setNombre(request.getUsername()); // Añadido para cumplir con la restricción NOT NULL
        newUser.setApellido(request.getUsername()); // Añadido para cumplir con la restricción NOT NULL
        
        // 4. Cifrar y establecer la contraseña
        newUser.setContrasena(passwordEncoder.encode(request.getPassword()));
        newUser.setRol(userRole);

        // 5. Guardar el nuevo usuario en la BD
        return usuarioRepository.save(newUser);
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

    public Usuario updateUser(Integer id, RegisterRequest request) {
        // 1. Buscar el usuario existente
        Usuario existingUser = usuarioRepository.findById(id)
                .orElseThrow(() -> new BusinessLogicException("Usuario no encontrado."));

        // 2. Validar si el nuevo nombre de usuario ya está en uso por otro usuario
        usuarioRepository.findByNombreUsuario(request.getUsername()).ifPresent(user -> {
            if (!user.getIdUsuario().equals(id)) {
                throw new BusinessLogicException("El nuevo nombre de usuario ya está en uso.");
            }
        });

        // 3. Actualizar el nombre de usuario
        existingUser.setNombreUsuario(request.getUsername());

        // 4. Actualizar la contraseña solo si se proporciona una nueva
        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            existingUser.setContrasena(passwordEncoder.encode(request.getPassword()));
        }

        // 5. Actualizar el rol
        Rol userRole = rolRepository.findByNombreRol(request.getRole())
                .orElseThrow(() -> new BusinessLogicException("El rol especificado no existe."));
        existingUser.setRol(userRole);

        // 6. Guardar los cambios
        return usuarioRepository.save(existingUser);
    }
}