package com.API.WebServer.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record ProveedorRequest(
        @NotBlank(message = "El RUC es obligatorio")
        @Pattern(regexp = "\\d{11}", message = "El RUC debe tener 11 dígitos")
        String ruc,

        @NotBlank(message = "La razón social es obligatoria")
        String razonSocial,

        @Pattern(regexp = "^\\+?[0-9 ()-]{7,20}$", message = "El teléfono no tiene un formato válido")
        String telefono,

        @Email(message = "El correo no tiene un formato válido")
        String correo,

        String direccion
) {
}
