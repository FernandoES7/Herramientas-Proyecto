package com.API.WebServer.service;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.API.WebServer.dto.ProveedorRequest;
import com.API.WebServer.dto.ProveedorResponse;
import com.API.WebServer.exception.ProveedorConflictoException;
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
        return proveedorRepository.findAll().stream()
                .map(proveedorMapper::toResponse)
                .toList();
    }

    public ProveedorResponse buscarPorId(Integer idProveedor) {
        return proveedorRepository.findById(idProveedor)
                .map(proveedorMapper::toResponse)
                .orElseThrow(() -> new ProveedorNoEncontradoException(idProveedor));
    }

    public ProveedorResponse buscarPorRuc(String ruc) {
        return proveedorRepository.findByRuc(ruc)
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
        if (proveedorRepository.existsByRuc(request.ruc())) {
            throw new RucDuplicadoException(request.ruc());
        }
        Proveedor proveedor = Proveedor.builder()
                .razonSocial(request.razonSocial())
                .ruc(request.ruc())
                .telefono(request.telefono())
                .correo(request.correo())
                .direccion(request.direccion())
                .build();
        return proveedorMapper.toResponse(guardar(proveedor));
    }

    @Transactional
    public ProveedorResponse actualizar(Integer idProveedor, ProveedorRequest request) {
        Proveedor actual = proveedorRepository.findById(idProveedor)
                .orElseThrow(() -> new ProveedorNoEncontradoException(idProveedor));

        proveedorRepository.findByRuc(request.ruc())
                .filter(proveedor -> !proveedor.getIdProveedor().equals(idProveedor))
                .ifPresent(proveedor -> {
                    throw new RucDuplicadoException(request.ruc());
                });

        actual.setRazonSocial(request.razonSocial());
        actual.setRuc(request.ruc());
        actual.setTelefono(request.telefono());
        actual.setCorreo(request.correo());
        actual.setDireccion(request.direccion());

        return proveedorMapper.toResponse(guardar(actual));
    }

    @Transactional
    public void eliminar(Integer idProveedor) {
        if (!proveedorRepository.existsById(idProveedor)) {
            throw new ProveedorNoEncontradoException(idProveedor);
        }
        try {
            proveedorRepository.deleteById(idProveedor);
            // flush para que el error de clave foránea salga aquí y no al terminar la transacción
            proveedorRepository.flush();
        } catch (DataIntegrityViolationException ex) {
            throw new ProveedorConflictoException(
                    "No se puede eliminar el proveedor porque tiene registros asociados", ex);
        }
    }

    // saveAndFlush para que un dato repetido (teléfono, correo) dé 409 aquí y no un 500 al hacer commit
    private Proveedor guardar(Proveedor proveedor) {
        try {
            return proveedorRepository.saveAndFlush(proveedor);
        } catch (DataIntegrityViolationException ex) {
            throw new ProveedorConflictoException(
                    "Los datos del proveedor incumplen una restricción de la base de datos", ex);
        }
    }
}
