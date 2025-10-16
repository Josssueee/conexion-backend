package com.conexion.backend.service.service;

import com.conexion.backend.dto.*;
import com.conexion.backend.model.*;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class MapperService {

    // --- Cliente Mappers ---
    public ClienteDTO toClienteDTO(Cliente cliente) {
        ClienteDTO dto = new ClienteDTO();
        dto.setIdCliente(cliente.getIdCliente());
        dto.setNombre(cliente.getNombre());
        dto.setApellido(cliente.getApellido());
        dto.setTelefono(cliente.getTelefono());
        dto.setDireccion(cliente.getDireccion());
        dto.setDpi(cliente.getDpi());
        dto.setFechaRegistro(cliente.getFechaRegistro());
        dto.setEstadoCliente(cliente.getEstadoCliente());
        return dto;
    }

    public Cliente toClienteEntity(ClienteDTO dto) {
        Cliente cliente = new Cliente();
        cliente.setIdCliente(dto.getIdCliente());
        cliente.setNombre(dto.getNombre());
        cliente.setApellido(dto.getApellido());
        cliente.setTelefono(dto.getTelefono());
        cliente.setDireccion(dto.getDireccion());
        cliente.setDpi(dto.getDpi());
        return cliente;
    }

    public ClienteDTO toClienteDTOConServicios(Cliente cliente) {
        ClienteDTO dto = toClienteDTO(cliente);
        if (cliente.getServicios() != null) {
            dto.setServicios(cliente.getServicios().stream()
                    .map(this::toServicioDTO)
                    .collect(Collectors.toList()));
        }
        return dto;
    }

    // --- Servicio Mappers ---
    public ServicioDTO toServicioDTO(Servicio servicio) {
        ServicioDTO dto = new ServicioDTO();
        dto.setIdServicio(servicio.getIdServicio());
        dto.setFechaActivacion(servicio.getFechaActivacion());
        dto.setProximoPago(servicio.getProximoPago());
        dto.setEstadoServicio(servicio.getEstadoServicio());
        dto.setDescuentoMonto(servicio.getDescuentoMonto());
        if (servicio.getCliente() != null) {
            dto.setIdCliente(servicio.getCliente().getIdCliente());
        }
        if (servicio.getPlan() != null) {
            dto.setPlan(toPlanDTO(servicio.getPlan()));
        }
        if (servicio.getDispositivo() != null) {
            dto.setDispositivo(toDispositivoDTO(servicio.getDispositivo()));
        }
        if (servicio.getPagos() != null) {
            dto.setPagos(servicio.getPagos().stream().map(this::toPagoDTO).collect(Collectors.toList()));
        }
        return dto;
    }

    public Servicio toServicioEntity(ServicioDTO dto) {
        Servicio servicio = new Servicio();
        // El ID, cliente, plan y dispositivo se asignan en el servicio principal
        servicio.setFechaActivacion(dto.getFechaActivacion());
        servicio.setProximoPago(dto.getProximoPago());
        servicio.setEstadoServicio(dto.getEstadoServicio());
        servicio.setDescuentoMonto(dto.getDescuentoMonto());
        return servicio;
    }

    // --- Pago Mappers ---
    public PagoDTO toPagoDTO(Pago pago) {
        PagoDTO dto = new PagoDTO();
        dto.setIdPago(pago.getIdPago());
        dto.setFechaPago(pago.getFechaPago());
        dto.setMonto(pago.getMonto());
        dto.setMetodoPago(pago.getMetodoPago());
        dto.setDiasCubiertos(pago.getDiasCubiertos());
        dto.setComentario(pago.getComentario());
        dto.setPeriodoInicio(pago.getPeriodoInicio());
        dto.setPeriodoFin(pago.getPeriodoFin());
        dto.setEstadoPago(pago.getEstadoPago());
        if (pago.getServicio() != null) {
            dto.setIdServicio(pago.getServicio().getIdServicio());
        }
        return dto;
    }

    public Pago toPagoEntity(PagoDTO dto) {
        Pago pago = new Pago();
        // El ID y el servicio se asignan en el servicio principal
        pago.setFechaPago(dto.getFechaPago());
        pago.setMonto(dto.getMonto());
        pago.setMetodoPago(dto.getMetodoPago());
        pago.setDiasCubiertos(dto.getDiasCubiertos());
        pago.setComentario(dto.getComentario());
        pago.setPeriodoInicio(dto.getPeriodoInicio());
        pago.setPeriodoFin(dto.getPeriodoFin());
        return pago;
    }

    // --- Plan Mappers ---
    public PlanDTO toPlanDTO(Plan plan) {
        PlanDTO dto = new PlanDTO();
        dto.setIdPlan(plan.getIdPlan());
        dto.setNombrePlan(plan.getNombrePlan());
        dto.setVelocidad(plan.getVelocidad());
        dto.setCostoMensual(plan.getCostoMensual());
        return dto;
    }

    // --- Dispositivo Mappers ---
    public DispositivoDTO toDispositivoDTO(Dispositivo dispositivo) {
        DispositivoDTO dto = new DispositivoDTO();
        dto.setIdDispositivo(dispositivo.getIdDispositivo());
        dto.setModelo(dispositivo.getModelo());
        dto.setDireccionMac(dispositivo.getDireccionMac());
        dto.setIpLocal(dispositivo.getIpLocal());
        dto.setContrasenaWifi(dispositivo.getContrasenaWifi());
        dto.setSsid(dispositivo.getSsid());
        if (dispositivo.getClasificacion() != null) {
            dto.setIdClasificacion(dispositivo.getClasificacion().getIdClasificacion());
        }
        return dto;
    }
}