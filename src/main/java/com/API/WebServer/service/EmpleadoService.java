package com.API.WebServer.service;

import com.API.WebServer.dto.EmpleadoCreateRequest;
import com.API.WebServer.dto.EmpleadoUpdateRequest;
import com.API.WebServer.dto.EmpleadoResponse;
import com.API.WebServer.mapper.EmpleadoMapper;
import com.API.WebServer.model.Empleado;
import com.API.WebServer.repository.EmpleadoRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional(readOnly = true)
public class EmpleadoService {
    private final EmpleadoRepository empleadoRepository;
    private final EmpleadoMapper empleadoMapper;

    public EmpleadoService(EmpleadoRepository empleadoRepository, EmpleadoMapper empleadoMapper) {
        this.empleadoRepository = empleadoRepository;
        this.empleadoMapper = empleadoMapper;
    }

    public List<EmpleadoResponse> listar() {
        return empleadoRepository.findAll().stream()
                .map(empleadoMapper::toResponse)
                .toList();
    }

    public EmpleadoResponse filtrarPorDni(String dni) {
        return empleadoRepository.findByDni(dni)
                .map(empleadoMapper::toResponse)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Empleado no encontrado"));
    }

    @Transactional
    public void eliminar(Integer idEmpleado) {
        Empleado empleado = empleadoRepository.findById(idEmpleado)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        empleadoRepository.deleteById(empleado.getIdEmpleado());
    }

    @Transactional
    public EmpleadoResponse insertar(EmpleadoCreateRequest request) {
        Empleado empleado = new Empleado(null, request.idSucursal(), request.nombres(),
                request.apellidos(), request.dni(), request.cargo(), request.sueldo(),
                request.telefono(), request.correo());
        return empleadoMapper.toResponse(empleadoRepository.save(empleado));
    }

    @Transactional
    public EmpleadoResponse actualizar(Integer idEmpleado, EmpleadoUpdateRequest request) {
        Empleado empleado = empleadoRepository.findById(idEmpleado)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        empleado.setIdSucursal(request.idSucursal());
        empleado.setNombres(request.nombres());
        empleado.setApellidos(request.apellidos());
        empleado.setDni(request.dni());
        empleado.setCargo(request.cargo());
        empleado.setSueldo(request.sueldo());
        empleado.setTelefono(request.telefono());
        empleado.setCorreo(request.correo());
        return empleadoMapper.toResponse(empleadoRepository.save(empleado));
    }
}
