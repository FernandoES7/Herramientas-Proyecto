package com.API.WebServer.service;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.API.WebServer.dto.ProveedorRequest;
import com.API.WebServer.dto.ProveedorResponse;
import com.API.WebServer.exception.ProveedorNoEncontradoException;
import com.API.WebServer.exception.RucDuplicadoException;
import com.API.WebServer.mapper.ProveedorMapper;
import com.API.WebServer.model.Proveedor;
import com.API.WebServer.repository.ProveedorRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProveedorService {

    private final ProveedorRepository proveedorRepository;
    private final ProveedorMapper proveedorMapper;

    public List<ProveedorResponse> listar() {
        return proveedorRepository.listar().stream()
                .map(proveedorMapper::toResponse)
                .toList();
    }

    public ProveedorResponse buscarPorId(Integer idProveedor) {
        return proveedorRepository.buscarPorId(idProveedor)
                .map(proveedorMapper::toResponse)
                .orElseThrow(() -> new ProveedorNoEncontradoException(idProveedor));
    }

    public ProveedorResponse buscarPorRuc(String ruc) {
        return proveedorRepository.buscarPorRuc(ruc)
                .map(proveedorMapper::toResponse)
                .orElseThrow(() -> new ProveedorNoEncontradoException(ruc));
    }

    public List<ProveedorResponse> buscarPorRazonSocial(String razonSocial) {
        return proveedorRepository.buscarPorRazonSocial(razonSocial).stream()
                .map(proveedorMapper::toResponse)
                .toList();
    }

    @Transactional
    public ProveedorResponse crear(ProveedorRequest request) {
        if (proveedorRepository.existePorRuc(request.getRuc())) {
            throw new RucDuplicadoException(request.getRuc());
        }
        Proveedor proveedor = convertir(request, null);
        return proveedorMapper.toResponse(proveedorRepository.guardar(proveedor));
    }

    @Transactional
    public ProveedorResponse actualizar(Integer idProveedor, ProveedorRequest request) {
        Proveedor actual = proveedorRepository.buscarPorId(idProveedor)
                .orElseThrow(() -> new ProveedorNoEncontradoException(idProveedor));

        proveedorRepository.buscarPorRuc(request.getRuc())
                .filter(proveedor -> !proveedor.getIdProveedor().equals(idProveedor))
                .ifPresent(proveedor -> {
                    throw new RucDuplicadoException(request.getRuc());
                });

        actual.setRazonSocial(request.getRazonSocial());
        actual.setRuc(request.getRuc());
        actual.setTelefono(request.getTelefono());
        actual.setCorreo(request.getCorreo());
        actual.setDireccion(request.getDireccion());

        return proveedorMapper.toResponse(proveedorRepository.actualizar(actual));
    }

    @Transactional
    public void eliminar(Integer idProveedor) {
        if (proveedorRepository.eliminar(idProveedor) == 0) {
            throw new ProveedorNoEncontradoException(idProveedor);
        }
    }

    private Proveedor convertir(ProveedorRequest request, Integer idProveedor) {
        return Proveedor.builder()
                .idProveedor(idProveedor)
                .razonSocial(request.getRazonSocial())
                .ruc(request.getRuc())
                .telefono(request.getTelefono())
                .correo(request.getCorreo())
                .direccion(request.getDireccion())
                .build();
    }
}