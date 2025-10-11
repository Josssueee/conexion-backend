package com.conexion.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
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

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailsService userDetailsService;

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
                // Permite acceso sin autenticación a todas las rutas de autenticación
                .requestMatchers("/api/auth/**").permitAll() 
                // >> ¡LÍNEA AÑADIDA PARA PRUEBAS! <<
                .requestMatchers("/api/clientes/**").permitAll() 
                // Requerir autenticación para cualquier otra petición
                .anyRequest().authenticated() 
            )

            // 4. Configurar gestión de sesiones como SIN ESTADO (STATELLESS) 
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            
            // 5. Usar autenticación HTTP Basic como fallback o predeterminado
            .httpBasic(httpBasic -> httpBasic.init(http)); 
        
        return http.build();
    }
    
    /**
     * Define el bean AuthenticationManager, que coordina a los AuthenticationProviders.
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
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
    
    /**
     * Configuración básica de CORS.
     */
    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("*"); 
        configuration.addAllowedMethod("*"); 
        configuration.addAllowedHeader("*"); 
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
