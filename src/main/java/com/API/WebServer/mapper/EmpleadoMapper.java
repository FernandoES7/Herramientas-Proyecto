package com.API.WebServer.mapper;

import com.API.WebServer.dto.EmpleadoResponse;
import com.API.WebServer.model.Empleado;
import org.springframework.stereotype.Component;

@Component
public class EmpleadoMapper {
    public EmpleadoResponse toResponse(Empleado empleado) {
        return new EmpleadoResponse(
                empleado.getIdEmpleado(), empleado.getIdSucursal(), empleado.getNombres(),
                empleado.getApellidos(), empleado.getDni(), empleado.getCargo(),
                empleado.getSueldo(), empleado.getTelefono(), empleado.getCorreo()
        );
    }
}
