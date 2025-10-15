package com.conexion.backend.service.service;

import com.conexion.backend.dto.DispositivoDTO;
import com.conexion.backend.dto.PlanDTO;
import com.conexion.backend.dto.ServicioDTO;
import com.conexion.backend.model.*;
import com.conexion.backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServicioService {

    private final ServicioRepository servicioRepository;
    private final ClienteRepository clienteRepository;
    private final PlanRepository planRepository;
    private final DispositivoRepository dispositivoRepository;
    private final MapperService mapper;

    @Autowired
    public ServicioService(ServicioRepository servicioRepository, ClienteRepository clienteRepository, 
                           PlanRepository planRepository, DispositivoRepository dispositivoRepository, MapperService mapper) {
        this.servicioRepository = servicioRepository;
        this.clienteRepository = clienteRepository;
        this.planRepository = planRepository;
        this.dispositivoRepository = dispositivoRepository;
        this.mapper = mapper;
    }

    // UC03.01: Asignar un servicio a un cliente
    @Transactional
    public ServicioDTO asignarServicio(ServicioDTO servicioDTO) {
        Cliente cliente = clienteRepository.findById(servicioDTO.getIdCliente())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado."));
        Plan plan = planRepository.findById(servicioDTO.getIdPlan())
                .orElseThrow(() -> new RuntimeException("Plan no encontrado."));
        Dispositivo dispositivo = dispositivoRepository.findById(servicioDTO.getIdDispositivo())
                .orElseThrow(() -> new RuntimeException("Dispositivo no encontrado."));
        
        // Regla de Negocio: Verificar que el dispositivo no esté ya asignado a otro servicio activo
        if (servicioRepository.existsByDispositivoAndEstadoServicio(dispositivo, "ACTIVO")) {
            throw new RuntimeException("El dispositivo ya está en uso con un servicio activo.");
        }

        Servicio servicio = mapper.toServicioEntity(servicioDTO);
        servicio.setCliente(cliente);
        servicio.setPlan(plan);
        servicio.setDispositivo(dispositivo);
        servicio.setFechaActivacion(new Date());
        servicio.setProximoPago(calcularFechaProximoPago(new Date())); // Lógica de negocio para la fecha de corte
        servicio.setEstadoServicio("ACTIVO");
        servicio.setDescuentoMonto(BigDecimal.ZERO); // Inicialmente sin descuento

        Servicio nuevoServicio = servicioRepository.save(servicio);
        return mapper.toServicioDTO(nuevoServicio);
    }
    
    // UC06.01: Suspender un servicio de internet
    @Transactional
    public ServicioDTO suspenderServicio(Integer idServicio) {
        Servicio servicio = servicioRepository.findById(idServicio)
                .orElseThrow(() -> new RuntimeException("Servicio no encontrado."));
        
        // Regla de Negocio: Solo se puede suspender si está ACTIVO
        if (!"ACTIVO".equals(servicio.getEstadoServicio())) {
            throw new RuntimeException("El servicio no está ACTIVO para ser suspendido.");
        }
        
        servicio.setEstadoServicio("SUSPENDIDO");
        Servicio actualizado = servicioRepository.save(servicio);
        return mapper.toServicioDTO(actualizado);
    }
    
    // UC06.02: Reactivar un servicio de internet
    @Transactional
    public ServicioDTO reactivarServicio(Integer idServicio) {
        Servicio servicio = servicioRepository.findById(idServicio)
                .orElseThrow(() -> new RuntimeException("Servicio no encontrado."));
        
        // Regla de Negocio: Solo se puede reactivar si está SUSPENDIDO
        if (!"SUSPENDIDO".equals(servicio.getEstadoServicio())) {
            throw new RuntimeException("El servicio no está SUSPENDIDO para ser reactivado.");
        }
        
        // Aquí se podría integrar la lógica de UC08.01 para calcular la "Regulación" si es necesario
        
        servicio.setEstadoServicio("ACTIVO");
        Servicio actualizado = servicioRepository.save(servicio);
        return mapper.toServicioDTO(actualizado);
    }

    // Lógica interna para la fecha de corte (Ejemplo: un mes después)
    private Date calcularFechaProximoPago(Date fechaActivacion) {
        // Implementación simple: sumar 30 días. En la vida real, usar Calendar o JodaTime.
        return new Date(fechaActivacion.getTime() + (30L * 24 * 60 * 60 * 1000));
    }
    
    // UC03.02: Consultar listado completo de planes
    @Transactional(readOnly = true)
    public List<PlanDTO> getAllPlanes() {
        return planRepository.findAll().stream()
                .map(mapper::toPlanDTO)
                .collect(Collectors.toList());
    }

    // UC03.03: Consultar inventario de dispositivos
    @Transactional(readOnly = true)
    public List<DispositivoDTO> getAllDispositivos() {
        return dispositivoRepository.findAll().stream()
                .map(mapper::toDispositivoDTO)
                .collect(Collectors.toList());
    }
    
    // UC05.02: Buscar servicios por cliente o estado
    @Transactional(readOnly = true)
    public List<ServicioDTO> buscarServicios(String query) {
        List<Servicio> servicios = servicioRepository.searchByClienteOrMac(query);
        return servicios.stream()
                .map(mapper::toServicioDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ServicioDTO> findAll() {
        return servicioRepository.findAll().stream()
                .map(mapper::toServicioDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public ServicioDTO updateServicio(Integer id, ServicioDTO dto) {
        Servicio existingServicio = servicioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Servicio no encontrado."));

        Cliente cliente = clienteRepository.findById(dto.getIdCliente())
            .orElseThrow(() -> new RuntimeException("Cliente no encontrado."));
        Plan plan = planRepository.findById(dto.getIdPlan())
            .orElseThrow(() -> new RuntimeException("Plan no encontrado."));
        Dispositivo dispositivo = dispositivoRepository.findById(dto.getIdDispositivo())
            .orElseThrow(() -> new RuntimeException("Dispositivo no encontrado."));

        existingServicio.setCliente(cliente);
        existingServicio.setPlan(plan);
        existingServicio.setDispositivo(dispositivo);
        existingServicio.setFechaActivacion(dto.getFechaActivacion());
        existingServicio.setProximoPago(dto.getProximoPago());
        existingServicio.setEstadoServicio(dto.getEstadoServicio());
        existingServicio.setDescuentoMonto(dto.getDescuentoMonto());

        Servicio updatedServicio = servicioRepository.save(existingServicio);
        return mapper.toServicioDTO(updatedServicio);
    }

    @Transactional
    public void deleteServicio(Integer id) {
        if (!servicioRepository.existsById(id)) {
            throw new RuntimeException("Servicio no encontrado.");
        }
        servicioRepository.deleteById(id);
    }
}
