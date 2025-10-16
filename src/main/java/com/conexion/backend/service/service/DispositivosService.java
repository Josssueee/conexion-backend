package com.conexion.backend.service.service;

import com.conexion.backend.dto.DispositivoDTO;
import com.conexion.backend.exception.ResourceNotFoundException;
import com.conexion.backend.model.ClasificacionDispositivo;
import com.conexion.backend.model.Dispositivo;
import com.conexion.backend.repository.ClasificacionDispositivoRepository;
import com.conexion.backend.repository.DispositivoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DispositivosService {

    @Autowired
    private DispositivoRepository dispositivoRepository;
    @Autowired
    private ClasificacionDispositivoRepository clasificacionRepository;

    private DispositivoDTO toDTO(Dispositivo entity) {
        DispositivoDTO dto = new DispositivoDTO();
        dto.setIdDispositivo(entity.getIdDispositivo());
        dto.setModelo(entity.getModelo());
        dto.setDireccionMac(entity.getDireccionMac());
        dto.setIpLocal(entity.getIpLocal());
        dto.setContrasenaWifi(entity.getContrasenaWifi());
        dto.setSsid(entity.getSsid());
        if (entity.getClasificacion() != null) {
            dto.setIdClasificacion(entity.getClasificacion().getIdClasificacion());
        }
        return dto;
    }

    private Dispositivo toEntity(DispositivoDTO dto) {
        Dispositivo entity = new Dispositivo();
        // El ID se maneja por separado en save/update
        entity.setModelo(dto.getModelo());
        entity.setDireccionMac(dto.getDireccionMac());
        entity.setIpLocal(dto.getIpLocal());
        entity.setContrasenaWifi(dto.getContrasenaWifi());
        entity.setSsid(dto.getSsid());

        if (dto.getIdClasificacion() != null) {
            ClasificacionDispositivo clasificacion = clasificacionRepository.findById(dto.getIdClasificacion())
                    .orElseThrow(() -> new ResourceNotFoundException("Clasificación no encontrada con id: " + dto.getIdClasificacion()));
            entity.setClasificacion(clasificacion);
        }
        return entity;
    }

    public List<DispositivoDTO> findAll() {
        return dispositivoRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public DispositivoDTO findById(Integer id) {
        Dispositivo entity = dispositivoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Dispositivo no encontrado con id: " + id));
        return toDTO(entity);
    }

    public DispositivoDTO save(DispositivoDTO dto) {
        Dispositivo entity = toEntity(dto);
        entity = dispositivoRepository.save(entity);
        return toDTO(entity);
    }

    public DispositivoDTO update(Integer id, DispositivoDTO dto) {
        Dispositivo existingEntity = dispositivoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Dispositivo no encontrado con id: " + id));

        // Actualizar campos desde el DTO
        existingEntity.setModelo(dto.getModelo());
        existingEntity.setDireccionMac(dto.getDireccionMac());
        existingEntity.setIpLocal(dto.getIpLocal());
        existingEntity.setContrasenaWifi(dto.getContrasenaWifi());
        existingEntity.setSsid(dto.getSsid());

        if (dto.getIdClasificacion() != null) {
            ClasificacionDispositivo clasificacion = clasificacionRepository.findById(dto.getIdClasificacion())
                    .orElseThrow(() -> new ResourceNotFoundException("Clasificación no encontrada con id: " + dto.getIdClasificacion()));
            existingEntity.setClasificacion(clasificacion);
        }

        Dispositivo updatedEntity = dispositivoRepository.save(existingEntity);
        return toDTO(updatedEntity);
    }

    public void deleteById(Integer id) {
        if (!dispositivoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Dispositivo no encontrado con id: " + id);
        }
        dispositivoRepository.deleteById(id);
    }

    public List<DispositivoDTO> search(String query) {
        return dispositivoRepository.findByModeloContainingIgnoreCaseOrDireccionMacContainingIgnoreCase(query, query)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }
}
