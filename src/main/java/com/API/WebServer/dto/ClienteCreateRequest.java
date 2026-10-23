package com.API.WebServer.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import org.hibernate.Length;

import java.time.LocalDate;

public record ClienteCreateRequest(
        @NotNull(message = "El nombre no puede ser nulo")
        String nombres,

        @NotNull(message = "El apellido no puede ser nulo")
        String apellidos,

        @NotNull(message = "El dni no puede ser nulo")
        String dni,

        @NotNull(message = "El telefono no puede ser nulo")
        String telefono,

        @NotNull(message = "La direccion no puede ser nula")
        String direccion,

        @NotNull(message = "La fecha no puede ser nula")
        LocalDate fechaRegistro
) {
}
