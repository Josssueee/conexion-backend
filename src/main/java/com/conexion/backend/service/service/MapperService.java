package com.conexion.backend.service;

import com.conexion.backend.dto.*;
import com.conexion.backend.model.*;
import org.springframework.stereotype.Service;
import java.util.stream.Collectors;

@Service
public class MapperService {

    // --- Mappers de Cliente ---
    public Cliente toClienteEntity(ClienteDTO dto) {
        if (dto == null) return null;
        Cliente entity = new Cliente();
        entity.setIdCliente(dto.getIdCliente());
        entity.setNombre(dto.getNombre());
        entity.setApellido(dto.getApellido());
        entity.setTelefono(dto.getTelefono());
        entity.setDireccion(dto.getDireccion());
        entity.setDpi(dto.getDpi());
        entity.setFechaRegistro(dto.getFechaRegistro());
        entity.setEstadoCliente(dto.getEstadoCliente());
        return entity;
    }

    public ClienteDTO toClienteDTO(Cliente entity) {
        if (entity == null) return null;
        ClienteDTO dto = new ClienteDTO();
        dto.setIdCliente(entity.getIdCliente());
        dto.setNombre(entity.getNombre());
        dto.setApellido(entity.getApellido());
        dto.setTelefono(entity.getTelefono());
        dto.setDireccion(entity.getDireccion());
        dto.setDpi(entity.getDpi());
        dto.setFechaRegistro(entity.getFechaRegistro());
        dto.setEstadoCliente(entity.getEstadoCliente());
        return dto;
    }
    
    // Para UC05.03: Incluir servicios en la respuesta
    public ClienteDTO toClienteDTOConServicios(Cliente entity) {
        ClienteDTO dto = toClienteDTO(entity);
        if (entity.getServicios() != null) {
            dto.setServicios(entity.getServicios().stream()
                    .map(this::toServicioDTO)
                    .collect(Collectors.toList()));
        }
        return dto;
    }

    // --- Mappers de Servicio ---
    public Servicio toServicioEntity(ServicioDTO dto) {
        if (dto == null) return null;
        Servicio entity = new Servicio();
        entity.setIdServicio(dto.getIdServicio());
        // Las relaciones (Cliente, Plan, Dispositivo) se setean en el Service
        entity.setFechaActivacion(dto.getFechaActivacion());
        entity.setProximoPago(dto.getProximoPago());
        entity.setEstadoServicio(dto.getEstadoServicio());
        entity.setDescuentoMonto(dto.getDescuentoMonto());
        return entity;
    }
    
    public ServicioDTO toServicioDTO(Servicio entity) {
        if (entity == null) return null;
        ServicioDTO dto = new ServicioDTO();
        dto.setIdServicio(entity.getIdServicio());
        dto.setIdCliente(entity.getCliente() != null ? entity.getCliente().getIdCliente() : null);
        dto.setIdPlan(entity.getPlan() != null ? entity.getPlan().getIdPlan() : null);
        dto.setIdDispositivo(entity.getDispositivo() != null ? entity.getDispositivo().getIdDispositivo() : null);
        dto.setFechaActivacion(entity.getFechaActivacion());
        dto.setProximoPago(entity.getProximoPago());
        dto.setEstadoServicio(entity.getEstadoServicio());
        dto.setDescuentoMonto(entity.getDescuentoMonto());
        
        // Mapear Plan y Dispositivo (solo para visualización)
        dto.setPlan(toPlanDTO(entity.getPlan()));
        dto.setDispositivo(toDispositivoDTO(entity.getDispositivo()));
        
        // Mapear Pagos (solo para visualización del historial)
        if (entity.getPagos() != null) {
            dto.setPagos(entity.getPagos().stream()
                    .map(this::toPagoDTO)
                    .collect(Collectors.toList()));
        }
        
        return dto;
    }

    // --- Mappers de Pago ---
    public Pago toPagoEntity(PagoDTO dto) {
        if (dto == null) return null;
        Pago entity = new Pago();
        entity.setIdPago(dto.getIdPago());
        // El Servicio se setea en el Service
        entity.setFechaPago(dto.getFechaPago());
        entity.setMonto(dto.getMonto());
        entity.setMetodoPago(dto.getMetodoPago());
        entity.setDiasCubiertos(dto.getDiasCubiertos());
        entity.setComentario(dto.getComentario());
        entity.setPeriodoInicio(dto.getPeriodoInicio());
        entity.setPeriodoFin(dto.getPeriodoFin());
        return entity;
    }

    public PagoDTO toPagoDTO(Pago entity) {
        if (entity == null) return null;
        PagoDTO dto = new PagoDTO();
        // Corrección: Debe ser setIdPago() ya que el campo es 'idPago'
        dto.setIdPago(entity.getIdPago()); 
        dto.setIdServicio(entity.getServicio() != null ? entity.getServicio().getIdServicio() : null);
        dto.setFechaPago(entity.getFechaPago());
        dto.setMonto(entity.getMonto());
        dto.setMetodoPago(entity.getMetodoPago());
        dto.setDiasCubiertos(entity.getDiasCubiertos());
        dto.setComentario(entity.getComentario());
        dto.setPeriodoInicio(entity.getPeriodoInicio());
        dto.setPeriodoFin(entity.getPeriodoFin());
        return dto;
    }
    
    // --- Mappers de Plan y Dispositivo (simples) ---
    public PlanDTO toPlanDTO(Plan entity) {
        if (entity == null) return null;
        PlanDTO dto = new PlanDTO();
        dto.setIdPlan(entity.getIdPlan());
        dto.setNombrePlan(entity.getNombrePlan());
        dto.setCostoMensual(entity.getCostoMensual());
        dto.setVelocidad(entity.getVelocidad());
        return dto;
    }
    
    public DispositivoDTO toDispositivoDTO(Dispositivo entity) {
        if (entity == null) return null;
        DispositivoDTO dto = new DispositivoDTO();
        dto.setIdDispositivo(entity.getIdDispositivo());
        dto.setModelo(entity.getModelo());
        dto.setDireccionMac(entity.getDireccionMac());
        dto.setIpLocal(entity.getIpLocal());
        dto.setContrasenaWifi(entity.getContrasenaWifi());
        dto.setSsid(entity.getSsid());
        dto.setIdClasificacion(entity.getClasificacion() != null ? entity.getClasificacion().getIdClasificacion() : null);
        return dto;
    }
}
