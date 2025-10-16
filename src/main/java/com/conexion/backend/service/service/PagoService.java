package com.conexion.backend.service.service;

import com.conexion.backend.dto.PagoDTO;
import com.conexion.backend.model.Pago;
import com.conexion.backend.model.Servicio;
import com.conexion.backend.repository.PagoRepository;
import com.conexion.backend.repository.ServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PagoService {

    private final PagoRepository pagoRepository;
    private final ServicioRepository servicioRepository;
    private final MapperService mapper;

    @Autowired
    public PagoService(PagoRepository pagoRepository, ServicioRepository servicioRepository, MapperService mapper) {
        this.pagoRepository = pagoRepository;
        this.servicioRepository = servicioRepository;
        this.mapper = mapper;
    }

    // UC04.01: Registrar un nuevo pago para un servicio
    @Transactional
    public PagoDTO registrarPago(PagoDTO pagoDTO) {
        Servicio servicio = servicioRepository.findById(pagoDTO.getIdServicio())
                .orElseThrow(() -> new RuntimeException("Servicio no encontrado para el pago."));

        // Lógica de Negocio (UC08.01 - Regulación): Determinar si el pago incluye regulación
        // Esta lógica es compleja y se simula aquí, en un entorno real debe ser un cálculo exacto
        if (pagoDTO.isRequiereRegulacion()) {
            // Aplicar la lógica de cálculo de regulación aquí
            // pagoDTO.setMonto(pagoDTO.getMonto().add(pagoDTO.getMontoRegulacion()));
        }

        Pago pago = mapper.toPagoEntity(pagoDTO);
        pago.setServicio(servicio);
        pago.setFechaPago(new Date());
        pago.setEstadoPago("REALIZADO"); // <-- AÑADIDO: Estado inicial

        Pago nuevoPago = pagoRepository.save(pago);
        
        // Regla de Negocio: Actualizar la fecha de próximo pago del servicio
        // y cambiar el estado si estaba SUSPENDIDO
        servicio.setProximoPago(pago.getPeriodoFin()); // Establecer nueva fecha de corte
        servicio.setEstadoServicio("ACTIVO");
        servicioRepository.save(servicio); 

        return mapper.toPagoDTO(nuevoPago);
    }

    @Transactional
    public PagoDTO anularPago(Integer id) {
        Pago pago = pagoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pago no encontrado."));
        pago.setEstadoPago("ANULADO");
        // Opcional: Lógica para revertir el efecto del pago en el servicio.
        // Por simplicidad, aquí solo se anula el pago.
        return mapper.toPagoDTO(pagoRepository.save(pago));
    }

    @Transactional
    public PagoDTO updateComentario(Integer id, String comentario) {
        Pago pago = pagoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pago no encontrado."));
        pago.setComentario(comentario);
        return mapper.toPagoDTO(pagoRepository.save(pago));
    }

    // UC04.02: Consultar el historial de pagos de un cliente (por ID de servicio)
    @Transactional(readOnly = true)
    public List<PagoDTO> getHistorialPagosPorServicio(Integer idServicio) {
        List<Pago> pagos = pagoRepository.findByServicioIdServicio(idServicio);
        return pagos.stream()
                .map(mapper::toPagoDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PagoDTO> findAll() {
        return pagoRepository.findAll().stream()
                .map(mapper::toPagoDTO)
                .collect(Collectors.toList());
    }

    public List<PagoDTO> search(String query) {
        return pagoRepository.findByServicioClienteNombreContainingIgnoreCaseOrComentarioContainingIgnoreCase(query, query)
                .stream().map(mapper::toPagoDTO).collect(Collectors.toList());
    }
}
