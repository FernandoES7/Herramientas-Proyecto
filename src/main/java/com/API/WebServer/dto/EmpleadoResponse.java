package com.API.WebServer.dto;

import java.math.BigDecimal;

public record EmpleadoResponse(
        Integer idEmpleado,
        Integer idSucursal,
        String nombres,
        String apellidos,
        String dni,
        String cargo,
        BigDecimal sueldo,
        String telefono,
        String correo
) {
}
