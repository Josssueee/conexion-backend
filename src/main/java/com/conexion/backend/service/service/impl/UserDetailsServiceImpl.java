package com.conexion.backend.service.service.impl;

import com.conexion.backend.model.Usuario;
import com.conexion.backend.repository.UsuarioRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service // Marca esta clase como un Bean de Servicio
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public UserDetailsServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String nombreUsuario) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByNombreUsuario(nombreUsuario)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + nombreUsuario));

        // Crea la lista de autoridades (roles) para el usuario
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(usuario.getRol().getNombreRol()));

        return new org.springframework.security.core.userdetails.User(
                usuario.getNombreUsuario(),
                usuario.getContrasena(),
                authorities // Asigna los roles correctos
        );
    }
}
