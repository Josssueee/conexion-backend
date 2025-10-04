package com.conexion.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration; // ¡Nueva Importación!
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

// Asegúrate de importar tu UserDetailsServiceImpl si lo tienes
// import com.conexion.backend.service.impl.UserDetailsServiceImpl; 

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailsService userDetailsService;

    // Inyección del servicio de detalles de usuario
    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    /**
     * Configuración del filtro de seguridad principal.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        
        http
            // 1. Deshabilitar CSRF (necesario para APIs REST sin estado)
            .csrf(csrf -> csrf.disable())

            // 2. Configurar CORS (llamamos al bean definido abajo)
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            
            // 3. Configurar la autorización de peticiones
            .authorizeHttpRequests(auth -> auth
                // Permitir acceso sin autenticación a todas las rutas de autenticación
                .requestMatchers("/api/auth/**").permitAll() 
                // Requerir autenticación para cualquier otra petición
                .anyRequest().authenticated() 
            )

            // 4. Configurar gestión de sesiones como SIN ESTADO (STATELLESS) 
            // Esto es crucial para usar tokens (como JWT) en lugar de sesiones.
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            
            // 5. Usar autenticación HTTP Basic como fallback o predeterminado
            .httpBasic(httpBasic -> httpBasic.init(http)); 
        
        return http.build();
    }
    
    /**
     * Define el bean AuthenticationManager, que coordina a los AuthenticationProviders.
     * Este método fue añadido para resolver la inyección en AuthController.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
    
    /**
     * Define el proveedor de autenticación que usa el UserDetailsService.
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    /**
     * Define el codificador de contraseñas.
     * Estamos usando NoOpPasswordEncoder (sin cifrado) por simplicidad en desarrollo.
     * En producción DEBERÍA usarse BCryptPasswordEncoder.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        // En un entorno real, usarías new BCryptPasswordEncoder();
        return NoOpPasswordEncoder.getInstance();
    }
    
    /**
     * Configuración básica de CORS para permitir todas las peticiones desde cualquier origen.
     */
    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // Permite cualquier origen (deberías restringirlo en producción)
        configuration.addAllowedOrigin("*"); 
        // Permite métodos comunes
        configuration.addAllowedMethod("*"); 
        // Permite cualquier encabezado
        configuration.addAllowedHeader("*"); 
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // Aplicar esta configuración a todas las rutas
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
