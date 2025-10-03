package com.conexion.backend.service;

import com.conexion.backend.dto.ClienteDTO;
import com.conexion.backend.model.Cliente;
import com.conexion.backend.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final MapperService mapper; // Servicio de mapeo (se definirá más adelante)

    @Autowired
    public ClienteService(ClienteRepository clienteRepository, MapperService mapper) {
        this.clienteRepository = clienteRepository;
        this.mapper = mapper;
    }

    // UC02.01: Registrar un nuevo cliente
    @Transactional
    public ClienteDTO registrarCliente(ClienteDTO clienteDTO) {
        // Regla de Negocio: Validar que el DPI no exista
        if (clienteRepository.existsByDpi(clienteDTO.getDpi())) {
            throw new RuntimeException("El DPI ya está registrado.");
        }
        // Regla de Negocio: Validar que el teléfono no exista
        if (clienteRepository.existsByTelefono(clienteDTO.getTelefono())) {
            throw new RuntimeException("El teléfono ya está registrado.");
        }

        Cliente cliente = mapper.toClienteEntity(clienteDTO);
        cliente.setFechaRegistro(new Date()); // Campo de auditoría/negocio
        cliente.setEstadoCliente("ACTIVO"); // Estado inicial
        
        Cliente nuevoCliente = clienteRepository.save(cliente);
        return mapper.toClienteDTO(nuevoCliente);
    }

    // UC02.02: Modificar los datos de un cliente existente
    @Transactional
    public ClienteDTO actualizarCliente(Integer id, ClienteDTO clienteDTO) {
        Cliente clienteExistente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + id));

        // Actualizar solo los campos permitidos
        clienteExistente.setNombre(clienteDTO.getNombre());
        clienteExistente.setApellido(clienteDTO.getApellido());
        clienteExistente.setTelefono(clienteDTO.getTelefono());
        clienteExistente.setDireccion(clienteDTO.getDireccion());
        // El DPI generalmente no se actualiza, pero se puede incluir si es un error tipográfico
        
        Cliente actualizado = clienteRepository.save(clienteExistente);
        return mapper.toClienteDTO(actualizado);
    }
    
    // UC05.01: Buscar clientes por nombre, DPI o teléfono
    @Transactional(readOnly = true)
    public List<ClienteDTO> buscarClientes(String query) {
        List<Cliente> clientes = clienteRepository.findByNombreContainingIgnoreCaseOrDpiOrTelefono(query, query, query);
        return clientes.stream()
                .map(mapper::toClienteDTO)
                .collect(Collectors.toList());
    }
    
    // UC05.03: Visualizar todos los datos de un cliente y sus servicios asociados
    @Transactional(readOnly = true)
    public ClienteDTO getClienteCompleto(Integer id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + id));
        
        // Mapea la entidad con sus colecciones (Servicios)
        return mapper.toClienteDTOConServicios(cliente);
    }
    
    // UC09.01: Eliminar un cliente de manera lógica (cambiar estado)
    @Transactional
    public void eliminarClienteLogico(Integer id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + id));
        
        // Regla de Negocio: Solo se puede inactivar si no tiene servicios activos (opcional, pero buena práctica)
        cliente.setEstadoCliente("INACTIVO");
        clienteRepository.save(cliente);
    }
}
