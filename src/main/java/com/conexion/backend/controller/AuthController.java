package com.conexion.backend.controller;

  import com.conexion.backend.dto.AuthResponse;
  import com.conexion.backend.dto.LoginRequest;
  import com.conexion.backend.dto.RegisterRequest;
  import com.conexion.backend.service.service.UsuarioService;
  import com.conexion.backend.util.JwtUtils;
  import org.springframework.http.ResponseEntity;
  import org.springframework.security.authentication.AuthenticationManager;
  import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
  import org.springframework.security.core.AuthenticationException;
  import org.springframework.security.core.userdetails.UserDetails;
  import org.springframework.security.core.userdetails.UserDetailsService;
  import org.springframework.security.core.userdetails.UsernameNotFoundException;
  import org.springframework.web.bind.annotation.*;

  @RestController
  @RequestMapping("/api/auth")
  public class AuthController {

      private final AuthenticationManager authenticationManager;
      private final UserDetailsService userDetailsService;
      private final UsuarioService usuarioService; // Servicio para registrar usuarios
      private final JwtUtils jwtUtils;

      public AuthController(
              AuthenticationManager authenticationManager,
              UserDetailsService userDetailsService,
              UsuarioService usuarioService, // Inyectar servicio
              JwtUtils jwtUtils
      ) {
          this.authenticationManager = authenticationManager;
          this.userDetailsService = userDetailsService;
          this.usuarioService = usuarioService;
          this.jwtUtils = jwtUtils;
      }

      @PostMapping("/register")
      public ResponseEntity<AuthResponse> registerUser(@RequestBody RegisterRequest registerRequest) {
          usuarioService.registerUser(registerRequest);
          // Devolvemos una respuesta simple de éxito
          return ResponseEntity.ok(new AuthResponse(null, registerRequest.getUsername(), "Usuario registrado exitosamente."));
      }

      @PostMapping("/login")
      public ResponseEntity<AuthResponse> authenticateUser(@RequestBody LoginRequest loginRequest) {
          try {
              authenticationManager.authenticate(
                      new UsernamePasswordAuthenticationToken(
                              loginRequest.getUsername(),
                              loginRequest.getPassword()
                      )
              );

              final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());
              final String jwt = jwtUtils.generateToken(userDetails);

              return ResponseEntity.ok(new AuthResponse(jwt, userDetails.getUsername(), "Autenticación exitosa."));

          } catch (UsernameNotFoundException e) {
              return ResponseEntity.status(401).body(new AuthResponse(null, null, "Usuario no encontrado."));

          } catch (AuthenticationException e) {
              return ResponseEntity.status(401).body(new AuthResponse(null, null, "Credenciales inválidas."));
          }
      }
  }