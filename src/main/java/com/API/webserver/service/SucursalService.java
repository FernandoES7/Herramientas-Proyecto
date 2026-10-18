package com.api.webserver.service;
import com.api.webserver.dto.SucursalResponse;
import com.api.webserver.mapper.SucursalMapper;
import com.api.webserver.model.Sucursal;
import com.api.webserver.repository.SucursalRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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
                .toList();
    }

    public SucursalResponse crear(Sucursal sucursal) {
        return SucursalMapper.toResponse(sucursalRepository.save(sucursal));
    }
}
