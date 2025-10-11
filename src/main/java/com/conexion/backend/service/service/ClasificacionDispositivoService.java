package com.conexion.backend.service.service;

import com.conexion.backend.model.ClasificacionDispositivo;
import com.conexion.backend.repository.ClasificacionDispositivoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Lógica de negocio para gestionar el catálogo de ClasificacionDispositivo.
 */
@Service
public class ClasificacionDispositivoService {

    private final ClasificacionDispositivoRepository repository;

    @Autowired
    public ClasificacionDispositivoService(ClasificacionDispositivoRepository repository) {
        this.repository = repository;
    }

    /**
     * Guarda una nueva clasificación o actualiza una existente.
     * @param clasificacion El objeto ClasificacionDispositivo a guardar.
     * @return La clasificación guardada.
     */
    public ClasificacionDispositivo save(ClasificacionDispositivo clasificacion) {
        return repository.save(clasificacion);
    }

    /**
     * Obtiene todas las clasificaciones.
     * @return Una lista de todas las clasificaciones de dispositivos.
     */
    public List<ClasificacionDispositivo> findAll() {
        return repository.findAll();
    }

    /**
     * Obtiene una clasificación por su ID.
     * @param id El ID de la clasificación.
     * @return Un Optional que contiene la clasificación, o vacío si no se encuentra.
     */
    public Optional<ClasificacionDispositivo> findById(Integer id) {
        return repository.findById(id);
    }

    /**
     * Elimina una clasificación por su ID.
     * @param id El ID de la clasificación a eliminar.
     */
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }
}
