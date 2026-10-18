package com.api.webserver.dto;

import java.time.LocalDate;

public record ClienteCreateRequest(
        String nombres,
        String apellidos,
        String dni,
        String telefono,
        String direccion,
        LocalDate fechaRegistro
) {
}
