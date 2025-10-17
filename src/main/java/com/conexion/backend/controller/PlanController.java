package com.conexion.backend.controller;

import com.conexion.backend.dto.PlanDTO;
import com.conexion.backend.service.service.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
asdw
@RestController
@RequestMapping("/api/planes")
public class PlanController {

    @Autowired
    private PlanService planService;

    @GetMapping
    public ResponseEntity<List<PlanDTO>> getAllPlanes() {
        return ResponseEntity.ok(planService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlanDTO> getPlanById(@PathVariable Integer id) {
        return ResponseEntity.ok(planService.findById(id));
    }

    @PostMapping
    public ResponseEntity<PlanDTO> createPlan(@RequestBody PlanDTO planDTO) {
        return new ResponseEntity<>(planService.save(planDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlanDTO> updatePlan(@PathVariable Integer id, @RequestBody PlanDTO planDTO) {
        return ResponseEntity.ok(planService.update(id, planDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlan(@PathVariable Integer id) {
        planService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
