package com.conexion.backend.service.service;

import com.conexion.backend.dto.PlanDTO;
import com.conexion.backend.exception.ResourceNotFoundException;
import com.conexion.backend.model.Plan;
import com.conexion.backend.repository.PlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlanService {

    @Autowired
    private PlanRepository planRepository;

    // Convertir Entidad a DTO
    private PlanDTO toDTO(Plan plan) {
        PlanDTO dto = new PlanDTO();
        dto.setIdPlan(plan.getIdPlan());
        dto.setNombrePlan(plan.getNombrePlan());
        dto.setVelocidad(plan.getVelocidad());
        dto.setCostoMensual(plan.getCostoMensual());
        return dto;
    }

    // Convertir DTO a Entidad
    private Plan toEntity(PlanDTO dto) {
        Plan plan = new Plan();
        plan.setIdPlan(dto.getIdPlan());
        plan.setNombrePlan(dto.getNombrePlan());
        plan.setVelocidad(dto.getVelocidad());
        plan.setCostoMensual(dto.getCostoMensual());
        return plan;
    }

    public List<PlanDTO> findAll() {
        return planRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public PlanDTO findById(Integer id) {
        Plan plan = planRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Plan no encontrado con id: " + id));
        return toDTO(plan);
    }

    public PlanDTO save(PlanDTO planDTO) {
        Plan plan = toEntity(planDTO);
        plan = planRepository.save(plan);
        return toDTO(plan);
    }

    public PlanDTO update(Integer id, PlanDTO planDTO) {
        Plan existingPlan = planRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Plan no encontrado con id: " + id));

        existingPlan.setNombrePlan(planDTO.getNombrePlan());
        existingPlan.setVelocidad(planDTO.getVelocidad());
        existingPlan.setCostoMensual(planDTO.getCostoMensual());

        Plan updatedPlan = planRepository.save(existingPlan);
        return toDTO(updatedPlan);
    }

    public void deleteById(Integer id) {
        if (!planRepository.existsById(id)) {
            throw new ResourceNotFoundException("Plan no encontrado con id: " + id);
        }
        planRepository.deleteById(id);
    }
}
