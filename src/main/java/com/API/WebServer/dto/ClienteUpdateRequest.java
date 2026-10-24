package com.API.WebServer.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record ClienteUpdateRequest(
        @NotNull(message = "El nombre no puede ser nulo")
        @Size(min = 3, max = 50, message = "El nombre excede los 50 caracteres")
        String nombres,

        @NotNull(message = "El apellido no puede ser nulo")
        @Size(min = 3, max = 50, message = "El apellido excede los 50 caracteres")
        String apellidos,

        @NotNull(message = "El dni no puede ser nulo")
        @Size(min = 8, max = 8, message = "El DNI debe tener 8 caracteres")
        String dni,

        @NotNull(message = "El telefono no puede ser nulo")
        @Size(min = 7, message = "El teléfono es inválido")
        String telefono,

        @NotNull(message = "La direccion no puede ser nula")
        @Size(min = 3, max = 50, message = "La dirección excede los 50 caracteres")
        String direccion
) {
}
