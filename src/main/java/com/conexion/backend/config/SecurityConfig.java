package com.conexion.backend.config;

import com.conexion.backend.security.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod; // ¡Importar HttpMethod!
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * Configuración principal de Spring Security para la aplicación API REST con JWT.
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                // 1. Deshabilitar CSRF (necesario para APIs REST sin estado)
                .csrf(AbstractHttpConfigurer::disable)

                // 2. Configurar CORS
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                
                // 3. Configurar la autorización de peticiones (¡Ajuste CLAVE aquí!)
                                .authorizeHttpRequests(auth -> auth
                                        // Endpoints públicos
                                        .requestMatchers("/", "/index.html", "/static/**").permitAll()
                                        .requestMatchers("/api/auth/**").permitAll()
                
                                        // Reglas de LECTURA (GET) para ambos roles
                                        .requestMatchers(HttpMethod.GET, "/api/usuarios/**", "/api/clientes/**", "/api/planes/**", "/api/clasificaciones/**", "/api/dispositivos/**", "/api/servicios/**", "/api/pagos/**").hasAnyAuthority("ADMINISTRADOR", "TECNICO")
                
                                        // Reglas de ESCRITURA (POST, PUT, DELETE, PATCH) solo para ADMIN
                                        .requestMatchers(HttpMethod.POST, "/api/**").hasAuthority("ADMINISTRADOR")
                                        .requestMatchers(HttpMethod.PUT, "/api/**").hasAuthority("ADMINISTRADOR")
                                        .requestMatchers(HttpMethod.PATCH, "/api/**").hasAuthority("ADMINISTRADOR")
                                        .requestMatchers(HttpMethod.DELETE, "/api/**").hasAuthority("ADMINISTRADOR")
                                        
                                        // Cualquier otra petición requiere autenticación
                                        .anyRequest().authenticated()
                                )

                // 4. Configurar gestión de sesiones como SIN ESTADO (STATELLESS)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // 5. Añadir el proveedor de autenticación
                .authenticationProvider(authenticationProvider)

                // 6. Añadir el filtro JWT
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
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
