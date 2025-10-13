package com.conexion.backend.config;

import com.conexion.backend.model.Rol;
import com.conexion.backend.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private RolRepository rolRepository;

    @Override
    public void run(String... args) throws Exception {
        // Solo inserta los roles si no existen, para evitar duplicados
        if (rolRepository.findByNombreRol("ADMINISTRADOR").isEmpty()) {
            Rol adminRol = new Rol();
            adminRol.setNombreRol("ADMINISTRADOR");
            rolRepository.save(adminRol);
        }

        if (rolRepository.findByNombreRol("TECNICO").isEmpty()) {
            Rol tecnicoRol = new Rol();
            tecnicoRol.setNombreRol("TECNICO");
            rolRepository.save(tecnicoRol);
        }
    }
}
