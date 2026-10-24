package com.API.WebServer.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SucursalCreateRequest(
        @NotBlank(message = "El nombre no puede estar vacio")
        @Size(max = 50, message = "El nombre no puede superar los 50 caracteres")
        String nombre,

        @Size(max = 200, message = "La direccion no puede superar los 200 caracteres")
        String direccion,

        @Size(max = 9, message = "El telefono no puede superar los 9 caracteres")
        String telefono,

        @Size(max = 100, message = "La ciudad no puede superar los 100 caracteres")
        String ciudad
) {
}