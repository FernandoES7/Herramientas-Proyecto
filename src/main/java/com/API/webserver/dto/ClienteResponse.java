package com.api.webserver.dto;

import java.time.LocalDate;

public record ClienteResponse(
        Integer idCliente,
        String nombres,
        String apellidos,
        String dni,
        String telefono,
        String direccion,
        LocalDate fechaRegistro
) {
}
