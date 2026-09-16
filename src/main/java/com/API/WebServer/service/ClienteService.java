package com.API.WebServer.service;

import com.API.WebServer.dto.ClienteResponse;
import com.API.WebServer.dto.ClienteUpdateRequest;
import com.API.WebServer.model.Cliente;
import com.API.WebServer.mapper.ClienteMapper;
import com.API.WebServer.repository.ClienteRepository;
import java.util.List;
import org.springframework.dao.DataIntegrityViolationException;
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
        return clienteRepository.listar().stream()
                .map(clienteMapper::toResponse)
                .toList();
    }

    public List<ClienteResponse> filtrarPorDni(String dni) {
        return clienteRepository.filtrarPorDni(dni).stream()
                .map(clienteMapper::toResponse)
                .toList();
    }

    @Transactional
    public void eliminar(Integer idCliente) {
        try {
            if (clienteRepository.eliminar(idCliente) == 0) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente no encontrado");
            }
        } catch (DataIntegrityViolationException ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "No se puede eliminar el cliente porque tiene registros asociados", ex);
        }
    }

    @Transactional
    public ClienteResponse actualizar(Integer idCliente, ClienteUpdateRequest request) {
        validarTexto(request.nombres(), "nombres", 100, true);
        validarTexto(request.apellidos(), "apellidos", 100, true);
        validarTexto(request.dni(), "dni", 8, false);
        validarTexto(request.telefono(), "telefono", 20, false);
        validarTexto(request.direccion(), "direccion", 200, false);
        Cliente cliente = new Cliente(idCliente, request.nombres(), request.apellidos(),
                request.dni(), request.telefono(), request.direccion(), request.fechaRegistro());
        try {
            if (clienteRepository.actualizar(cliente) == 0) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente no encontrado");
            }
        } catch (DataIntegrityViolationException ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Los datos del cliente incumplen una restriccion de la base de datos", ex);
        }
        return clienteMapper.toResponse(cliente);
    }

    private void validarTexto(String valor, String campo, int maximo, boolean obligatorio) {
        if ((obligatorio && (valor == null || valor.isBlank()))
                || (valor != null && valor.length() > maximo)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Campo invalido: " + campo + " (maximo " + maximo + " caracteres)");
        }
    }
}
