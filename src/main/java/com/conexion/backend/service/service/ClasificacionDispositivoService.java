package com.conexion.backend.service.service;

import com.conexion.backend.dto.ClasificacionDispositivoDTO;
import com.conexion.backend.exception.ResourceNotFoundException;
import com.conexion.backend.model.ClasificacionDispositivo;
import com.conexion.backend.repository.ClasificacionDispositivoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClasificacionDispositivoService {

    @Autowired
    private ClasificacionDispositivoRepository repository;

    private ClasificacionDispositivoDTO toDTO(ClasificacionDispositivo entity) {
        ClasificacionDispositivoDTO dto = new ClasificacionDispositivoDTO();
        dto.setIdClasificacion(entity.getIdClasificacion());
        dto.setNombreClasificacion(entity.getNombreClasificacion());
        return dto;
    }

    private ClasificacionDispositivo toEntity(ClasificacionDispositivoDTO dto) {
        ClasificacionDispositivo entity = new ClasificacionDispositivo();
        entity.setIdClasificacion(dto.getIdClasificacion());
        entity.setNombreClasificacion(dto.getNombreClasificacion());
        return entity;
    }

    public List<ClasificacionDispositivoDTO> findAll() {
        return repository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public ClasificacionDispositivoDTO findById(Integer id) {
        ClasificacionDispositivo entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Clasificación no encontrada con id: " + id));
        return toDTO(entity);
    }

    public ClasificacionDispositivoDTO save(ClasificacionDispositivoDTO dto) {
        ClasificacionDispositivo entity = toEntity(dto);
        entity = repository.save(entity);
        return toDTO(entity);
    }

    public ClasificacionDispositivoDTO update(Integer id, ClasificacionDispositivoDTO dto) {
        ClasificacionDispositivo existingEntity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Clasificación no encontrada con id: " + id));
        existingEntity.setNombreClasificacion(dto.getNombreClasificacion());
        ClasificacionDispositivo updatedEntity = repository.save(existingEntity);
        return toDTO(updatedEntity);
    }

    public void deleteById(Integer id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Clasificación no encontrada con id: " + id);
        }
        repository.deleteById(id);
    }
}