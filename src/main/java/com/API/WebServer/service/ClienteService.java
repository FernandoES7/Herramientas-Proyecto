package com.API.WebServer.service;

import com.API.WebServer.dto.ClienteResponse;
import com.API.WebServer.dto.ClienteCreateRequest;
import com.API.WebServer.dto.ClienteUpdateRequest;
import com.API.WebServer.model.Cliente;
import com.API.WebServer.mapper.ClienteMapper;
import com.API.WebServer.repository.ClienteRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;

    public ClienteService(ClienteRepository clienteRepository, ClienteMapper clienteMapper) {
        this.clienteRepository = clienteRepository;
        this.clienteMapper = clienteMapper;
    }

    public List<ClienteResponse> listar() {
        return clienteRepository.findAll().stream()
                .map(clienteMapper::toResponse)
                .toList();
    }

    public ClienteResponse filtrarPorDni(String dni) {
        return clienteRepository.findByDni(dni)
                .map(clienteMapper::toResponse)
                .orElseThrow(()-> new RuntimeException("Cliente no encontrado"));

    }

    @Transactional
    public void eliminar(Integer idCliente) {
        Cliente cliente = clienteRepository.findById(idCliente).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        clienteRepository.deleteById(cliente.getIdCliente());
    }

    @Transactional
    public ClienteResponse insertar(ClienteCreateRequest request) {
        Cliente cliente = new Cliente(null, request.nombres(), request.apellidos(),
                request.dni(), request.telefono(), request.direccion(), request.fechaRegistro());
        return clienteMapper.toResponse(clienteRepository.save(cliente));
    }

    @Transactional
    public ClienteResponse actualizar(Integer idCliente, ClienteUpdateRequest request) {
        Cliente cliente = clienteRepository.findById(idCliente).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        cliente.setNombres(request.nombres());
        cliente.setApellidos(request.apellidos());
        cliente.setDireccion(request.dni());
        cliente.setDireccion(request.direccion());
        return clienteMapper.toResponse(clienteRepository.save(cliente));
    }
}
