package com.conexion.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
// No se necesita importar java.util.stream.Collectors;

@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
public class Usuario implements UserDetails { // Implementar UserDetails es crucial
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUsuario;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellido;

    @Column(nullable = false, unique = true)
    private String nombreUsuario;

    @Column(nullable = false)
    private String contrasena;

    @ManyToOne(fetch = FetchType.EAGER) // Asegura que el Rol se cargue junto con el Usuario
    @JoinColumn(name = "id_rol", nullable = false)
    private Rol rol;
    
    // -------------------------------------------------------------------------
    // MÉTODOS DE LA INTERFAZ USERDETAILS
    // -------------------------------------------------------------------------

    /**
     * Retorna las autoridades (roles) que el usuario tiene.
     * Usa getNombreRol() para coincidir con tu entidad Rol.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.rol != null && this.rol.getNombreRol() != null) {
            // Utilizamos getNombreRol() para obtener la cadena del rol (ej: "ADMIN")
            return Collections.singletonList(new SimpleGrantedAuthority(this.rol.getNombreRol()));
        }
        return Collections.emptyList();
    }
    
    @Override
    public String getPassword() {
        return this.contrasena;
    }

    @Override
    public String getUsername() {
        return this.nombreUsuario;
    }

    // El resto de los métodos se dejan en 'true' por defecto:
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
