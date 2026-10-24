package com.API.WebServer.service;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.API.WebServer.dto.ProveedorRequest;
import com.API.WebServer.dto.ProveedorResponse;
import com.API.WebServer.mapper.ProveedorMapper;
import com.API.WebServer.model.Proveedor;
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

    public ProveedorResponse buscarPorId(Integer idProveedor) {
        return proveedorRepository.buscarPorId(idProveedor)
                .map(proveedorMapper::toResponse)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Proveedor no encontrado"));
    }

    public List<ProveedorResponse> filtrarPorRuc(String ruc) {
        return proveedorRepository.filtrarPorRuc(ruc).stream()
                .map(proveedorMapper::toResponse)
                .toList();
    }

    @Transactional
    public ProveedorResponse insertar(ProveedorRequest request) {
        validar(request);
        Proveedor proveedor = new Proveedor(null, request.razonSocial(), request.ruc(),
                request.telefono(), request.direccion());
        try {
            return proveedorMapper.toResponse(proveedorRepository.insertar(proveedor));
        } catch (DataIntegrityViolationException ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Los datos del proveedor incumplen una restriccion de la base de datos (¿RUC duplicado?)", ex);
        }
    }

    @Transactional
    public ProveedorResponse actualizar(Integer idProveedor, ProveedorRequest request) {
        validar(request);
        Proveedor proveedor = new Proveedor(idProveedor, request.razonSocial(), request.ruc(),
                request.telefono(), request.direccion());
        try {
            if (proveedorRepository.actualizar(proveedor) == 0) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Proveedor no encontrado");
            }
        } catch (DataIntegrityViolationException ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Los datos del proveedor incumplen una restriccion de la base de datos (¿RUC duplicado?)", ex);
        }
        return proveedorMapper.toResponse(proveedor);
    }

    @Transactional
    public void eliminar(Integer idProveedor) {
        try {
            if (proveedorRepository.eliminar(idProveedor) == 0) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Proveedor no encontrado");
            }
        } catch (DataIntegrityViolationException ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "No se puede eliminar el proveedor porque tiene registros asociados", ex);
        }
    }

    private void validar(ProveedorRequest request) {
        if (request.razonSocial() == null || request.razonSocial().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La razon social es obligatoria");
        }
        if (request.ruc() == null || !request.ruc().matches("\\d{11}")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El RUC es obligatorio y debe tener 11 digitos");
        }
    }
}