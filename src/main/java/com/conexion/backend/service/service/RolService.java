package com.conexion.backend.service.service;

import com.conexion.backend.model.Rol;
import com.conexion.backend.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Lógica de negocio para la gestión de Roles.
 */
@Service
public class RolService {

    private final RolRepository rolRepository;

    @Autowired
    public RolService(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    public Rol save(Rol rol) {
        return rolRepository.save(rol);
    }

    public List<Rol> findAll() {
        return rolRepository.findAll();
    }

    public Optional<Rol> findById(Integer id) {
        return rolRepository.findById(id);
    }
    
    public void deleteById(Integer id) {
        rolRepository.deleteById(id);
    }
}