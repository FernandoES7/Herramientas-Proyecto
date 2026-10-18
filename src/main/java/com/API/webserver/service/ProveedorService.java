package com.api.webserver.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.webserver.dto.ProveedorResponse;
import com.api.webserver.mapper.ProveedorMapper;
import com.api.webserver.repository.ProveedorRepository;

@Service
@Transactional(readOnly = true)
public class ProveedorService {

    private final ProveedorRepository proveedorRepository;
    private final ProveedorMapper proveedorMapper;

    public ProveedorService(ProveedorRepository proveedorRepository, ProveedorMapper proveedorMapper) {
        this.proveedorRepository = proveedorRepository;
        this.proveedorMapper = proveedorMapper;
    }

    public List<ProveedorResponse> listar() {
        return proveedorRepository.listar().stream()
                .map(proveedorMapper::toResponse)
                .toList();
    }
}