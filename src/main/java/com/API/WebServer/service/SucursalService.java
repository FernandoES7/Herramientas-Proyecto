package com.API.WebServer.service;
import com.API.WebServer.dto.SucursalResponse;
import com.API.WebServer.mapper.SucursalMapper;
import com.API.WebServer.model.Sucursal;
import com.API.WebServer.repository.SucursalRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SucursalService {
    private final SucursalRepository sucursalRepository;

    public SucursalService(SucursalRepository sucursalRepository) {
        this.sucursalRepository = sucursalRepository;
    }

    public List<SucursalResponse> listar() {
        return sucursalRepository.findAll()
                .stream()
                .map(SucursalMapper::toResponse)
                .collect(Collectors.toList());
    }

    public SucursalResponse crear(Sucursal sucursal) {
        return SucursalMapper.toResponse(sucursalRepository.save(sucursal));
    }
}
