package com.API.WebServer.mapper;

import com.API.WebServer.dto.EmpleadoRequest;
import com.API.WebServer.dto.EmpleadoResponse;
import com.API.WebServer.model.Empleado;
import org.springframework.stereotype.Component;

@Component
public class EmpleadoMapper {

    public Empleado toEntity(EmpleadoRequest request) {
        if (request == null) return null;
        return Empleado.builder()
                .idSucursal(request.getIdSucursal())
                .nombres(request.getNombres())
                .apellidos(request.getApellidos())
                .dni(request.getDni())
                .cargo(request.getCargo())
                .sueldo(request.getSueldo())
                .telefono(request.getTelefono())
                .correo(request.getCorreo())
                .build();
    }

    public EmpleadoResponse toResponse(Empleado empleado) {
        if (empleado == null) return null;
        return EmpleadoResponse.builder()
                .idEmpleado(empleado.getIdEmpleado())
                .idSucursal(empleado.getIdSucursal())
                .nombres(empleado.getNombres())
                .apellidos(empleado.getApellidos())
                .dni(empleado.getDni())
                .cargo(empleado.getCargo())
                .sueldo(empleado.getSuelso())
                .telefono(empleado.getTelefono())
                .correo(empleado.getCorreo())
                .build();
    }
}