package com.conexion.backend.service.service;

import com.conexion.backend.model.Dispositivo;
import com.conexion.backend.repository.DispositivoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Lógica de negocio para la gestión de Dispositivos.
 */
@Service
public class DispositivosService {

    private final DispositivoRepository dispositivosRepository;

    @Autowired
    public DispositivosService(DispositivoRepository dispositivosRepository) {
        this.dispositivosRepository = dispositivosRepository;
    }

    public Dispositivo save(Dispositivo dispositivo) {
        return dispositivosRepository.save(dispositivo);
    }

    public List<Dispositivo> findAll() {
        return dispositivosRepository.findAll();
    }

    public Optional<Dispositivo> findById(Integer id) {
        return dispositivosRepository.findById(id);
    }

    public void deleteById(Integer id) {
        dispositivosRepository.deleteById(id);
    }
}
