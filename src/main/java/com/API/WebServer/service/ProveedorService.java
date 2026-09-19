package com.API.WebServer.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.API.WebServer.dto.ProveedorResponse;
import com.API.WebServer.mapper.ProveedorMapper;
import com.API.WebServer.repository.ProveedorRepository;

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