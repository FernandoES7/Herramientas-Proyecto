package com.API.WebServer.service;

import com.API.WebServer.dto.ClienteResponse;
import com.API.WebServer.mapper.ClienteMapper;
import com.API.WebServer.repository.ClienteRepository;
import java.util.List;
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
}
