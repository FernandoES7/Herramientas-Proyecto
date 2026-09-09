package com.API.WebServer.model;

import java.time.LocalDate;

public record Cliente(
        Integer idCliente,
        String nombres,
        String apellidos,
        String dni,
        String telefono,
        String direccion,
        LocalDate fechaRegistro
) {
}
