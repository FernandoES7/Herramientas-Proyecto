package com.API.WebServer.service;

import com.API.WebServer.dto.SucursalCreateRequest;
import com.API.WebServer.dto.SucursalResponse;
import com.API.WebServer.dto.SucursalUpDateRequest;
import com.API.WebServer.mapper.SucursalMapper;
import com.API.WebServer.model.Sucursal;
import com.API.WebServer.repository.SucursalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SucursalService {

    private final SucursalRepository sucursalRepository;
    private final SucursalMapper sucursalMapper;

    public List<SucursalResponse> listar() {
        return sucursalRepository.findAll().stream()
                .map(sucursalMapper::toResponse)
                .toList();
    }

    @Transactional
    public SucursalResponse insertar(SucursalCreateRequest request) {
        Sucursal sucursal = new Sucursal(null, request.nombre(), request.direccion(),
                request.telefono(), request.ciudad());
        return sucursalMapper.toResponse(sucursalRepository.save(sucursal));
    }

    @Transactional
    public SucursalResponse actualizar(Long idSucursal, SucursalUpDateRequest request) {
        Sucursal sucursal = buscar(idSucursal);
        sucursal.setNombre(request.nombre());
        sucursal.setDireccion(request.direccion());
        sucursal.setTelefono(request.telefono());
        sucursal.setCiudad(request.ciudad());
        return sucursalMapper.toResponse(sucursalRepository.save(sucursal));
    }

    @Transactional
    public void eliminar(Long idSucursal) {
        Sucursal sucursal = buscar(idSucursal);
        sucursalRepository.deleteById(sucursal.getIdSucursal());
    }

    private Sucursal buscar(Long idSucursal) {
        return sucursalRepository.findById(idSucursal)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sucursal no encontrada"));
    }
}