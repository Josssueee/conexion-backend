package com.conexion.backend.service.service.impl;

import com.conexion.backend.model.Usuario;
import com.conexion.backend.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service // Marca esta clase como un Bean de Servicio
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    // Inyección de dependencia de tu repositorio de usuarios
    public UserDetailsServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Carga el usuario por su nombre de usuario. Este es el método central
     * que usa Spring Security para buscar credenciales.
     */
    @Override
    public UserDetails loadUserByUsername(String nombreUsuario) throws UsernameNotFoundException {
        // Busca el usuario en la base de datos usando tu repositorio
        Usuario usuario = usuarioRepository.findByNombreUsuario(nombreUsuario)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + nombreUsuario));

        // Construye un objeto UserDetails que Spring Security pueda entender
        // Usamos el nombre de usuario, la contraseña y una lista vacía de autoridades (roles)
        return new org.springframework.security.core.userdetails.User(
                usuario.getNombreUsuario(),
                usuario.getContrasena(),
                new ArrayList<>() // Por ahora, pasamos una lista vacía de roles/autoridades
        );
    }
}
